package biz.ostw.security.editor.ui.control;

import biz.ostw.security.editor.ui.CommonService;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;

import java.io.IOException;
import java.util.ResourceBundle;

public class AlgorithmDataView extends SplitPane {
    private ResourceBundle resources = ResourceBundle.getBundle("biz.ostw.security.editor.ui.control.message");

    private ObjectProperty<AlgorithmIdentifier> algorithmIdentifierProperty = new SimpleObjectProperty<>(null);

    private ObjectProperty<byte[]> dataProperty = new SimpleObjectProperty<>(null);

    @FXML
    private TextField algorithmIdentifier;

    @FXML
    private Asn1View algorithmParameters;

    @FXML
    private TextArea dataView;

    @FXML
    private Label dataTitleView;

    public AlgorithmDataView() {
        final FXMLLoader loader = new FXMLLoader(X509CertificateView.class.getResource("algorithmDataView.fxml"));

        loader.setRoot(this);
        loader.setResources(this.resources);
        loader.setController(this);
        loader.setLocation(Asn1View.class.getResource("algorithmDataView.fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.initComponents();
    }

    public ObjectProperty<AlgorithmIdentifier> algorithmIdentifierProperty() {
        return this.algorithmIdentifierProperty;
    }

    public AlgorithmIdentifier getAlgorithmIdentifier() {
        return this.algorithmIdentifierProperty.get();
    }

    public void setAlgorithmIdentifier(AlgorithmIdentifier value) {
        this.algorithmIdentifierProperty.set(value);
    }

    public ObjectProperty<byte[]> dataProperty() {
        return this.dataProperty;
    }

    public byte[] getData() {
        return this.dataProperty.get();
    }

    public void setData(byte[] value) {
        this.dataProperty.set(value);
    }

    public StringProperty dataTitleProperty() {
        return this.dataTitleView.textProperty();
    }

    public String getDataTitle() {
        return this.dataTitleProperty().get();
    }

    public void setDataTitle(String value) {
        this.dataTitleProperty().set(value);
    }

    private void initComponents() {
        this.algorithmParameters.contentTypeVisible().set(false);

        this.algorithmIdentifierProperty.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    AlgorithmDataView.this.algorithmIdentifier.setText(newValue.getAlgorithm().getId());

                    if (newValue.getParameters() != null) {
                        AlgorithmDataView.this.algorithmParameters.setValue(newValue.getParameters().toASN1Primitive());
                    } else {
                        AlgorithmDataView.this.algorithmParameters.setValue(null);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        this.dataProperty.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                this.dataView.setText(CommonService.toString(newValue, 16));
            } else {
                this.dataView.setText(null);
            }
        });
    }
}
