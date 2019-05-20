package biz.ostw.security.editor.content;

import org.bouncycastle.asn1.pkcs.CertificationRequest;

public class PKCS10CertificationRequestContent extends LeafContent<CertificationRequest> {

    public PKCS10CertificationRequestContent(CertificationRequest object) {
        super(object);
    }

    @Override
    public String getDescription() {
        return ContentDescriptionFactory.getDescription("description.pkcs10");
    }
}