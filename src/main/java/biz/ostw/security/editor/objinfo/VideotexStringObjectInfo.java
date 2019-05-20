package biz.ostw.security.editor.objinfo;

import org.bouncycastle.asn1.DERVideotexString;
import org.bouncycastle.util.encoders.Hex;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class VideotexStringObjectInfo extends ASN1ObjectInfo.Default<DERVideotexString> {

    @Override
    public String toString(DERVideotexString value) {
        StringBuffer sb = new StringBuffer("VIDEOTEXT STRING: ");
        sb.append(value.toString());

        return sb.toString();
    }

    @Override
    protected Class<DERVideotexString> getKey() {
        return DERVideotexString.class;
    }

    @Override
    public Collection<ASN1ObjectDescription> descriptions(DERVideotexString value) {

        final List<ASN1ObjectDescription> result = new ArrayList<>();

        try {
            result.add(ASN1ObjectDescription.builder().name("Type").object(value).value("ASN1.DERVideotexString").build());
            result.add(ASN1ObjectDescription.builder().name("Value").object(value).value(String.valueOf(value.toString())).build());
            result.add(ASN1ObjectDescription.builder().name("Octets").object(value).value(Hex.toHexString(value.getOctets())).build());
            result.add(ASN1ObjectDescription.builder().name("Length").object(value).value(String.valueOf(value.toString().length())).build());

            result.add(this.raw(value));
        } catch (Exception e) {
            throw new RuntimeException("Can't get bytes of " + value + "!", e);
        }

        return result;
    }
}
