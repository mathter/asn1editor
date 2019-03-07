package biz.ostw.security.asn1.editor.ui.render;

import org.bouncycastle.asn1.ASN1ApplicationSpecific;
import org.bouncycastle.asn1.ASN1Primitive;

public class ASN1ApplicationSpecificObjectInfo extends ASN1ObjectInfo<ASN1ApplicationSpecific> {

    @Override
    public String toString(ASN1ApplicationSpecific value) {
        StringBuilder sb = new StringBuilder();

        sb.append("ASN1.ApplicationSpecific (tag=")
                .append(value.getApplicationTag())
                .append(")");

        return sb.toString();
    }

    @Override
    protected Class<? extends ASN1Primitive> getKey() {
        return ASN1ApplicationSpecific.class;
    }
}
