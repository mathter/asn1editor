package biz.ostw.security.asn1.editor.ui.render;

import org.bouncycastle.asn1.ASN1Primitive;

public class Renderer<T extends ASN1Primitive> {

    private final T value;

    public Renderer(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        ASN1ObjectInfo objectInfo = ASN1ObjectInfo.get(this.value.getClass());

        return objectInfo.toString(this.value);
    }
}
