package biz.ostw.security.editor.content;

import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;

import java.io.IOException;

public class PublicKeyContent extends SubjectPublicKeyInfoContent {
    public PublicKeyContent(SubjectPublicKeyInfo object) {
        super(object);
    }

    @Override
    public String getDescription() {
        return ContentDescriptionFactory.getDescription("description.key.public");
    }

    @Override
    public byte[] getEncoded() throws IOException {
        return new byte[0];
    }
}
