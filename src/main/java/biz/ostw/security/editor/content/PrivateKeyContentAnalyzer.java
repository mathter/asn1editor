package biz.ostw.security.editor.content;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;

import java.util.Collections;
import java.util.List;

public class PrivateKeyContentAnalyzer extends ContentAnalyzer {

    @Override
    protected List<LeafContent<?>> resolve(ASN1Encodable object) {
        try {
            final PrivateKeyInfo o = PrivateKeyInfo.getInstance(object);

            return Collections.singletonList(new PrivateKeyContent(o));
        } catch (Exception e) {
            return null;
        }
    }
}
