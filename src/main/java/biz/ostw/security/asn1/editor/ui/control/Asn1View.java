package biz.ostw.security.asn1.editor.ui.control;

import biz.ostw.security.asn1.editor.content.Content;
import biz.ostw.security.asn1.editor.content.ContentAnalyzer;
import biz.ostw.security.asn1.editor.objinfo.ASN1ObjectInfo;
import biz.ostw.security.asn1.editor.ui.Main;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
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

    private final Preferences preferences = Preferences.userNodeForPackage(Main.class);

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

    private ResourceBundle resources = ResourceBundle.getBundle("biz.ostw.security.asn1.editor.ui.control.message");

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

        if (asn1 != null) {
            final Content<?> content = new ContentAnalyzer().analyze(asn1);
            this.contentType.setText(String.format(this.resources.getString("biz.ostw.security.asn1.editor.ui.control.Asn1View.objectidentifier.type"), content.getDescription()));
            this.treeView.setRoot(asn1);
        } else {
            this.contentType.setText("");
            this.treeView.setRoot((ASN1Primitive) null);
        }
    }

    public ObjectProperty<ASN1Primitive> valueProperty() {
        return this.valueProperty;
    }

    public StringProperty typeNameProperty() {
        return this.contentType.textProperty();
    }

    private void initComponents() {
        Preferences preferences = this.getPreferences();

        this.contentType.prefWidthProperty().bind(this.widthProperty());

        this.treeView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> Asn1View.this.descriptionTableView.set(newValue.getValue()));
        this.treeView.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                Asn1View.this.searchText.requestFocus();
            }
        });

        this.splitPane.setDividerPositions(preferences.getDouble("divpos", 0.8));
        this.splitPane.getDividers().stream().findFirst().ifPresent(d -> {
            d.positionProperty().addListener((observable, oldValue, newValue) -> preferences.putDouble("divpos", d.getPosition()));
        });


        this.searchClearButton.setOnAction(event -> Asn1View.this.searchText.clear());
        this.searchText.setOnAction(this::search);
        this.searchUpButton.setOnAction(this::search);
        this.searchDownButton.setOnAction(this::search);
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
        return Optional.ofNullable(this.getId()).map(id -> this.preferences.node(id)).orElse(this.preferences);
    }
}
