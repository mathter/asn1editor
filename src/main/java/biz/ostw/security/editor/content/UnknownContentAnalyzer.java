package biz.ostw.security.editor.content;

import org.bouncycastle.asn1.ASN1Encodable;

import java.util.Collections;
import java.util.List;

public class UnknownContentAnalyzer extends ContentAnalyzer {

    @Override
    protected List<LeafContent<?>> resolve(ASN1Encodable object) {
        try {
            if (object instanceof ASN1Encodable) {
                return Collections.singletonList(new UnknownContent(((ASN1Encodable) object).toASN1Primitive()));
            } else {
                throw new IllegalContent();
            }
        } catch (Exception e) {
            return null;
        }
    }
}
