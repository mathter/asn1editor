package biz.ostw.security.asn1.editor.objinfo.provider;

import biz.ostw.security.asn1.editor.objinfo.ASN1ObjectIdentifierInfoProvider;
import biz.ostw.security.asn1.editor.objinfo.ASN1ObjectIdentifierInfoProviderFactory;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;

import java.net.MalformedURLException;
import java.net.URL;

class OidrefComASN1ObjectIdentifierInfoProvider implements ASN1ObjectIdentifierInfoProvider {

    protected static final URL URL;

    private ASN1ObjectIdentifierInfoProvider defaultProvider = ASN1ObjectIdentifierInfoProviderFactory.get("Default").provider();

    @Override
    public String identifier(ASN1ObjectIdentifier identifier) {
        return identifier != null ? identifier.getId() : "";
    }

    @Override
    public Object info(ASN1ObjectIdentifier identifier) {

        Object result;
        try {
            URL url = new URL(URL, identifier.getId());

            result = url;
        } catch (Exception e) {
            e.printStackTrace();
            result = this.defaultProvider.info(identifier);
        }

        return result;
    }

    static {
        try {
            URL = new URL("http://oidref.com");
        } catch (MalformedURLException e) {
            throw new RuntimeException("Can't initialize provider!", e);
        }
    }
}
