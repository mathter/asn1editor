package biz.ostw.security.asn1.editor.ui.control;

import biz.ostw.security.asn1.editor.objinfo.ASN1ObjectInfo;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.util.Callback;
import org.bouncycastle.asn1.ASN1Primitive;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class ASN1ObjectInfoTableView extends VBox implements ChangeListener<Object> {

    private final Preferences preferences = Preferences.userNodeForPackage(ASN1ObjectInfoTableView.class);

    @FXML
    private TableView<ASN1ObjectInfo.ASN1ObjectDescription> table;

    @FXML
    private TableColumn<ASN1ObjectInfo.ASN1ObjectDescription, String> nameColumn;

    @FXML
    private TableColumn<ASN1ObjectInfo.ASN1ObjectDescription, String> valueColumn;

    @FXML
    private WebView valueView;

    @FXML
    private SplitPane splitePane;

    private ObservableList<ASN1ObjectInfo.ASN1ObjectDescription> list = FXCollections.observableArrayList();

    private ResourceBundle resources = ResourceBundle.getBundle("biz.ostw.security.asn1.editor.ui.control.message");

    public ASN1ObjectInfoTableView() {
        final FXMLLoader loader = new FXMLLoader(ASN1ObjectInfoTableView.class.getResource("asn1objectinfodescriptiontableview.fxml"));

        loader.setRoot(this);
        loader.setResources(this.resources);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.table.setItems(this.list);
        this.table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.table.getSelectionModel().selectedIndexProperty().addListener(this::changed);

        this.nameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ASN1ObjectInfo.ASN1ObjectDescription, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ASN1ObjectInfo.ASN1ObjectDescription, String> param) {
                return new SimpleStringProperty(param.getValue().getName());
            }
        });

        this.nameColumn.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            }
        });

        this.valueColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ASN1ObjectInfo.ASN1ObjectDescription, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ASN1ObjectInfo.ASN1ObjectDescription, String> param) {
                return new SimpleStringProperty(String.valueOf(param.getValue().getValue()));
            }
        });

        Platform.runLater(() -> {
            Preferences prefs = this.getPreferences();
            ASN1ObjectInfoTableView.this.splitePane.setDividerPositions(prefs.getDouble("divpos", 0.5));
            ASN1ObjectInfoTableView.this.splitePane.getDividers().stream().findFirst().ifPresent(d -> {
                d.positionProperty().addListener((observable, oldValue, newValue) -> prefs.putDouble("divpos", d.getPosition()));
            });
        });
    }

    public void set(ASN1Primitive asn1) {
        ASN1ObjectInfo<ASN1Primitive> objectInfo = ASN1ObjectInfo.get((Class<ASN1Primitive>) asn1.getClass());
        this.addAll(objectInfo.descriptions(asn1));
    }

    @Override
    public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
        Object value = this.table.selectionModelProperty().get().getSelectedItem().getValue();

        this.setDetailes(value);
    }

    private void setDetailes(Object value) {
        this.valueView.getEngine().loadContent(null);

        if (value instanceof URL) {
            this.valueView.getEngine().load(value.toString());
        } else {
            StringBuilder sb = new StringBuilder("<html><body style='padding: 10px''><p style='word-break: break-all;'>");
            sb.append(value);
            sb.append("</p></body>");

            this.valueView.getEngine().loadContent(String.valueOf(value), "text/html");
        }
    }

    public void addAll(Collection<ASN1ObjectInfo.ASN1ObjectDescription> c) {
        this.clear();
        this.list.addAll(c);
    }

    private void clear() {
        this.list.clear();
        this.valueView.getEngine().loadContent(this.resources.getString("biz.ostw.security.asn1.editor.ui.control.ASN1ObjectInfoTableView.object.info.detailes.empty.message"));
    }

    private Preferences getPreferences() {
        return Optional.ofNullable(this.getId()).map(id -> this.preferences.node(ASN1ObjectInfoTableView.class.getName() + "_" + id)).orElse(this.preferences);
    }
}
