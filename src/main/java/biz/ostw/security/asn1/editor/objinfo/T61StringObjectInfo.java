package biz.ostw.security.asn1.editor.objinfo;

import org.bouncycastle.asn1.DERT61String;
import org.bouncycastle.util.encoders.Hex;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class T61StringObjectInfo extends ASN1ObjectInfo.Default<DERT61String> {

    @Override
    public String toString(DERT61String value) {
        StringBuffer sb = new StringBuffer("T61 STRING: ");
        sb.append(value.toString());

        return sb.toString();
    }

    @Override
    protected Class<DERT61String> getKey() {
        return DERT61String.class;
    }

    @Override
    public Collection<ASN1ObjectDescription> descriptions(DERT61String value) {

        final List<ASN1ObjectDescription> result = new ArrayList<>();

        try {
            result.add(ASN1ObjectDescription.builder().name("Type").object(value).value("ASN1.DERT61String").build());
            result.add(ASN1ObjectDescription.builder().name("Value").object(value).value(String.valueOf(value.toString())).build());
            result.add(ASN1ObjectDescription.builder().name("Octets").object(value).value(String.valueOf(Hex.toHexString(value.getOctets()))).build());
            result.add(ASN1ObjectDescription.builder().name("Length").object(value).value(String.valueOf(value.toString().length())).build());

            result.add(this.raw(value));
        } catch (Exception e) {
            throw new RuntimeException("Can't get bytes of " + value + "!", e);
        }

        return result;
    }
}
