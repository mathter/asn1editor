package biz.ostw.security.editor.content;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.util.Encodable;

public interface Content<T extends ASN1Encodable> extends Encodable {
}
