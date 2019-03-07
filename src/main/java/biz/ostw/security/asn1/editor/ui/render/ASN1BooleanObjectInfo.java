package biz.ostw.security.asn1.editor.ui.render;

import org.bouncycastle.asn1.ASN1Boolean;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;

public class ASN1BooleanObjectInfo extends ASN1ObjectInfo<ASN1Boolean> {

    @Override
    public String toString(ASN1Boolean value) {
        StringBuilder sb = new StringBuilder();

        sb.append("BOOLEAN ")
                .append(value.isTrue() ? "true" : "false");

        return sb.toString();
    }

    @Override
    protected Class<? extends ASN1Primitive> getKey() {
        return ASN1ObjectIdentifier.class;
    }
}
