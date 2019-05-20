package biz.ostw.security.editor.objinfo;

import lombok.*;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.util.encoders.Hex;

import java.io.IOException;
import java.util.*;

public abstract class ASN1ObjectInfo<T extends ASN1Primitive> {

    private static Map<Class<? extends ASN1Primitive>, ASN1ObjectInfo> MAP = new HashMap<>();

    public static final <T extends ASN1Primitive> ASN1ObjectInfo<T> get(Class<T> clazz) {

        ASN1ObjectInfo<T> objectInfo = null;

        if ((objectInfo = MAP.get(clazz)) == null) {

            for (ASN1ObjectInfo item : ServiceLoader.load(ASN1ObjectInfo.class))
                if (item.getKey().equals(clazz)) {
                    objectInfo = item;
                    break;
                }

            if (objectInfo == null && ASN1Primitive.class.isAssignableFrom(clazz.getSuperclass())) {
                objectInfo = get((Class<T>) clazz.getSuperclass());
            }

            if (objectInfo == null) {
                objectInfo = new Default();
            }
        }

        return objectInfo;
    }

    public abstract String toString(T value);

    public abstract Collection<ASN1ObjectDescription> descriptions(T value);

    protected abstract Class<? extends ASN1Primitive> getKey();

    public static class Default<T extends ASN1Primitive> extends ASN1ObjectInfo<T> {
        @Override
        protected Class<T> getKey() {
            return (Class<T>) ASN1Primitive.class;
        }

        @Override
        public String toString(T asn1Primitive) {
            return asn1Primitive != null ? asn1Primitive.toString() : "<null>";
        }

        @Override
        public Collection<ASN1ObjectDescription> descriptions(T value) {

            final List<ASN1ObjectDescription> result = new ArrayList<>();

            result.add(ASN1ObjectDescription.builder().name("ASN1Object").object(value).value(this.toString()).build());

            try {
                result.add(this.raw(value));
            } catch (Exception e) {
                throw new RuntimeException("Can't get bytes of " + value + "!", e);
            }

            return result;
        }

        protected ASN1ObjectDescription<String> raw(T value) throws IOException {
            return ASN1ObjectDescription
                    .<String>builder()
                    .name("Raw")
                    .object(value)
                    .value(Hex.toHexString(value.getEncoded()))
                    .build();
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ASN1ObjectDescription<T> {

        @NonNull
        private ASN1Primitive object;

        private String name;

        private T value;

        @Builder.Default
        private boolean editable = false;
    }
}
