package biz.ostw.security.asn1.editor.ui.render;

import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;

public class ASN1SequenceObjectInfo extends ASN1ObjectInfo<ASN1Sequence> {

    @Override
    public String toString(ASN1Sequence asn1Primitive) {

        StringBuilder sb = new StringBuilder();

        sb.append("SEQUENCE")
                .append(" (")
                .append(asn1Primitive.size())
                .append(")");

        return sb.toString();
    }

    @Override
    protected Class<? extends ASN1Primitive> getKey() {
        return ASN1Sequence.class;
    }
}
