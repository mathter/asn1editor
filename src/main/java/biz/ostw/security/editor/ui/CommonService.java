package biz.ostw.security.editor.ui;

import biz.ostw.security.editor.objinfo.ASN1ObjectIdentifierInfoProvider;
import biz.ostw.security.editor.objinfo.provider.OidrefComASN1ObjectIdentifierInfoProviderFactory;
import org.bouncycastle.asn1.x500.X500NameStyle;
import org.bouncycastle.asn1.x500.style.BCStyle;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public abstract class CommonService {

    public static DateFormat dateFormat() {
        return new SimpleDateFormat("dd.MM.yyyy");
    }

    public static DateFormat dateTimeFormat() {
        return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss z X");
    }

    public static X500NameStyle x500NameStyle() {
        return BCStyle.INSTANCE;
    }

    public static ASN1ObjectIdentifierInfoProvider asn1ObjectIdentifierInfoProvider() {
        return new OidrefComASN1ObjectIdentifierInfoProviderFactory().provider();
    }

    private CommonService() {
    }
}
