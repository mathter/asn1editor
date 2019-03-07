package biz.ostw.security.asn1.editor.ui.render;

import org.bouncycastle.asn1.ASN1Primitive;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

public abstract class ASN1ObjectInfo<T extends ASN1Primitive> {

    private static Map<Class<? extends ASN1Primitive>, ASN1ObjectInfo> MAP = new HashMap<>();

    public static final ASN1ObjectInfo get(Class<? extends ASN1Primitive> clazz) {

        ASN1ObjectInfo ASN1ObjectInfo = null;

        if ((ASN1ObjectInfo = MAP.get(clazz)) == null) {

            for (ASN1ObjectInfo item : ServiceLoader.load(ASN1ObjectInfo.class)) {
                if (item.getKey().equals(clazz)) {
                    ASN1ObjectInfo = item;
                    break;
                }
            }

            if (ASN1ObjectInfo == null && ASN1Primitive.class.isAssignableFrom(clazz.getSuperclass())) {
                ASN1ObjectInfo = get((Class<ASN1Primitive>) clazz.getSuperclass());
            }

            if (ASN1ObjectInfo == null) {
                ASN1ObjectInfo = new Default();
            }
        }

        return ASN1ObjectInfo;
    }

    public abstract String toString(T value);

    protected abstract Class<? extends ASN1Primitive> getKey();

    private static class Default extends ASN1ObjectInfo {
        @Override
        protected Class<? extends ASN1Primitive> getKey() {
            return ASN1Primitive.class;
        }

        @Override
        public String toString(ASN1Primitive asn1Primitive) {
            return asn1Primitive != null ? asn1Primitive.toString() : "<null>";
        }
    }

    public class ObjectProperty<T> {
        private String name;

        private T value;

        private boolean editable;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
