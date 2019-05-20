package biz.ostw.security.editor.objinfo;

import org.bouncycastle.asn1.ASN1Integer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ASN1IntegerObjectInfo extends ASN1ObjectInfo.Default<ASN1Integer> {

    @Override
    public String toString(ASN1Integer value) {

        StringBuilder sb = new StringBuilder("INTEGER: ");

        sb.append(String.valueOf(value.getValue()));

        return sb.toString();
    }

    @Override
    protected Class<ASN1Integer> getKey() {
        return ASN1Integer.class;
    }

    @Override
    public Collection<ASN1ObjectDescription> descriptions(ASN1Integer value) {

        final List<ASN1ObjectDescription> result = new ArrayList<>();

        result.add(ASN1ObjectDescription.builder().name("Type").object(value).value("ASN1.Integer").build());
        result.add(ASN1ObjectDescription.builder().name("Value").object(value).value(value.toString()).build());

        try {
            result.add(this.raw(value));
        } catch (Exception e) {
            throw new RuntimeException("Can't get bytes of " + value + "!", e);
        }

        return result;
    }
}
