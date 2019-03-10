package biz.ostw.security.asn1.editor.objinfo;

import org.bouncycastle.asn1.ASN1TaggedObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ASN1TaggedObjectObjectInfo extends ASN1ObjectInfo.Default<ASN1TaggedObject> {

    @Override
    public String toString(ASN1TaggedObject value) {
        StringBuilder sb = new StringBuilder();

        sb.append("TAGGED OBJECT")
                .append(" (")
                .append(value.getTagNo())
                .append(")");

        return sb.toString();
    }

    @Override
    protected Class<ASN1TaggedObject> getKey() {
        return ASN1TaggedObject.class;
    }

    @Override
    public Collection<ASN1ObjectDescription> descriptions(ASN1TaggedObject value) {

        final List<ASN1ObjectDescription> result = new ArrayList<>();

        result.add(ASN1ObjectDescription.builder().name("Type").object(value).value("ASN1.TaggedObject").build());
        result.add(ASN1ObjectDescription.builder().name("Tag").object(value).value(value.getTagNo()).build());

        try {
            result.add(this.raw(value));
        } catch (Exception e) {
            throw new RuntimeException("Can't get bytes of " + value + "!", e);
        }

        return result;
    }
}
