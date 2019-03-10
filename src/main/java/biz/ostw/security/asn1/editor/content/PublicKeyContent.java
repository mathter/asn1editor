package biz.ostw.security.asn1.editor.content;

import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;

public class PublicKeyContent extends SubjectPublicKeyInfoContent {
    public PublicKeyContent(SubjectPublicKeyInfo object) {
        super(object);
    }

    @Override
    public String getDescription() {
        return ContentDescriptionFactory.getDescription("description.key.public");
    }
}
