package biz.ostw.security.asn1.editor.ui.render;

import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;

public class ASN1OctetStringObjectInfo extends ASN1ObjectInfo<ASN1OctetString> {

    @Override
    public String toString(ASN1OctetString value) {
        StringBuilder sb = new StringBuilder("OCTET STRING: ");
        sb.append(value.toString());

        return sb.toString();
    }

    @Override
    protected Class<? extends ASN1Primitive> getKey() {
        return ASN1OctetString.class;
    }
}
