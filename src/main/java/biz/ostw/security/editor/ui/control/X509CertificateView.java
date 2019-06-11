package biz.ostw.security.editor.ui.control;

import biz.ostw.security.editor.objinfo.ASN1ObjectIdentifierInfoProvider;
import biz.ostw.security.editor.objinfo.provider.OidrefComASN1ObjectIdentifierInfoProviderFactory;
import biz.ostw.security.editor.ui.CommonService;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.Certificate;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.util.ResourceBundle;
import java.util.function.Function;

public class X509CertificateView extends VBox {

    private ResourceBundle resources = ResourceBundle.getBundle("biz.ostw.security.editor.ui.control.message");

    private final ObjectProperty<Certificate> valueProperty = new SimpleObjectProperty<>(this, "value", null);

    private X509CertificateHolder holder;

    @FXML
    private X500NameView issuer;

    @FXML
    private X500NameView subject;

    @FXML
    private TableView<KeyValue<String, ?>> commonTableView;

    @FXML
    private TableColumn<KeyValue<String, String>, ?> commonTableNameColumn;

    @FXML
    private TableColumn<KeyValue<String, String>, ?> commonTableValueColumn;

    private final ObservableList<KeyValue<String, ?>> commonValues = FXCollections.observableArrayList();

    @FXML
    private AlgorithmDataView signatureView;

    @FXML
    private AlgorithmDataView subjectPublicKeyInfoView;

    public X509CertificateView() {
        final FXMLLoader loader = new FXMLLoader(X509CertificateView.class.getResource("x509CertificateView.fxml"));

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

        this.commonTableView.setItems(this.commonValues);
        this.commonTableNameColumn.setCellValueFactory(param -> new SimpleObjectProperty(param.getValue().key()));
        this.commonTableValueColumn.setCellValueFactory(param -> new SimpleObjectProperty(param.getValue().value()));
    }

    private void fill(ObservableValue<? extends Certificate> observable, Certificate oldValue, Certificate newValue) {
        try {
            this.holder = new X509CertificateHolder(newValue);

            this.issuer.setValue(this.holder.getIssuer());
            this.subject.setValue(this.holder.getSubject());
            this.fillCommonTableView(this.holder);
            this.fillSignature(this.holder);
            this.fillSubjectPublicKeyInfo(this.holder);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void fillCommonTableView(X509CertificateHolder holder) {

        this.commonValues.clear();
        this.commonValues.add(new KeyValue<>(holder,
                h -> this.resources.getString("biz.ostw.security.asn1.editor.ui.control.X509CertificateView.common.table.key.notAfter"),
                h -> CommonService.dateTimeFormat().format(h.getNotAfter())
        ));
        this.commonValues.add(new KeyValue<>(holder,
                h -> this.resources.getString("biz.ostw.security.asn1.editor.ui.control.X509CertificateView.common.table.key.notBefor"),
                h -> CommonService.dateTimeFormat().format(h.getNotBefore())
        ));
        this.commonValues.add(new KeyValue<>(holder,
                h -> this.resources.getString("biz.ostw.security.asn1.editor.ui.control.X509CertificateView.common.table.key.serialNumner"),
                h -> h.getSerialNumber()
        ));
        this.commonValues.add(new KeyValue<>(holder,
                h -> this.resources.getString("biz.ostw.security.asn1.editor.ui.control.X509CertificateView.common.table.key.versionNumner"),
                h -> h.getVersionNumber()
        ));
    }

    private void fillSignature(X509CertificateHolder holder) throws IOException {
        AlgorithmIdentifier algorithmIdentifier = holder.getSignatureAlgorithm();

        this.signatureView.setAlgorithmIdentifier(algorithmIdentifier);
        this.signatureView.setData(holder.getSignature());
    }

    private void fillSubjectPublicKeyInfo(X509CertificateHolder holder) throws IOException {
        SubjectPublicKeyInfo subjectPublicKeyInfo = holder.getSubjectPublicKeyInfo();

        this.subjectPublicKeyInfoView.setAlgorithmIdentifier(subjectPublicKeyInfo.getAlgorithm());

        if (subjectPublicKeyInfo.getPublicKeyData() != null) {
            this.subjectPublicKeyInfoView.setData(subjectPublicKeyInfo.getPublicKeyData().getBytes());
        } else {
            this.subjectPublicKeyInfoView.setData(null);
        }
    }

    private void gotoRef(ASN1ObjectIdentifier identifier) throws IOException {
        ASN1ObjectIdentifierInfoProvider provider = CommonService.asn1ObjectIdentifierInfoProvider();

        if (provider instanceof OidrefComASN1ObjectIdentifierInfoProviderFactory) {
            final Desktop desktop;

            if (Desktop.isDesktopSupported() && (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.BROWSE)) {
                desktop.browse((URI) provider.info(identifier));
            }
        }
    }

    public static X509CertificateView getInstance(Stage stage){
        final X509CertificateView instance = new X509CertificateView();
        stage.setScene(new Scene(instance));

        return instance;
    }

    private static class KeyValue<K, V> {

        private final X509CertificateHolder holder;

        private final Function<X509CertificateHolder, K> key;

        private final Function<X509CertificateHolder, V> value;

        private KeyValue(X509CertificateHolder holder, Function<X509CertificateHolder, K> key, Function<X509CertificateHolder, V> value) {
            this.holder = holder;
            this.key = key;
            this.value = value;
        }

        public K key() {
            return this.key.apply(this.holder);
        }

        public V value() {
            return this.value.apply(this.holder);
        }
    }
}
