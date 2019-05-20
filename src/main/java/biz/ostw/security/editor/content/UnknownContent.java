package biz.ostw.security.editor.content;

import org.bouncycastle.asn1.ASN1Primitive;

import java.io.IOException;

public class UnknownContent extends LeafContent<ASN1Primitive> {

    public UnknownContent(ASN1Primitive root) {
        super(root);
    }

    @Override
    public ASN1Primitive getObject() {
        return null;
    }

    @Override
    public String getDescription() {
        return ContentDescriptionFactory.getDescription("description.unknow");
    }

    @Override
    public byte[] getEncoded() throws IOException {
        return new byte[0];
    }
}