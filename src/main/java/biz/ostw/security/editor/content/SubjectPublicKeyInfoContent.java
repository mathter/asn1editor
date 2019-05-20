package biz.ostw.security.editor.content;

import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;

public abstract class SubjectPublicKeyInfoContent extends LeafContent<SubjectPublicKeyInfo> {

    public SubjectPublicKeyInfoContent(SubjectPublicKeyInfo object) {
        super(object);
    }
}
