package biz.ostw.security.asn1.editor.objinfo;

import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.util.encoders.Hex;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ASN1OctetStringObjectInfo extends ASN1ObjectInfo.Default<ASN1OctetString> {

    @Override
    public String toString(ASN1OctetString value) {
        StringBuilder sb = new StringBuilder("OCTET STRING: ");
        sb.append(Hex.toHexString(value.getOctets()));

        return sb.toString();
    }

    @Override
    protected Class<ASN1OctetString> getKey() {
        return ASN1OctetString.class;
    }

    @Override
    public Collection<ASN1ObjectDescription> descriptions(ASN1OctetString value) {

        final List<ASN1ObjectDescription> result = new ArrayList<>();

        result.add(ASN1ObjectDescription.builder().name("Type").object(value).value("ASN1.OctetString").build());
        result.add(ASN1ObjectDescription.builder().name("Value").object(value).value(Hex.toHexString(value.getOctets())).build());
        result.add(ASN1ObjectDescription.builder().name("Length").object(value).value(value.getOctets().length).build());

        try {
            result.add(this.raw(value));
        } catch (Exception e) {
            throw new RuntimeException("Can't get bytes of " + value + "!", e);
        }

        return result;
    }
}
