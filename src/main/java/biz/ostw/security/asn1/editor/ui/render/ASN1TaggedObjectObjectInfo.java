package biz.ostw.security.asn1.editor.ui.render;

import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1TaggedObject;

public class ASN1TaggedObjectObjectInfo extends ASN1ObjectInfo<ASN1TaggedObject> {

    @Override
    public String toString(ASN1TaggedObject value) {
        StringBuilder sb = new StringBuilder();

        sb.append("TAGGED OBJECT")
                .append(" (")
                .append(value.getTagNo())
                .append(")");

        return sb.toString();
    }

    @Override
    protected Class<? extends ASN1Primitive> getKey() {
        return ASN1TaggedObject.class;
    }
}
