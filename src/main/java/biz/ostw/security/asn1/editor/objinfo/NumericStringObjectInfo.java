package biz.ostw.security.asn1.editor.objinfo;

import org.bouncycastle.asn1.DERNumericString;
import org.bouncycastle.util.encoders.Hex;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NumericStringObjectInfo extends ASN1ObjectInfo.Default<DERNumericString> {

    @Override
    public String toString(DERNumericString value) {
        StringBuffer sb = new StringBuffer("NUMERIC STRING: ");
        sb.append(value.getString());

        return sb.toString();
    }

    @Override
    protected Class<DERNumericString> getKey() {
        return DERNumericString.class;
    }

    @Override
    public Collection<ASN1ObjectDescription> descriptions(DERNumericString value) {

        final List<ASN1ObjectDescription> result = new ArrayList<>();

        try {
            result.add(ASN1ObjectDescription.builder().name("Type").object(value).value("ASN1.DERNumericString").build());
            result.add(ASN1ObjectDescription.builder().name("Value").object(value).value(String.valueOf(value.getString())).build());
            result.add(ASN1ObjectDescription.builder().name("Octets").object(value).value(Hex.toHexString(value.getOctets())).build());
            result.add(ASN1ObjectDescription.builder().name("Length").object(value).value(String.valueOf(value.getString()).length()).build());

            result.add(this.raw(value));
        } catch (Exception e) {
            throw new RuntimeException("Can't get bytes of " + value + "!", e);
        }

        return result;
    }
}
