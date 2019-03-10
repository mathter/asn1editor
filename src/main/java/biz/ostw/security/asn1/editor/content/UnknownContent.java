package biz.ostw.security.asn1.editor.content;

import org.bouncycastle.asn1.ASN1Primitive;

public class UnknownContent extends Content<ASN1Primitive> {

    public UnknownContent(ASN1Primitive root) {
        super(root);
    }

    @Override
    public ASN1Primitive getObject() {
        return null;
    }

    @Override
    public String getDescription() {
        return ContentDescriptionFactory.getDescription("description.unknow");
    }
}