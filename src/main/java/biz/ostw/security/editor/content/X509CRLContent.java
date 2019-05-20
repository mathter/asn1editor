package biz.ostw.security.editor.content;

import org.bouncycastle.asn1.x509.CertificateList;

import java.io.IOException;

public class X509CRLContent extends LeafContent<CertificateList> {

    protected X509CRLContent(CertificateList object) {
        super(object);
    }

    @Override
    public String getDescription() {
        return ContentDescriptionFactory.getDescription("description.x509.crl");
    }

    @Override
    public byte[] getEncoded() throws IOException {
        return new byte[0];
    }
}
