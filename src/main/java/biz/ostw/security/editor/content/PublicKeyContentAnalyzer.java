package biz.ostw.security.editor.content;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;

import java.util.Collections;
import java.util.List;

public class PublicKeyContentAnalyzer extends ContentAnalyzer {

    @Override
    protected List<LeafContent<?>> resolve(ASN1Encodable object) {
        try {
            final SubjectPublicKeyInfo o = SubjectPublicKeyInfo.getInstance(object);

            return Collections.singletonList(new PublicKeyContent(o));
        } catch (Exception e) {
            return null;
        }
    }
}
