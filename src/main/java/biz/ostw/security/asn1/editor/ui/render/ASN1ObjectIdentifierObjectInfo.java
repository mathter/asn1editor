package biz.ostw.security.asn1.editor.ui.render;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;

public class ASN1ObjectIdentifierObjectInfo extends ASN1ObjectInfo<ASN1ObjectIdentifier> {

    @Override
    public String toString(ASN1ObjectIdentifier value) {
        StringBuilder sb = new StringBuilder();

        sb.append("OBJECT IDENTIFIER ")
                .append(value.getId());

        return sb.toString();
    }

    @Override
    protected Class<? extends ASN1Primitive> getKey() {
        return ASN1ObjectIdentifier.class;
    }
}
