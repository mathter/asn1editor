package biz.ostw.security.editor.content;

import org.bouncycastle.asn1.x509.AttributeCertificate;

public class X509AttributeCertificateContent extends Content<AttributeCertificate> {
    public X509AttributeCertificateContent(AttributeCertificate object) {
        super(object);
    }

    @Override
    public String getDescription() {
        return ContentDescriptionFactory.getDescription("description.x509.certificate.attribute");
    }
}
