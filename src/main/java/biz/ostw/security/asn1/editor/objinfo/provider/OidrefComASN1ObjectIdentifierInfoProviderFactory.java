package biz.ostw.security.asn1.editor.objinfo.provider;

import biz.ostw.security.asn1.editor.objinfo.ASN1ObjectIdentifierInfoProvider;
import biz.ostw.security.asn1.editor.objinfo.ASN1ObjectIdentifierInfoProviderFactory;

public class OidrefComASN1ObjectIdentifierInfoProviderFactory extends ASN1ObjectIdentifierInfoProviderFactory {

    public static final String KEY = "http://oidref.com";

    @Override
    public Object key() {
        return KEY;
    }

    @Override
    public ASN1ObjectIdentifierInfoProvider provider() {
        return new OidrefComASN1ObjectIdentifierInfoProvider();
    }
}
