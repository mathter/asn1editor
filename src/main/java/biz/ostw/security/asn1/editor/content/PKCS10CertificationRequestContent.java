package biz.ostw.security.asn1.editor.content;

import org.bouncycastle.pkcs.PKCS10CertificationRequest;

public class PKCS10CertificationRequestContent extends Content<PKCS10CertificationRequest> {

    public PKCS10CertificationRequestContent(PKCS10CertificationRequest object) {
        super(object);
    }

    @Override
    public String getDescription() {
        return ContentDescriptionFactory.getDescription("description.pkcs10");
    }
}