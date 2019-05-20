package biz.ostw.security.editor.objinfo;

import org.bouncycastle.asn1.ASN1Set;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ASN1SetObjectInfo extends ASN1ObjectInfo.Default<ASN1Set> {

    @Override
    public String toString(ASN1Set value) {
        StringBuilder sb = new StringBuilder();

        sb.append("SET (").append(value.size()).append(")");

        return sb.toString();
    }

    @Override
    protected Class<ASN1Set> getKey() {
        return ASN1Set.class;
    }

    @Override
    public Collection<ASN1ObjectDescription> descriptions(ASN1Set value) {

        final List<ASN1ObjectDescription> result = new ArrayList<>();

        result.add(ASN1ObjectDescription.builder().name("Type").object(value).value("ASN1.Set").build());
        result.add(ASN1ObjectDescription.builder().name("Length").object(value).value(value.size()).build());

        try {
            result.add(this.raw(value));
        } catch (Exception e) {
            throw new RuntimeException("Can't get bytes of " + value + "!", e);
        }

        return result;
    }
}
