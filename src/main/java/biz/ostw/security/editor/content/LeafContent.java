package biz.ostw.security.editor.content;

import org.bouncycastle.asn1.ASN1Encodable;

import java.io.IOException;

public abstract class LeafContent<T extends ASN1Encodable> implements Content {

    private T object;

    protected LeafContent(T object) {
        this.object = object;
    }

    public T getObject() {
        return this.object;
    }

    @Override
    public byte[] getEncoded() throws IOException {
        return this.object != null ? this.object.toASN1Primitive().getEncoded() : null;
    }

    public abstract String getDescription();
}
