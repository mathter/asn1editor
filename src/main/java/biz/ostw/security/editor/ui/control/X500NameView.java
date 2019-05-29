package biz.ostw.security.editor.ui.control;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TreeTableView;
import org.bouncycastle.asn1.x500.AttributeTypeAndValue;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;

import java.io.IOException;
import java.util.ResourceBundle;

public class X500NameView extends TreeTableView<AttributeTypeAndValue> {

    private ResourceBundle resources = ResourceBundle.getBundle("biz.ostw.security.editor.ui.control.message");

    private final ObservableList<RDN> list = FXCollections.observableArrayList();

    private final SimpleObjectProperty<X500Name> valueProperty = new SimpleObjectProperty<>();

    public X500NameView() {
        final FXMLLoader loader = new FXMLLoader(Asn1View.class.getResource("asn1view.fxml"));

        loader.setRoot(this);
        loader.setResources(this.resources);
        loader.setController(this);
        loader.setLocation(Asn1View.class.getResource("asn1view.fxml"));

        try {
            loader.load();
            this.initComponents();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initComponents() {
        this.table.setRoot(null);

        this.valueProperty.addListener((observable, oldValue, newValue) -> {
            this.list.clear();

            if (newValue != null) {
                this.list.addAll(newValue.getRDNs());
            }
        });
    }

    public X500Name getValueProperty() {
        return this.valueProperty.getValue();
    }

    public void setValueProperty(X500Name valueProperty) {
        this.valueProperty.set(valueProperty);
    }

    public SimpleObjectProperty<X500Name> valuePropertyProperty() {
        return valueProperty;
    }
}
