package biz.ostw.security.editor.ui.control;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x500.AttributeTypeAndValue;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameStyle;
import org.bouncycastle.asn1.x500.style.BCStyle;

import java.util.ResourceBundle;

public class X500NameView extends TableView<AttributeTypeAndValue> {

    private final ResourceBundle resources = ResourceBundle.getBundle("biz.ostw.security.editor.ui.control.message");

    private final ObjectProperty<X500Name> valueProperty = new SimpleObjectProperty<>();

    private final ObservableList<AttributeTypeAndValue> list = FXCollections.observableArrayList();

    private final TableColumn<AttributeTypeAndValue, String> identifierColumn = new TableColumn() {
        {
            this.setText(X500NameView.this.resources.getString("biz.ostw.security.asn1.editor.ui.control.x508NameView.rdn.identifier"));
            this.setCellValueFactory(new Callback<CellDataFeatures<AttributeTypeAndValue, String>, ObservableValue>() {
                @Override
                public ObservableValue call(CellDataFeatures<AttributeTypeAndValue, String> param) {
                    AttributeTypeAndValue attributeTypeAndValue = param.getValue();
                    ASN1ObjectIdentifier objectIdentifier = attributeTypeAndValue.getType();

                    return new SimpleStringProperty(objectIdentifier.getId());
                }
            });
        }
    };

    private final TableColumn<AttributeTypeAndValue, String> valueColumn = new TableColumn() {
        {
            this.setText(X500NameView.this.resources.getString("biz.ostw.security.asn1.editor.ui.control.x508NameView.rdn.value"));
            this.setCellValueFactory(new Callback<CellDataFeatures<AttributeTypeAndValue, String>, ObservableValue>() {
                @Override
                public ObservableValue call(CellDataFeatures<AttributeTypeAndValue, String> param) {
                    AttributeTypeAndValue attributeTypeAndValue = param.getValue();

                    return new SimpleStringProperty(String.valueOf(attributeTypeAndValue.getValue()));
                }
            });
        }
    };

    private final TableColumn<AttributeTypeAndValue, String> resolveColumn = new TableColumn() {
        {
            this.setText(X500NameView.this.resources.getString("biz.ostw.security.asn1.editor.ui.control.x508NameView.rdn.identifier.Resolver"));
            this.setCellValueFactory(new Callback<CellDataFeatures<AttributeTypeAndValue, String>, ObservableValue>() {
                @Override
                public ObservableValue call(CellDataFeatures<AttributeTypeAndValue, String> param) {
                    AttributeTypeAndValue attributeTypeAndValue = param.getValue();
                    ASN1ObjectIdentifier objectIdentifier = attributeTypeAndValue.getType();

                    return new SimpleStringProperty(X500NameView.this.getX500NameStyle().oidToDisplayName(objectIdentifier));
                }
            });
        }
    };

    private final TableColumn identifierInfoColumn = new TableColumn() {
        {
            this.setText(X500NameView.this.resources.getString("biz.ostw.security.asn1.editor.ui.control.x508NameView.rdn.identifierInfoColumn"));
            this.getColumns().add(identifierColumn);
            this.getColumns().add(resolveColumn);
        }
    };

    public X500NameView() {
        this.initComponents();
    }

    private void initComponents() {
        this.getColumns().add(identifierInfoColumn);
        this.getColumns().add(this.valueColumn);

        this.setItems(this.list);
        this.valueProperty.addListener(this::changed);
    }

    public void setValue(X500Name value) {
        this.valueProperty.setValue(value);
    }

    public X500Name getValue() {
        return this.valueProperty.getValue();
    }

    public ObjectProperty<X500Name> valueProperty() {
        return valueProperty;
    }

    private void changed(ObservableValue<? extends X500Name> observable, X500Name oldValue, X500Name newValue) {

        this.list.clear();

        if (newValue != null) {
            for (RDN rdn : newValue.getRDNs()) {
                for (AttributeTypeAndValue attributeTypeAndValue : rdn.getTypesAndValues()) {
                    this.list.add(attributeTypeAndValue);
                }
            }
        }
    }

    private X500NameStyle getX500NameStyle() {
        return BCStyle.INSTANCE;
    }
}
