package biz.ostw.security.editor.content;


import biz.ostw.security.asn1.CertificateTrustBlock;

import java.io.IOException;

public class X509TrustedCertificateBlockContent extends LeafContent<CertificateTrustBlock> {

    public X509TrustedCertificateBlockContent(CertificateTrustBlock object) {
        super(object);
    }

    @Override
    public String getDescription() {
        return ContentDescriptionFactory.getDescription("description.x509.certificate.trusted");
    }

    @Override
    public byte[] getEncoded() throws IOException {
        return new byte[0];
    }
}
