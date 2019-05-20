package biz.ostw.security.editor.ui.control;

import biz.ostw.security.editor.content.Content;
import biz.ostw.security.editor.content.ContentAnalyzer;
import biz.ostw.security.editor.objinfo.ASN1ObjectInfo;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.bouncycastle.asn1.ASN1Primitive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.prefs.Preferences;
import java.util.regex.Pattern;

public class Asn1View extends VBox {

    private final Preferences preferences = Preferences.userNodeForPackage(Asn1View.class);

    @FXML
    private ASN1TreeView treeView;

    @FXML
    private ASN1ObjectInfoTableView descriptionTableView;

    @FXML
    private SplitPane splitPane;

    @FXML
    private Label contentType;

    @FXML
    private Pane searchPane;

    @FXML
    private TextField searchText;

    @FXML
    private Button searchClearButton;

    @FXML
    private Button searchUpButton;

    @FXML
    private Button searchDownButton;

    @FXML
    private CheckBox searchUseRegExpr;

    private final ObservableList<TreeItem> searchResult = new ObservableListWrapper<>(new ArrayList<>());

    private ObjectProperty<TreeItem> searchCurrentItemProperty = new SimpleObjectProperty<>(this, "searchCurrentItemProperty", null);

    private final SimpleObjectProperty<ASN1Primitive> valueProperty = new SimpleObjectProperty<>(this, "value", null);

    private ResourceBundle resources = ResourceBundle.getBundle("biz.ostw.security.editor.ui.control.message");

    public Asn1View() {
        final FXMLLoader loader = new FXMLLoader(Asn1View.class.getResource("asn1view.fxml"));

        loader.setRoot(this);
        loader.setResources(this.resources);
        loader.setController(this);
        loader.setLocation(Asn1View.class.getResource("asn1view.fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.initComponents();
    }

    public void setValue(ASN1Primitive asn1) throws IOException {
        this.valueProperty.setValue(asn1);
    }

    public ASN1Primitive getValue() {
        return this.valueProperty.getValue();
    }

    public ObjectProperty<ASN1Primitive> valueProperty() {
        return this.valueProperty;
    }

    public StringProperty typeNameProperty() {
        return this.contentType.textProperty();
    }

    private void initComponents() {
        Preferences prefs = this.getPreferences();

        this.contentType.prefWidthProperty().bind(this.widthProperty());

        this.treeView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> Asn1View.this.descriptionTableView.set(newValue != null ? newValue.getValue() : null));
        this.treeView.addEventHandler(KeyEvent.KEY_RELEASED, event -> Asn1View.this.searchText.requestFocus());

        Platform.runLater(() -> {
            Asn1View.this.splitPane.getDividers().stream().findFirst().ifPresent(d -> {
                d.positionProperty().addListener((observable, oldValue, newValue) -> {
                    prefs.putDouble("divpos", d.getPosition());
                });
            });

            Asn1View.this.splitPane.setDividerPositions(prefs.getDouble("divpos", 0.8));
        });

        this.searchClearButton.setOnAction(event -> Asn1View.this.searchText.clear());
        this.searchText.setOnAction(this::search);
        this.searchUpButton.setOnAction(this::search);
        this.searchDownButton.setOnAction(this::search);

        Platform.runLater(() -> {
            this.searchUseRegExpr.selectedProperty().addListener((observable, oldValue, newValue) -> prefs.putBoolean("searchUseRegExpr", newValue));
            this.searchUseRegExpr.selectedProperty().setValue(prefs.getBoolean("searchUseRegExpr", false));
        });

        this.valueProperty.addListener(new ChangeListener<ASN1Primitive>() {
            @Override
            public void changed(ObservableValue<? extends ASN1Primitive> observable, ASN1Primitive oldValue, ASN1Primitive newValue) {
                if (newValue != null) {
                    final Content<?> content = new ContentAnalyzer().analyze(newValue);
                    Asn1View.this.contentType.setText(String.format(Asn1View.this.resources.getString("biz.ostw.security.asn1.editor.ui.control.Asn1View.objectidentifier.type"), content.getDescription()));
                } else {
                    Asn1View.this.contentType.setText("");
                }

                try {
                    Asn1View.this.treeView.setRoot(newValue);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void search(ActionEvent event) {

        Object source = event.getSource();

        if (source == this.searchText) {
            this.search(true, true);
            this.searchDownButton.requestFocus();
        } else if (source == this.searchUpButton) {
            this.search(false, false);
        } else {
            this.search(false, true);
        }
    }

    private Function<String, Boolean> getMatcher() {

        final Function<String, Boolean> matcher;

        if (this.searchUseRegExpr.isSelected()) {
            matcher = new Function<String, Boolean>() {

                private final Pattern pattern = Pattern.compile(Asn1View.this.searchText.getText());

                @Override
                public Boolean apply(String s) {
                    return this.pattern.matcher(s).matches();
                }
            };
        } else {
            matcher = (s) -> s.indexOf(Asn1View.this.searchText.getText()) > 0;
        }

        return matcher;
    }


    private void search(boolean resetSearch, boolean nextDirection) {

        if (resetSearch) {
            this.searchCurrentItemProperty.setValue(null);
            this.searchResult.clear();
            this.searchResult.addAll(filter(this.treeView.getRoot(), this.getMatcher(), new ArrayList<>()));
        }

        if (this.searchCurrentItemProperty == null) {
            this.searchCurrentItemProperty.setValue(this.searchResult.stream().findFirst().orElse(null));
        } else {
            final int index;
            final TreeItem treeItem1 = this.searchCurrentItemProperty.get();

            if (nextDirection) {
                index = this.searchResult.indexOf(treeItem1) + 1;
            } else {
                index = this.searchResult.indexOf(treeItem1) - 1;
            }

            try {
                this.searchCurrentItemProperty.setValue(this.searchResult.get(index));
            } catch (IndexOutOfBoundsException e) {
            }

            Optional.ofNullable(this.searchCurrentItemProperty.getValue()).ifPresent(treeItem -> {
                this.treeView.getSelectionModel().select(treeItem);
                this.treeView.scrollTo(this.treeView.getSelectionModel().getSelectedIndex());
            });
        }
    }

    private static List<TreeItem<ASN1Primitive>> filter(TreeItem<ASN1Primitive> root, Function<String, Boolean> matcher, List<TreeItem<ASN1Primitive>> list) {

        if (root != null) {
            ASN1Primitive asn1 = root.getValue();

            if (asn1 != null) {
                ASN1ObjectInfo<ASN1Primitive> objectInfo = ASN1ObjectInfo.get((Class<ASN1Primitive>) asn1.getClass());

                String textPresentation = objectInfo.toString(asn1);

                if (matcher.apply(textPresentation)) {
                    list.add(root);
                }
            }

            ObservableList<TreeItem<ASN1Primitive>> children = root.getChildren();

            if (children != null && !children.isEmpty()) {
                for (TreeItem<ASN1Primitive> treeItem : children) {
                    filter(treeItem, matcher, list);
                }
            }
        }

        return list;
    }

    private Preferences getPreferences() {
        return Optional.ofNullable(this.getId()).map(id -> this.preferences.node(Asn1View.class.getName() + "_" + id)).orElse(this.preferences);
    }
}
