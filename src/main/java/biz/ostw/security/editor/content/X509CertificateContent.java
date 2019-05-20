package biz.ostw.security.editor.content;

import org.bouncycastle.asn1.x509.Certificate;

public class X509CertificateContent extends LeafContent<Certificate> {

    X509CertificateContent(Certificate root) {
        super(root);
    }

    @Override
    public String getDescription() {
        return ContentDescriptionFactory.getDescription("description.x509.certificate");
    }
}