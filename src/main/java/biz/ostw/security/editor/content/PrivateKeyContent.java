package biz.ostw.security.editor.content;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;

import java.io.IOException;

public class PrivateKeyContent extends LeafContent<PrivateKeyInfo> {

    protected PrivateKeyContent(PrivateKeyInfo object) {
        super(object);
    }

    @Override
    public String getDescription() {
        return ContentDescriptionFactory.getDescription("description.key.private");
    }

    @Override
    public byte[] getEncoded() throws IOException {
        return new byte[0];
    }
}
