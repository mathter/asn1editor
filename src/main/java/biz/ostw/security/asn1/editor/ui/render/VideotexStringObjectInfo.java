package biz.ostw.security.asn1.editor.ui.render;

import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERVideotexString;

public class VideotexStringObjectInfo extends ASN1ObjectInfo<DERVideotexString> {

    @Override
    public String toString(DERVideotexString value) {
        StringBuffer sb = new StringBuffer("VIDEOTEXT STRING: ");
        sb.append(value.toString());

        return sb.toString();
    }

    @Override
    protected Class<? extends ASN1Primitive> getKey() {
        return DERVideotexString.class;
    }
}
