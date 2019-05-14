package biz.ostw.security.asn1.editor.objinfo;

import org.bouncycastle.asn1.ASN1BitString;
import org.bouncycastle.util.encoders.Hex;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ASN1BitStringObjectInfo extends ASN1ObjectInfo.Default<ASN1BitString> {

    @Override
    public String toString(ASN1BitString value) {
        StringBuffer sb = new StringBuffer("BIT STRING: ");
        sb.append(value.toString());

        return sb.toString();
    }

    @Override
    protected Class<ASN1BitString> getKey() {
        return ASN1BitString.class;
    }

    @Override
    public Collection<ASN1ObjectDescription> descriptions(ASN1BitString value) {

        final List<ASN1ObjectDescription> result = new ArrayList<>();

        result.add(ASN1ObjectDescription.builder().name("Type").object(value).value("BIT STRING").build());
        result.add(ASN1ObjectDescription.builder().name("Value").object(value).value(Hex.toHexString(value.getBytes())).build());
        result.add(ASN1ObjectDescription.builder().name("Octets").object(value).value(Hex.toHexString(value.getOctets())).build());
        result.add(ASN1ObjectDescription.builder().name("Length").object(value).value(value.getOctets().length).build());

        try {
            result.add(this.raw(value));
        } catch (Exception e) {
            throw new RuntimeException("Can't get bytes of " + value + "!", e);
        }

        return result;
    }
}
