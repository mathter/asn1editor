package biz.ostw.security.editor.ui;

import biz.ostw.security.editor.objinfo.ASN1ObjectIdentifierInfoProvider;
import biz.ostw.security.editor.objinfo.provider.OidrefComASN1ObjectIdentifierInfoProviderFactory;
import org.bouncycastle.asn1.x500.X500NameStyle;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.util.encoders.Hex;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Optional;

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

    public static String toString(byte[] data) {
        return Optional.ofNullable(data)
                .map(p -> Hex.toHexString(data))
                .map(p -> p.replaceAll("[ \n]+", "").replaceAll("([0-9a-zA-Z]{2})", "$1 "))
                .orElse(null);
    }

    public static String toString(byte[] data, int timesToLine) {
        return Optional.ofNullable(toString(data))
                .map(p -> p.split(" "))
                .map(p -> {
                    StringBuilder sb = new StringBuilder();

                    for (int i = 0; i < data.length - timesToLine; i += timesToLine) {
                        for (int j = 0; j < timesToLine; j++) {
                            sb.append(p[i + j]).append(" ");
                        }

                        sb.append('\n');
                    }

                    return sb.toString();
                }).orElse(null);
    }

    public static ASN1ObjectIdentifierInfoProvider asn1ObjectIdentifierInfoProvider() {
        return new OidrefComASN1ObjectIdentifierInfoProviderFactory().provider();
    }

    private CommonService() {
    }
}
