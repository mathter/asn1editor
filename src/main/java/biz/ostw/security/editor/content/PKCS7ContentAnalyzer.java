package biz.ostw.security.editor.content;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.cms.ContentInfo;

import java.util.Collections;
import java.util.List;

public class PKCS7ContentAnalyzer extends ContentAnalyzer {

    @Override
    protected List<LeafContent<?>> resolve(ASN1Encodable object) {
        try {
            final ContentInfo o = ContentInfo.getInstance(object);

            return Collections.singletonList(new PKCS7Content(o));
        } catch (Exception e) {
            return null;
        }
    }
}
