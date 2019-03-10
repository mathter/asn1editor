package biz.ostw.security.asn1.editor.objinfo;

import org.bouncycastle.asn1.ASN1GeneralizedTime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ASN1GeneralizedTimeObjectInfo extends ASN1ObjectInfo.Default<ASN1GeneralizedTime> {

    @Override
    public String toString(ASN1GeneralizedTime value) {
        StringBuilder sb = new StringBuilder();

        sb.append("SEQUENCE")
                .append(" (")
                .append(value.getTimeString())
                .append(")");

        return sb.toString();
    }

    @Override
    protected Class<ASN1GeneralizedTime> getKey() {
        return ASN1GeneralizedTime.class;
    }

    @Override
    public Collection<ASN1ObjectDescription> descriptions(ASN1GeneralizedTime value) {

        final List<ASN1ObjectDescription> result = new ArrayList<>();

        result.add(ASN1ObjectDescription.builder().name("Type").object(value).value("ASN1.GeneralizedTime").build());
        result.add(ASN1ObjectDescription.builder().name("Value").object(value).value(value.getTimeString()).build());

        try {
            result.add(this.raw(value));
        } catch (Exception e) {
            throw new RuntimeException("Can't get bytes of " + value + "!", e);
        }

        return result;
    }
}
