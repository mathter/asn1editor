package biz.ostw.security.editor.content;

import org.bouncycastle.asn1.ASN1Encodable;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public abstract class ContentAnalyzer {

    public static List<LeafContent<?>> analyze(ASN1Encodable encodable) throws IOException {
        final List<LeafContent<?>> result = new ArrayList<>();

        for (ContentAnalyzer contentAnalyzer : ServiceLoader.load(ContentAnalyzer.class)) {
            List<LeafContent<?>> c = contentAnalyzer.resolve(encodable);

            if (c != null) {
                result.addAll(c);
                break;
            }
        }

        return result;
    }

    protected abstract List<LeafContent<?>> resolve(ASN1Encodable object) throws IOException;

    private PrivateKeyContent tryPrivateKeyContent(ASN1Primitive asn1) throws IOException {
        final PrivateKeyInfo object = PrivateKeyInfo.getInstance(asn1.getEncoded());

        return new PrivateKeyContent(object);
    }

    private X509CRLContent tryX509CRLContent(ASN1Primitive asn1) throws IOException {
        final X509CRLHolder object = new X509CRLHolder(asn1.getEncoded());

        return new X509CRLContent(object.toASN1Structure());
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

        return new X509AttributeCertificateContent(object.toASN1Structure());
    }

    private PKCS7Content tryPkcs7Content(ASN1Primitive asn1) {
        final ContentInfo object = ContentInfo.getInstance(asn1);

        return new PKCS7Content(object);
    }

    private X509TrustedCertificateBlockContent tryX509TrustedCertificateBlockContent(ASN1Primitive asn1) throws IOException {
        final X509TrustedCertificateBlock object = new X509TrustedCertificateBlock(asn1.getEncoded());

//        return new X509TrustedCertificateBlockContent(object.);
        return null;
    }

    private PKCS10CertificationRequestContent tryPkcs10CertificationRequest(ASN1Primitive asn1) throws IOException {
        final PKCS10CertificationRequest object = new PKCS10CertificationRequest(asn1.getEncoded());

        return new PKCS10CertificationRequestContent(object.toASN1Structure());
    }

    private X509CertificateContent tryX509CertificateContent(ASN1Primitive asn1) throws IOException, CertificateException {
        final X509CertificateHolder object = new X509CertificateHolder(asn1.getEncoded());

        return new X509CertificateContent(object.toASN1Structure());
    }
}
