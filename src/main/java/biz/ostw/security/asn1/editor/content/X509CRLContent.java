package biz.ostw.security.asn1.editor.content;

import org.bouncycastle.cert.X509CRLHolder;

public class X509CRLContent extends Content<X509CRLHolder> {

    protected X509CRLContent(X509CRLHolder object) {
        super(object);
    }

    @Override
    public String getDescription() {
        return ContentDescriptionFactory.getDescription("description.x509.crl");
    }
}
