package biz.ostw.security.editor.ui.control.provider;

import biz.ostw.security.editor.content.ContentAnalyzer;
import biz.ostw.security.editor.ui.Main;
import biz.ostw.security.editor.ui.control.Asn1MenuProvider;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import org.bouncycastle.asn1.ASN1BitString;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Asn1MenuBytesProvider extends Asn1MenuProvider {

    private static final String KEY = "bytes";

    @Override
    public List<MenuItem> get(ASN1Primitive asn1) {

        final List<MenuItem> result = new ArrayList<>();

        this.openAs(asn1, result);

        return result;
    }

    private List<MenuItem> openAs(ASN1Primitive asn1, List<MenuItem> collector) {

        this.openAsMenuItem(asn1).ifPresent(menuItem -> collector.add(menuItem));

        return collector;
    }

    private Optional<MenuItem> openAsMenuItem(ASN1Primitive asn1) {
        final MenuItem menuItem;

        if (asn1 instanceof ASN1OctetString || asn1 instanceof ASN1BitString) {
            menuItem = new MenuItem("Open as " + new ContentAnalyzer().analyze(asn1).getDescription());

            menuItem.setOnAction(event -> {
                final Stage stage = new Stage();
                final Main main = Main.getInstance(stage);
                main.setValue(asn1);

                stage.show();
            });
        } else {
            menuItem = null;
        }

        return Optional.ofNullable(menuItem);
    }

    @Override
    public Object key() {
        return KEY;
    }
}
