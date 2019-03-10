package biz.ostw.security.asn1.editor.content;

import org.bouncycastle.cert.X509CertificateHolder;

public class X509CertificateContent extends Content<X509CertificateHolder> {

    X509CertificateContent(X509CertificateHolder root) {
        super(root);
    }

    @Override
    public String getDescription() {
        return ContentDescriptionFactory.getDescription("description.x509.certificate");
    }
}