package biz.ostw.security.editor.ui.control.provider;

import biz.ostw.security.editor.content.ContentAnalyzer;
import biz.ostw.security.editor.content.LeafContent;
import biz.ostw.security.editor.content.X509CertificateContent;
import biz.ostw.security.editor.ui.control.Asn1MenuProvider;
import biz.ostw.security.editor.ui.control.X509CertificateView;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.x509.Certificate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class CertificateAsn1MenuProvider extends Asn1MenuProvider {

    private static final String KEY = "certificate";

    private final ResourceBundle resources = ResourceBundle.getBundle("biz.ostw.security.editor.ui.control.message");

    @Override
    public List<MenuItem> get(ASN1Primitive asn1) {

        final List<MenuItem> result = new ArrayList<>();

        this.openAs(asn1, result);

        return result;
    }

    private List<MenuItem> openAs(ASN1Primitive asn1, List<MenuItem> collector) {

        this.openAsCertificate(asn1).ifPresent(menuItem -> collector.add(menuItem));

        return collector;
    }

    private Optional<MenuItem> openAsCertificate(ASN1Primitive asn1) {
        List<X509CertificateContent> certificateContents = new ArrayList<>();

        try {
            for (LeafContent<?> content : ContentAnalyzer.analyze(asn1)) {
                if (content instanceof X509CertificateContent) {
                    certificateContents.add((X509CertificateContent) content);
                }
            }

            int size;

            if ((size = certificateContents.size()) > 0) {
                final X509CertificateContent content = certificateContents.get(0);
                final Certificate certificate = content.getObject();
                final MenuItem menuItem = new MenuItem("Open as " + content.getDescription());

                menuItem.setOnAction(event -> {
                    final Stage stage = new Stage();
                    final X509CertificateView x509CertificateView = X509CertificateView.getInstance(stage);

                    stage.setTitle(resources.getString("biz.ostw.security.asn1.editor.ui.control.X509CertificateView.title"));

                    x509CertificateView.setValue(certificate);

                    stage.show();
                });

                return Optional.ofNullable(menuItem);
            } else {
                return Optional.ofNullable(null);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object key() {
        return KEY;
    }
}
