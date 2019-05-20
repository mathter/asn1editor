package biz.ostw.security.editor.content;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.x509.AttributeCertificate;

import java.util.Collections;
import java.util.List;

public class X509AttributeCertificateContentAnalyzer extends ContentAnalyzer {

    @Override
    protected List<LeafContent<?>> resolve(ASN1Encodable object) {
        try {
            final AttributeCertificate o = AttributeCertificate.getInstance(object);

            return Collections.singletonList(new X509AttributeCertificateContent(o));
        } catch (Exception e) {
            return null;
        }
    }
}
