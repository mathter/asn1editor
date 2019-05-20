package biz.ostw.security.editor.content;

import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1StreamParser;
import org.bouncycastle.openssl.PEMParser;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;

public class ContentLoader {

    public ASN1Primitive fromByteArray(byte[] bytes) throws CantParseException {

        ASN1Primitive result;

        try {
            result = this.loadPEM(bytes);
        } catch (CantParseException e) {

            try {
                result = this.loadDER(bytes);
            } catch (CantParseException e1) {
                throw new CantParseException(e1.getMessage(), e1.getCause());
            } catch (Exception e1) {
                throw new CantParseException("Can't parse object!", e1);
            }
        }

        return result;
    }

    private ASN1Primitive loadDER(byte[] bytes) throws CantParseException {
        try {
            ASN1StreamParser parser = new ASN1StreamParser(bytes);
            return parser.readObject().toASN1Primitive();
        } catch (Exception e) {
            throw new CantParseException("Can't parse DER!", e);
        }
    }

    private ASN1Primitive loadPEM(byte[] bytes) throws CantParseException {

        ASN1Primitive result;

        try {
            final PEMParser pemParser = new PEMParser(new InputStreamReader(new ByteArrayInputStream(bytes)));
            final Object object = pemParser.readObject();

            Method getEncodedMethod = object.getClass().getMethod("getEncoded", null);
            bytes = (byte[]) getEncodedMethod.invoke(object);

            result = ASN1Primitive.fromByteArray(bytes);
        } catch (Exception e) {
            throw new CantParseException("Can't parse PEM!", e);
        }

        if (result == null) {
            throw new CantParseException("Can't parse PEM!");
        }

        return result;
    }

    private static class CantParseException extends Exception {

        public CantParseException(String message) {
            super(message);
        }

        public CantParseException(String message, Throwable t) {
            super(message, t);
        }
    }
}
