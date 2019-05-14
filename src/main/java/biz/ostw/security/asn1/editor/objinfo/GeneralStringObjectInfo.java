package biz.ostw.security.asn1.editor.objinfo;

import org.bouncycastle.asn1.DERGeneralString;
import org.bouncycastle.util.encoders.Hex;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GeneralStringObjectInfo extends ASN1ObjectInfo.Default<DERGeneralString> {

    @Override
    public String toString(DERGeneralString value) {
        StringBuffer sb = new StringBuffer("GENERAL STRING: ");
        sb.append(value.getString());

        return sb.toString();
    }

    @Override
    protected Class<DERGeneralString> getKey() {
        return DERGeneralString.class;
    }

    @Override
    public Collection<ASN1ObjectDescription> descriptions(DERGeneralString value) {

        final List<ASN1ObjectDescription> result = new ArrayList<>();

        try {
            result.add(ASN1ObjectDescription.builder().name("Type").object(value).value("ASN1.DERGeneralString").build());
            result.add(ASN1ObjectDescription.builder().name("Value").object(value).value(String.valueOf(value.getString())).build());
            result.add(ASN1ObjectDescription.builder().name("Octets").object(value).value(Hex.toHexString(value.getOctets())).build());
            result.add(ASN1ObjectDescription.builder().name("Length").object(value).value(String.valueOf(value.getString().length())).build());

            result.add(this.raw(value));
        } catch (Exception e) {
            throw new RuntimeException("Can't get bytes of " + value + "!", e);
        }

        return result;
    }
}
