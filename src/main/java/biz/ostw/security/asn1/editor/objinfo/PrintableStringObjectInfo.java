package biz.ostw.security.asn1.editor.objinfo;

import org.bouncycastle.asn1.DERPrintableString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PrintableStringObjectInfo extends ASN1ObjectInfo.Default<DERPrintableString> {

    @Override
    public String toString(DERPrintableString value) {
        StringBuffer sb = new StringBuffer("PRINTABLE STRING: ");
        sb.append(value.toString());

        return sb.toString();
    }

    @Override
    protected Class<DERPrintableString> getKey() {
        return DERPrintableString.class;
    }

    @Override
    public Collection<ASN1ObjectDescription> descriptions(DERPrintableString value) {

        final List<ASN1ObjectDescription> result = new ArrayList<>();

        try {
            result.add(ASN1ObjectDescription.builder().name("Type").object(value).value("ASN1.DERPrintableString").build());
            result.add(ASN1ObjectDescription.builder().name("Value").object(value).value(String.valueOf(value.toString())).build());
            result.add(ASN1ObjectDescription.builder().name("Length").object(value).value(String.valueOf(value.toString().length())).build());

            result.add(this.raw(value));
        } catch (Exception e) {
            throw new RuntimeException("Can't get bytes of " + value + "!", e);
        }

        return result;
    }
}
