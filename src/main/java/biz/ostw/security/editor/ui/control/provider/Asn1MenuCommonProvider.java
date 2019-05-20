package biz.ostw.security.editor.ui.control.provider;

import biz.ostw.security.editor.ui.control.Asn1MenuProvider;
import javafx.scene.control.MenuItem;
import org.bouncycastle.asn1.ASN1Primitive;

import java.util.Collections;
import java.util.List;

public class Asn1MenuCommonProvider extends Asn1MenuProvider {

    private static final String KEY = "common";

    @Override
    public List<MenuItem> get(ASN1Primitive asn1) {
        return Collections.emptyList();
    }

    @Override
    public Object key() {
        return KEY;
    }
}
