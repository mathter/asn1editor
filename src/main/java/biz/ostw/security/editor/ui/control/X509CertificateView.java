package biz.ostw.security.editor.ui.control;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.bouncycastle.asn1.x509.Certificate;
import org.bouncycastle.cert.X509CertificateHolder;

import java.io.IOException;
import java.util.ResourceBundle;

public class X509CertificateView extends VBox {

    private ResourceBundle resources = ResourceBundle.getBundle("biz.ostw.security.editor.ui.control.message");

    private final ObjectProperty<Certificate> valueProperty = new SimpleObjectProperty<>(this, "value", null);

    private X509CertificateHolder holder;

    @FXML
    private X500NameView issuer;

    @FXML
    private X500NameView subject;

    public X509CertificateView() {
        final FXMLLoader loader = new FXMLLoader(Asn1View.class.getResource("x509CertificateView.fxml"));

        loader.setRoot(this);
        loader.setResources(this.resources);
        loader.setController(this);
        loader.setLocation(Asn1View.class.getResource("x509CertificateView.fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.initComponents();
    }

    public void setValue(Certificate value) {
        this.valueProperty.setValue(value);
    }

    public Certificate getValue() {
        return this.valueProperty.getValue();
    }

    public ObjectProperty<Certificate> valueProperty() {
        return this.valueProperty;
    }

    private void initComponents() {
        this.valueProperty.addListener(this::fill);
    }

    private void fill(ObservableValue<? extends Certificate> observable, Certificate oldValue, Certificate newValue) {
        this.holder = new X509CertificateHolder(newValue);
        this.issuer.setValue(this.holder.getIssuer());
        this.subject.setValue(this.holder.getSubject());
    }

    public static X509CertificateView getInstance(Stage stage){
        final X509CertificateView instance = new X509CertificateView();
        stage.setScene(new Scene(instance));

        return instance;
    }
}
