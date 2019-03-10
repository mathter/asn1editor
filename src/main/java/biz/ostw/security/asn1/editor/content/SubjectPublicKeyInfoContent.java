package biz.ostw.security.asn1.editor.content;

import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;

public abstract class SubjectPublicKeyInfoContent extends Content<SubjectPublicKeyInfo> {

    public SubjectPublicKeyInfoContent(SubjectPublicKeyInfo object) {
        super(object);
    }
}
