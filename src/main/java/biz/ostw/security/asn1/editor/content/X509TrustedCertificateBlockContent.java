package biz.ostw.security.asn1.editor.content;

import org.bouncycastle.openssl.X509TrustedCertificateBlock;

public class X509TrustedCertificateBlockContent extends Content<X509TrustedCertificateBlock> {

    public X509TrustedCertificateBlockContent(X509TrustedCertificateBlock object) {
        super(object);
    }

    @Override
    public String getDescription() {
        return ContentDescriptionFactory.getDescription("description.x509.certificate.trusted");
    }
}
