package biz.ostw.security.editor.objinfo;

import org.bouncycastle.asn1.ASN1UTCTime;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ASN1UTCTimeObjectInfo extends ASN1ObjectInfo.Default<ASN1UTCTime> {

    @Override
    public String toString(ASN1UTCTime value) {
        StringBuilder sb = new StringBuilder();

        try {
            sb.append("UTCTime ")
                    .append(value.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    @Override
    protected Class<ASN1UTCTime> getKey() {
        return ASN1UTCTime.class;
    }

    @Override
    public Collection<ASN1ObjectDescription> descriptions(ASN1UTCTime value) {

        final List<ASN1ObjectDescription> result = new ArrayList<>();

        try {
            result.add(ASN1ObjectDescription.builder().name("Type").object(value).value("ASN1.UTCTime").build());
            result.add(ASN1ObjectDescription.builder().name("Date").object(value).value(String.valueOf(value.getDate())).build());
            result.add(ASN1ObjectDescription.builder().name("Time").object(value).value(String.valueOf(value.getTime())).build());

            result.add(ASN1ObjectDescription.builder().name("AdjustedDate").object(value).value(String.valueOf(value.getAdjustedDate())).build());
            result.add(ASN1ObjectDescription.builder().name("AdjustedTime").object(value).value(String.valueOf(value.getAdjustedTime())).build());

            result.add(this.raw(value));
        } catch (Exception e) {
            throw new RuntimeException("Can't get bytes of " + value + "!", e);
        }

        return result;
    }
}
