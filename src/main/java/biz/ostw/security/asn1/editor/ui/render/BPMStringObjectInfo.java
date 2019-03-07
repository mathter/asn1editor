package biz.ostw.security.asn1.editor.ui.render;

import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERBMPString;

public class BPMStringObjectInfo extends ASN1ObjectInfo<DERBMPString> {

    @Override
    public String toString(DERBMPString value) {
        StringBuffer sb = new StringBuffer("BPM STRING: ");
        sb.append(value.toString());

        return sb.toString();
    }

    @Override
    protected Class<? extends ASN1Primitive> getKey() {
        return DERBMPString.class;
    }
}
