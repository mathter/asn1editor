package biz.ostw.security.asn1.editor.objinfo.provider;

import biz.ostw.security.asn1.editor.objinfo.ASN1ObjectIdentifierInfoProvider;
import biz.ostw.security.asn1.editor.objinfo.ASN1ObjectIdentifierInfoProviderFactory;

public class DefaultASN1ObjectIdentifierInfoProviderFactory extends ASN1ObjectIdentifierInfoProviderFactory {

    private static final String KEY = "Default";

    @Override
    public Object key() {
        return KEY;
    }

    @Override
    public ASN1ObjectIdentifierInfoProvider provider() {
        return new DefaultASN1ObjectInfoLoader();
    }
}
