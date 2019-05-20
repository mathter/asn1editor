package biz.ostw.security.editor.content;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;

public class PrivateKeyContent extends Content<PrivateKeyInfo> {

    protected PrivateKeyContent(PrivateKeyInfo object) {
        super(object);
    }

    @Override
    public String getDescription() {
        return ContentDescriptionFactory.getDescription("description.key.private");
    }
}
