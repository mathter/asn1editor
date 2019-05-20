package biz.ostw.security.editor.content;

import org.bouncycastle.asn1.ASN1Encodable;

public abstract class Content<T extends ASN1Encodable> {

    private T object;

    protected Content(T object) {
        this.object = object;
    }

    public T getObject() {
        return this.object;
    }

    public abstract String getDescription();
}
