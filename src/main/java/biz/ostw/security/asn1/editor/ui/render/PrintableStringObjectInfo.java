package biz.ostw.security.asn1.editor.ui.render;

import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERPrintableString;

public class PrintableStringObjectInfo extends ASN1ObjectInfo<DERPrintableString> {

    @Override
    public String toString(DERPrintableString value) {
        StringBuffer sb = new StringBuffer("PRINTABLE STRING: ");
        sb.append(value.toString());

        return sb.toString();
    }

    @Override
    protected Class<? extends ASN1Primitive> getKey() {
        return DERPrintableString.class;
    }
}
