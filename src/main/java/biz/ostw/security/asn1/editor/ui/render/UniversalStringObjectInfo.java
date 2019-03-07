package biz.ostw.security.asn1.editor.ui.render;

import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERUniversalString;

public class UniversalStringObjectInfo extends ASN1ObjectInfo<DERUniversalString> {

    @Override
    public String toString(DERUniversalString value) {
        StringBuffer sb = new StringBuffer("UNIVERSAL STRING: ");
        sb.append(value.toString());

        return sb.toString();
    }

    @Override
    protected Class<? extends ASN1Primitive> getKey() {
        return DERUniversalString.class;
    }
}
