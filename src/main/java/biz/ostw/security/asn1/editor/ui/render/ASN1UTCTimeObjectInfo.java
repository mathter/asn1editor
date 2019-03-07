package biz.ostw.security.asn1.editor.ui.render;

import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1UTCTime;

import java.text.ParseException;

public class ASN1UTCTimeObjectInfo extends ASN1ObjectInfo<ASN1UTCTime> {

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
    protected Class<? extends ASN1Primitive> getKey() {
        return ASN1UTCTime.class;
    }
}
