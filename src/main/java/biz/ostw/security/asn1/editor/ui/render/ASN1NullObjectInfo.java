package biz.ostw.security.asn1.editor.ui.render;

import org.bouncycastle.asn1.ASN1Null;
import org.bouncycastle.asn1.ASN1Primitive;

public class ASN1NullObjectInfo extends ASN1ObjectInfo<ASN1Null> {

    @Override
    public String toString(ASN1Null value) {
        return value.toString();
    }

    @Override
    protected Class<? extends ASN1Primitive> getKey() {
        return ASN1Null.class;
    }
}
