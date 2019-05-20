package biz.ostw.security.editor.content;

import org.bouncycastle.asn1.cms.ContentInfo;

public class PKCS7Content extends Content<ContentInfo> {

    public PKCS7Content(ContentInfo object) {
        super(object);
    }

    @Override
    public String getDescription() {
        return ContentDescriptionFactory.getDescription("description.pkcs7");
    }
}
