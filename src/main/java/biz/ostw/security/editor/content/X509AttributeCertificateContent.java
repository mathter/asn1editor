package biz.ostw.security.editor.content;

import org.bouncycastle.asn1.x509.AttributeCertificate;

import java.io.IOException;

public class X509AttributeCertificateContent extends LeafContent<AttributeCertificate> {
    public X509AttributeCertificateContent(AttributeCertificate object) {
        super(object);
    }

    @Override
    public String getDescription() {
        return ContentDescriptionFactory.getDescription("description.x509.certificate.attribute");
    }

    @Override
    public byte[] getEncoded() throws IOException {
        return new byte[0];
    }
}
