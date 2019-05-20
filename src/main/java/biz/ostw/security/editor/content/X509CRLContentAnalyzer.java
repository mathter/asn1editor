package biz.ostw.security.editor.content;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.x509.CertificateList;

import java.util.Collections;
import java.util.List;

public class X509CRLContentAnalyzer extends ContentAnalyzer {

    @Override
    protected List<LeafContent<?>> resolve(ASN1Encodable object) {
        try {
            final CertificateList o = CertificateList.getInstance(object);

            return Collections.singletonList(new X509CRLContent(o));
        } catch (Exception e) {
            return null;
        }
    }
}
