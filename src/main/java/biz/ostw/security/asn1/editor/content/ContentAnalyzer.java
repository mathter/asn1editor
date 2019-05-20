package biz.ostw.security.asn1.editor.content;

import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509AttributeCertificateHolder;
import org.bouncycastle.cert.X509CRLHolder;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.openssl.X509TrustedCertificateBlock;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;

import java.io.IOException;
import java.security.cert.CertificateException;

public class ContentAnalyzer {

    public Content<?> analyze(ASN1Primitive asn1) {
        Content content;

        try {
            content = this.tryX509CertificateContent(asn1);
        } catch (Exception e0) {
            try {
                content = this.tryPkcs10CertificationRequest(asn1);
            } catch (Exception e1) {
                try {
                    content = this.tryX509TrustedCertificateBlockContent(asn1);
                } catch (Exception e2) {
                    try {
                        content = this.tryX509AttributeCertificateContent(asn1);
                    } catch (Exception e3) {
                        try {
                            content = this.tryPublicKeyContent(asn1);
                        } catch (Exception e4) {
                            try {
                                content = this.tryRsaPublicKeyContent(asn1);
                            } catch (Exception e5) {
                                try {
                                    content = tryPkcs7Content(asn1);
                                } catch (Exception e6) {
                                    try {
                                        content = this.tryX509CRLContent(asn1);
                                    } catch (Exception e7) {
                                        try {
                                            content = this.tryPrivateKeyContent(asn1);
                                        } catch (Exception e8) {
                                            content = new UnknownContent(asn1);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return content;
    }

    private PrivateKeyContent tryPrivateKeyContent(ASN1Primitive asn1) throws IOException {
        final PrivateKeyInfo object = PrivateKeyInfo.getInstance(asn1.getEncoded());

        return new PrivateKeyContent(object);
    }

    private X509CRLContent tryX509CRLContent(ASN1Primitive asn1) throws IOException {
        final X509CRLHolder object = new X509CRLHolder(asn1.getEncoded());

        return new X509CRLContent(object);
    }

    private RsaPublicKeyContent tryRsaPublicKeyContent(ASN1Primitive asn1) {
        final SubjectPublicKeyInfo object = SubjectPublicKeyInfo.getInstance(asn1);

        return new RsaPublicKeyContent(object);
    }

    private PublicKeyContent tryPublicKeyContent(ASN1Primitive asn1) {
        final SubjectPublicKeyInfo object = SubjectPublicKeyInfo.getInstance(asn1);

        return new PublicKeyContent(object);
    }

    private X509AttributeCertificateContent tryX509AttributeCertificateContent(ASN1Primitive asn1) throws IOException {
        final X509AttributeCertificateHolder object = new X509AttributeCertificateHolder(asn1.getEncoded());

        return new X509AttributeCertificateContent(object);
    }

    private PKCS7Content tryPkcs7Content(ASN1Primitive asn1) {
        final ContentInfo object = ContentInfo.getInstance(asn1);

        return new PKCS7Content(object);
    }

    private X509TrustedCertificateBlockContent tryX509TrustedCertificateBlockContent(ASN1Primitive asn1) throws IOException {
        final X509TrustedCertificateBlock object = new X509TrustedCertificateBlock(asn1.getEncoded());

        return new X509TrustedCertificateBlockContent(object);
    }

    private PKCS10CertificationRequestContent tryPkcs10CertificationRequest(ASN1Primitive asn1) throws IOException {
        final PKCS10CertificationRequest object = new PKCS10CertificationRequest(asn1.getEncoded());

        return new PKCS10CertificationRequestContent(object);
    }

    private X509CertificateContent tryX509CertificateContent(ASN1Primitive asn1) throws IOException, CertificateException {
        final X509CertificateHolder object = new X509CertificateHolder(asn1.getEncoded());

        return new X509CertificateContent(object);
    }
}
