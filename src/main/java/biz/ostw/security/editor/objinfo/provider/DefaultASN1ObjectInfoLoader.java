package biz.ostw.security.editor.objinfo.provider;

import biz.ostw.security.editor.objinfo.ASN1ObjectIdentifierInfoProvider;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;

class DefaultASN1ObjectInfoLoader implements ASN1ObjectIdentifierInfoProvider {

    @Override
    public String identifier(ASN1ObjectIdentifier identifier) {
        return identifier != null ? identifier.getId() : "";
    }

    @Override
    public Object info(ASN1ObjectIdentifier identifier) {
        return identifier != null ? identifier.getId() : "";
    }
}
