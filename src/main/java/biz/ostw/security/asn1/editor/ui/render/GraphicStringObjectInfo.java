package biz.ostw.security.asn1.editor.ui.render;

import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERGraphicString;

public class GraphicStringObjectInfo extends ASN1ObjectInfo<DERGraphicString> {

    @Override
    public String toString(DERGraphicString value) {
        StringBuffer sb = new StringBuffer("GRAPHIC STRING: ");
        sb.append(value.toString());

        return sb.toString();
    }

    @Override
    protected Class<? extends ASN1Primitive> getKey() {
        return DERGraphicString.class;
    }
}
