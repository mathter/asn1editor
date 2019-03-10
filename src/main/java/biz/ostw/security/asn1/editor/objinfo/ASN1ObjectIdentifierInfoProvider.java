package biz.ostw.security.asn1.editor.objinfo;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;

public interface ASN1ObjectIdentifierInfoProvider {

    public String identifier(ASN1ObjectIdentifier identifier);

    public Object info(ASN1ObjectIdentifier identifier);
}
