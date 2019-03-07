package biz.ostw.security.asn1.editor.ui.render;

import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERT61String;

public class T61StringObjectInfo extends ASN1ObjectInfo<DERT61String> {

    @Override
    public String toString(DERT61String value) {
        StringBuffer sb = new StringBuffer("T61 STRING: ");
        sb.append(value.toString());

        return sb.toString();
    }

    @Override
    protected Class<? extends ASN1Primitive> getKey() {
        return DERT61String.class;
    }
}
