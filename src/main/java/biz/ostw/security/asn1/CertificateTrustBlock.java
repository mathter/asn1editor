package biz.ostw.security.asn1;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;

public class CertificateTrustBlock implements ASN1Encodable {

    private final ASN1Sequence sequence;

    public CertificateTrustBlock(ASN1Sequence sequence) {
        this.sequence = sequence;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        return null;
    }
}
