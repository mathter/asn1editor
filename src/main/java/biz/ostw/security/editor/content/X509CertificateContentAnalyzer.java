package biz.ostw.security.editor.content;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.x509.Certificate;

import java.util.Collections;
import java.util.List;

public class X509CertificateContentAnalyzer extends ContentAnalyzer {

    @Override
    protected List<LeafContent<?>> resolve(ASN1Encodable object) {
        try {
            return Collections.singletonList(new X509CertificateContent(Certificate.getInstance(object)));
        } catch (Exception e) {
            return null;
        }
    }
}
