package biz.ostw.security.editor.objinfo;

import org.bouncycastle.asn1.DERBMPString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BPMStringObjectInfo extends ASN1ObjectInfo.Default<DERBMPString> {

    @Override
    public String toString(DERBMPString value) {
        StringBuffer sb = new StringBuffer("BPM STRING: ");
        sb.append(value.getString());

        return sb.toString();
    }

    @Override
    protected Class<DERBMPString> getKey() {
        return DERBMPString.class;
    }

    @Override
    public Collection<ASN1ObjectDescription> descriptions(DERBMPString value) {

        final List<ASN1ObjectDescription> result = new ArrayList<>();

        try {
            result.add(ASN1ObjectDescription.builder().name("Type").object(value).value("ASN1.DERBMPString").build());
            result.add(ASN1ObjectDescription.builder().name("Value").object(value).value(String.valueOf(value.getString())).build());
            result.add(ASN1ObjectDescription.builder().name("Length").object(value).value(String.valueOf(value.getString()).length()).build());

            result.add(this.raw(value));
        } catch (Exception e) {
            throw new RuntimeException("Can't get bytes of " + value + "!", e);
        }

        return result;
    }
}
