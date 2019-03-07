package biz.ostw.security.asn1.editor.ui.render;

import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Primitive;

public class ASN1IntegerObjectInfo extends ASN1ObjectInfo<ASN1Integer> {

    @Override
    public String toString(ASN1Integer value) {

        return value != null ? value.toString() : null;
    }

    @Override
    protected Class<? extends ASN1Primitive> getKey() {
        return ASN1Integer.class;
    }
}
