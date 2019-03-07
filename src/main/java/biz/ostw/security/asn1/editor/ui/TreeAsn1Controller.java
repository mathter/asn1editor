package biz.ostw.security.asn1.editor.ui;

import biz.ostw.security.asn1.editor.ui.render.Renderer;
import javafx.scene.control.TreeView;
import lombok.NonNull;
import org.bouncycastle.asn1.ASN1ApplicationSpecific;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.util.Iterable;

import java.io.IOException;
import java.util.Iterator;

public class TreeAsn1Controller {

    private final TreeView<Renderer<ASN1Primitive>> treeView;

    private ASN1Primitive root;

    public TreeAsn1Controller(@NonNull TreeView<Renderer<ASN1Primitive>> treeView) {
        this.treeView = treeView;
    }

    public void setRoot(ASN1Primitive root) throws IOException {

        this.treeView.setRoot(this.build(root));
    }

    private Asn1PrimitiveTreeItem build(ASN1Primitive asn1Primitive) throws IOException {

        final Asn1PrimitiveTreeItem result = new Asn1PrimitiveTreeItem(asn1Primitive);

        if (asn1Primitive instanceof Iterable) {

            Iterator<ASN1Primitive> iterator = ((Iterable) asn1Primitive).iterator();

            while (iterator.hasNext()) {
                ASN1Primitive item = iterator.next();

                result.getChildren().add(this.build(item));
            }
        } else if (asn1Primitive instanceof ASN1TaggedObject) {

            result.getChildren().add(this.build(((ASN1TaggedObject) asn1Primitive).getObject()));
        } else if (asn1Primitive instanceof ASN1ApplicationSpecific) {

            result.getChildren().add(this.build(((ASN1ApplicationSpecific) asn1Primitive).getObject()));
        }
        
        return result;
    }
}
