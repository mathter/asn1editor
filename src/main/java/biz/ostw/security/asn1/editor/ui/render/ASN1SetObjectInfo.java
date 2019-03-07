package biz.ostw.security.asn1.editor.ui.render;

import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Set;

public class ASN1SetObjectInfo extends ASN1ObjectInfo<ASN1Set> {

    @Override
    public String toString(ASN1Set value) {
        StringBuilder sb = new StringBuilder();

        sb.append("SET (").append(value.size()).append(")");

        return sb.toString();
    }

    @Override
    protected Class<? extends ASN1Primitive> getKey() {
        return ASN1Set.class;
    }
}
