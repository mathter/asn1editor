package biz.ostw.security.asn1.editor.ui.render;

import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERIA5String;

public class IA5StringObjectInfo extends ASN1ObjectInfo<DERIA5String> {

    @Override
    public String toString(DERIA5String value) {
        StringBuffer sb = new StringBuffer("IA5 STRING: ");
        sb.append(value.toString());

        return sb.toString();
    }

    @Override
    protected Class<? extends ASN1Primitive> getKey() {
        return DERIA5String.class;
    }
}
