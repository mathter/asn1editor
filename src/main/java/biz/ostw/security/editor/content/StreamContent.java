package biz.ostw.security.editor.content;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.util.Arrays;

import java.io.IOException;
import java.util.List;

public class StreamContent implements Content<ASN1Sequence> {

    private final List<ASN1Encodable> stream;

    public StreamContent(List<ASN1Encodable> stream) {
        this.stream = stream;
    }

    @Override
    public byte[] getEncoded() {

        if (this.stream != null && !this.stream.isEmpty()) {
            return this.stream
                    .stream()
                    .map(e -> {
                        try {
                            return e.toASN1Primitive().getEncoded();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    })
                    .reduce((left, right) -> Arrays.concatenate(left, right))
                    .get();
        } else {
            return null;
        }
    }
}
