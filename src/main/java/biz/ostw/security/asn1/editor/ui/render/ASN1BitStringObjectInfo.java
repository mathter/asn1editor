package biz.ostw.security.asn1.editor.ui.render;

import org.bouncycastle.asn1.ASN1BitString;
import org.bouncycastle.asn1.ASN1Primitive;

public class ASN1BitStringObjectInfo extends ASN1ObjectInfo<ASN1BitString> {

    @Override
    public String toString(ASN1BitString value) {
        StringBuffer sb = new StringBuffer("BIT STRING: ");
        sb.append(value.toString());

        return sb.toString();
    }

    @Override
    protected Class<? extends ASN1Primitive> getKey() {
        return ASN1BitString.class;
    }
}
