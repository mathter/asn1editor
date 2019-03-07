package biz.ostw.security.asn1.editor.ui.render;

import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERGeneralString;

public class GeneralStringObjectInfo extends ASN1ObjectInfo<DERGeneralString> {

    @Override
    public String toString(DERGeneralString value) {
        StringBuffer sb = new StringBuffer("GENERAL STRING: ");
        sb.append(value.toString());

        return sb.toString();
    }

    @Override
    protected Class<? extends ASN1Primitive> getKey() {
        return DERGeneralString.class;
    }
}
