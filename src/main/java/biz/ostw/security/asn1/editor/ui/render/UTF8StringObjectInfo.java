package biz.ostw.security.asn1.editor.ui.render;

import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERUTF8String;

public class UTF8StringObjectInfo extends ASN1ObjectInfo<DERUTF8String> {

    @Override
    public String toString(DERUTF8String value) {
        StringBuffer sb = new StringBuffer("UTF8 STRING: ");
        sb.append(value.toString());

        return sb.toString();
    }

    @Override
    protected Class<? extends ASN1Primitive> getKey() {
        return DERUTF8String.class;
    }
}
