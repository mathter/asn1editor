package biz.ostw.security.editor.objinfo;

import org.bouncycastle.asn1.DERIA5String;
import org.bouncycastle.util.encoders.Hex;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class IA5StringObjectInfo extends ASN1ObjectInfo.Default<DERIA5String> {

    @Override
    public String toString(DERIA5String value) {
        StringBuffer sb = new StringBuffer("IA5 STRING: ");
        sb.append(value.getString());

        return sb.toString();
    }

    @Override
    protected Class<DERIA5String> getKey() {
        return DERIA5String.class;
    }

    @Override
    public Collection<ASN1ObjectDescription> descriptions(DERIA5String value) {

        final List<ASN1ObjectDescription> result = new ArrayList<>();

        try {
            result.add(ASN1ObjectDescription.builder().name("Type").object(value).value("ASN1.DERIA5String").build());
            result.add(ASN1ObjectDescription.builder().name("Value").object(value).value(String.valueOf(value.getString())).build());
            result.add(ASN1ObjectDescription.builder().name("Octets").object(value).value(Hex.toHexString(value.getOctets())).build());
            result.add(ASN1ObjectDescription.builder().name("Length").object(value).value(String.valueOf(value.toString().length())).build());

            result.add(this.raw(value));
        } catch (Exception e) {
            throw new RuntimeException("Can't get bytes of " + value + "!", e);
        }

        return result;
    }
}
