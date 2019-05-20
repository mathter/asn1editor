package biz.ostw.security.editor.content;

import org.bouncycastle.asn1.ASN1Encodable;

import java.util.Collections;
import java.util.List;

public class UnknownContentFactory extends ContentFactory {

    @Override
    protected List<Content<?>> build(Object object) {
        if (object instanceof ASN1Encodable) {
            return Collections.singletonList(new UnknownContent(((ASN1Encodable) object).toASN1Primitive()));
        } else {
            throw new IllegalContent();
        }
    }
}
