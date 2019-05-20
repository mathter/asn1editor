package biz.ostw.security.editor.content;

import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;

import java.io.IOException;

public class RsaPublicKeyContent extends SubjectPublicKeyInfoContent {
    public RsaPublicKeyContent(SubjectPublicKeyInfo object) {
        super(object);
    }

    @Override
    public String getDescription() {
        return ContentDescriptionFactory.getDescription("description.key.rsa.public");
    }

    @Override
    public byte[] getEncoded() throws IOException {
        return new byte[0];
    }
}
