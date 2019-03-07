package biz.ostw.security.asn1.editor.ui.render;

import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERVisibleString;

public class VisibleStringObjectInfo extends ASN1ObjectInfo<DERVisibleString> {

    @Override
    public String toString(DERVisibleString value) {
        StringBuffer sb = new StringBuffer("VISIBLE STRING: ");
        sb.append(value.toString());

        return sb.toString();
    }

    @Override
    protected Class<? extends ASN1Primitive> getKey() {
        return DERVisibleString.class;
    }
}
