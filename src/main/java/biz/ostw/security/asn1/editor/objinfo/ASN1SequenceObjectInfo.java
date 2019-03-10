package biz.ostw.security.asn1.editor.objinfo;

import org.bouncycastle.asn1.ASN1Sequence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ASN1SequenceObjectInfo extends ASN1ObjectInfo.Default<ASN1Sequence> {

    @Override
    public String toString(ASN1Sequence asn1Primitive) {

        StringBuilder sb = new StringBuilder();

        sb.append("SEQUENCE")
                .append(" (")
                .append(asn1Primitive.size())
                .append(")");

        return sb.toString();
    }

    @Override
    protected Class<ASN1Sequence> getKey() {
        return ASN1Sequence.class;
    }

    @Override
    public Collection<ASN1ObjectDescription> descriptions(ASN1Sequence value) {

        final List<ASN1ObjectDescription> result = new ArrayList<>();

        result.add(ASN1ObjectDescription.builder().name("Type").object(value).value("ASN1.Sequence").build());
        result.add(ASN1ObjectDescription.builder().name("Length").object(value).value(value.size()).build());

        try {
            result.add(this.raw(value));
        } catch (Exception e) {
            throw new RuntimeException("Can't get bytes of " + value + "!", e);
        }

        return result;
    }
}
