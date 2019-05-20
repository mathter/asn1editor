package biz.ostw.security.editor.content;

import org.bouncycastle.asn1.x509.CertificateList;

public class X509CRLContent extends Content<CertificateList> {

    protected X509CRLContent(CertificateList object) {
        super(object);
    }

    @Override
    public String getDescription() {
        return ContentDescriptionFactory.getDescription("description.x509.crl");
    }
}
