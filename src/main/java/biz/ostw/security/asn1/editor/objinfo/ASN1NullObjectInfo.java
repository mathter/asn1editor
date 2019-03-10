package biz.ostw.security.asn1.editor.objinfo;

import org.bouncycastle.asn1.ASN1Null;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ASN1NullObjectInfo extends ASN1ObjectInfo.Default<ASN1Null> {

    @Override
    public String toString(ASN1Null value) {
        return value.toString();
    }

    @Override
    protected Class<ASN1Null> getKey() {
        return ASN1Null.class;
    }

    @Override
    public Collection<ASN1ObjectDescription> descriptions(ASN1Null value) {

        final List<ASN1ObjectDescription> result = new ArrayList<>();

        result.add(ASN1ObjectDescription.builder().name("Type").object(value).value("ASN1.Null").build());
        result.add(ASN1ObjectDescription.builder().name("Value").object(value).value(value.toString()).build());

        try {
            result.add(this.raw(value));
        } catch (Exception e) {
            throw new RuntimeException("Can't get bytes of " + value + "!", e);
        }

        return result;
    }
}
