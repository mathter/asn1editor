package biz.ostw.security.editor.objinfo;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

public abstract class ASN1ObjectIdentifierInfoProviderFactory {

    private static Map<Object, ASN1ObjectIdentifierInfoProviderFactory> MAP = new HashMap<>();

    public static final ASN1ObjectIdentifierInfoProviderFactory get(Object key) {
        ASN1ObjectIdentifierInfoProviderFactory provider;

        if ((provider = MAP.get(key)) == null) {
            for (ASN1ObjectIdentifierInfoProviderFactory provider1 : ServiceLoader.load(ASN1ObjectIdentifierInfoProviderFactory.class)) {
                if (provider1.key().equals(key)) {
                    provider = provider1;
                    MAP.put(key, provider1);
                    break;
                }
            }
        }

        return provider != null ? provider : null;
    }

    public abstract Object key();

    public abstract ASN1ObjectIdentifierInfoProvider provider();
}
