package biz.ostw.security.asn1.editor.objinfo;

import org.bouncycastle.asn1.ASN1Boolean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ASN1BooleanObjectInfo extends ASN1ObjectInfo.Default<ASN1Boolean> {

    @Override
    public String toString(ASN1Boolean value) {
        StringBuilder sb = new StringBuilder();

        sb.append("BOOLEAN: ")
                .append(value.isTrue() ? "true" : "false");

        return sb.toString();
    }

    @Override
    protected Class<ASN1Boolean> getKey() {
        return ASN1Boolean.class;
    }

    @Override
    public Collection<ASN1ObjectDescription> descriptions(ASN1Boolean value) {

        final List<ASN1ObjectDescription> result = new ArrayList<>();

        result.add(ASN1ObjectDescription.builder().name("Type").object(value).value("ASN1.Boolean").build());
        result.add(ASN1ObjectDescription.builder().name("Value").object(value).value(value.isTrue() ? "true" : "false").build());

        try {
            result.add(this.raw(value));
        } catch (Exception e) {
            throw new RuntimeException("Can't get bytes of " + value + "!", e);
        }

        return result;
    }
}
