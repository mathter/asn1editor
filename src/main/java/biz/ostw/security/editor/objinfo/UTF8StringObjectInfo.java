package biz.ostw.security.editor.objinfo;

import org.bouncycastle.asn1.DERUTF8String;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UTF8StringObjectInfo extends ASN1ObjectInfo.Default<DERUTF8String> {

    @Override
    public String toString(DERUTF8String value) {
        StringBuffer sb = new StringBuffer("UTF8 STRING: ");
        sb.append(value.toString());

        return sb.toString();
    }

    @Override
    protected Class<DERUTF8String> getKey() {
        return DERUTF8String.class;
    }

    @Override
    public Collection<ASN1ObjectDescription> descriptions(DERUTF8String value) {

        final List<ASN1ObjectDescription> result = new ArrayList<>();

        try {
            result.add(ASN1ObjectDescription.builder().name("Type").object(value).value("ASN1.DERUTF8String").build());
            result.add(ASN1ObjectDescription.builder().name("Value").object(value).value(String.valueOf(value.toString())).build());
            result.add(ASN1ObjectDescription.builder().name("Length").object(value).value(String.valueOf(value.toString().length())).build());

            result.add(this.raw(value));
        } catch (Exception e) {
            throw new RuntimeException("Can't get bytes of " + value + "!", e);
        }

        return result;
    }
}
