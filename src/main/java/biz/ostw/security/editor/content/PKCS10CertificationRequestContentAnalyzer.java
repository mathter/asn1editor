package biz.ostw.security.editor.content;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.pkcs.CertificationRequest;

import java.util.Collections;
import java.util.List;

public class PKCS10CertificationRequestContentAnalyzer extends ContentAnalyzer {

    @Override
    protected List<LeafContent<?>> resolve(ASN1Encodable object) {
        try {
            final CertificationRequest o = CertificationRequest.getInstance(object);

            return Collections.singletonList(new PKCS10CertificationRequestContent(o));
        } catch (Exception e) {
            return null;
        }
    }
}
