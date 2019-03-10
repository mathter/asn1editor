package biz.ostw.security.asn1.editor.content;

import org.bouncycastle.cert.X509AttributeCertificateHolder;

public class X509AttributeCertificateContent extends Content<X509AttributeCertificateHolder> {
    public X509AttributeCertificateContent(X509AttributeCertificateHolder object) {
        super(object);
    }

    @Override
    public String getDescription() {
        return ContentDescriptionFactory.getDescription("description.x509.certificate.attribute");
    }
}
