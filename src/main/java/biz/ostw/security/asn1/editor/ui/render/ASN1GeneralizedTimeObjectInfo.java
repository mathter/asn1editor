package biz.ostw.security.asn1.editor.ui.render;

import org.bouncycastle.asn1.ASN1GeneralizedTime;
import org.bouncycastle.asn1.ASN1Primitive;

public class ASN1GeneralizedTimeObjectInfo extends ASN1ObjectInfo<ASN1GeneralizedTime> {

    @Override
    public String toString(ASN1GeneralizedTime value) {
        StringBuilder sb = new StringBuilder();

        sb.append("SEQUENCE")
                .append(" (")
                .append(value.getTimeString())
                .append(")");

        return sb.toString();
    }

    @Override
    protected Class<? extends ASN1Primitive> getKey() {
        return ASN1GeneralizedTime.class;
    }
}
