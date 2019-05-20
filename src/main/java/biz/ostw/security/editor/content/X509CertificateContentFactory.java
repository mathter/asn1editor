package biz.ostw.security.editor.content;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.util.io.pem.PemObject;

import java.io.IOException;
import java.util.List;

public class X509CertificateContentFactory extends ContentFactory {

    @Override
    protected List<Content<?>> build(Object object) throws IOException {

        final X509CertificateHolder holder;

        if (object instanceof PemObject) {
            holder = new X509CertificateHolder(((PemObject) object).getContent());
        } else if (object instanceof ASN1Encodable) {
            holder = new X509CertificateHolder(((ASN1Encodable) object).toASN1Primitive().getEncoded());
        } else {
            throw new IllegalContent();
        }

        return null;
    }
}
