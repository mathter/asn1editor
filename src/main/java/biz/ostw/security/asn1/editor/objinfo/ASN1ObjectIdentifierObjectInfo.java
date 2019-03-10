package biz.ostw.security.asn1.editor.objinfo;

import biz.ostw.security.asn1.editor.objinfo.provider.OidrefComASN1ObjectIdentifierInfoProviderFactory;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ASN1ObjectIdentifierObjectInfo extends ASN1ObjectInfo.Default<ASN1ObjectIdentifier> {

    private final ASN1ObjectIdentifierInfoProvider provider = ASN1ObjectIdentifierInfoProviderFactory.get(OidrefComASN1ObjectIdentifierInfoProviderFactory.KEY).provider();

    @Override
    public String toString(ASN1ObjectIdentifier value) {
        StringBuilder sb = new StringBuilder();

        sb.append("OBJECT IDENTIFIER: ")
                .append(value.getId());

        return sb.toString();
    }

    @Override
    protected Class<ASN1ObjectIdentifier> getKey() {
        return ASN1ObjectIdentifier.class;
    }

    @Override
    public Collection<ASN1ObjectDescription> descriptions(ASN1ObjectIdentifier value) {

        final List<ASN1ObjectDescription> result = new ArrayList<>();

        result.add(ASN1ObjectDescription.builder().name("Type").object(value).value("ASN1.ObjectIdentifier").build());
        result.add(ASN1ObjectDescription.builder().name("Value").object(value).value(value.getId()).build());
        result.add(new ASN1ObjectDescription<Object>() {

            {
                this.setName("Info");
                this.setObject(value);
            }

            @Override
            public Object getValue() {
                return ASN1ObjectIdentifierObjectInfo.this.provider.info((ASN1ObjectIdentifier) this.getObject());
            }
        });

        try {
            result.add(this.raw(value));
        } catch (Exception e) {
            throw new RuntimeException("Can't get bytes of " + value + "!", e);
        }

        return result;
    }
}
