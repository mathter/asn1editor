package biz.ostw.security.asn1.editor.ui;

import biz.ostw.security.asn1.editor.ui.render.Renderer;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import org.bouncycastle.asn1.ASN1Primitive;

public class Asn1PrimitiveTreeItem extends TreeItem<Renderer<ASN1Primitive>> {

    public Asn1PrimitiveTreeItem() {
    }

    public Asn1PrimitiveTreeItem(ASN1Primitive value) {
        super(new Renderer(value));
    }

    public Asn1PrimitiveTreeItem(ASN1Primitive value, Node graphic) {
        super(new Renderer(value), graphic);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(this.getValue().getClass().getSimpleName());


        return sb.toString();
    }
}
