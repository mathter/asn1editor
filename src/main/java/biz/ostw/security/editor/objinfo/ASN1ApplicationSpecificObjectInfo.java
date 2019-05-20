package biz.ostw.security.editor.objinfo;

import org.bouncycastle.asn1.ASN1ApplicationSpecific;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ASN1ApplicationSpecificObjectInfo extends ASN1ObjectInfo.Default<ASN1ApplicationSpecific> {

    @Override
    public String toString(ASN1ApplicationSpecific value) {
        StringBuilder sb = new StringBuilder();

        sb.append("ASN1.ApplicationSpecific (tag=")
                .append(value.getApplicationTag())
                .append(")");

        return sb.toString();
    }

    @Override
    protected Class<ASN1ApplicationSpecific> getKey() {
        return ASN1ApplicationSpecific.class;
    }

    @Override
    public Collection<ASN1ObjectDescription> descriptions(ASN1ApplicationSpecific value) {

        final List<ASN1ObjectDescription> result = new ArrayList<>();

        result.add(ASN1ObjectDescription.builder().name("Type").object(value).value("ASN1.ApplicationSpecific").build());
        result.add(ASN1ObjectDescription.builder().name("ASN1.ApplicationSpecific").object(value).value("tag=" + value.getApplicationTag()).build());

        try {
            result.add(this.raw(value));
        } catch (Exception e) {
            throw new RuntimeException("Can't get bytes of " + value + "!", e);
        }

        return result;
    }
}
