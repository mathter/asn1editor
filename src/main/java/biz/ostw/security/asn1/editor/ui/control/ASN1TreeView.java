package biz.ostw.security.asn1.editor.ui.control;

import biz.ostw.security.asn1.editor.objinfo.ASN1ObjectInfo;
import javafx.scene.Node;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.bouncycastle.asn1.ASN1ApplicationSpecific;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.util.Iterable;

import java.io.IOException;
import java.util.Iterator;

public class ASN1TreeView extends TreeView<ASN1Primitive> implements Callback<TreeView<ASN1Primitive>, TreeCell<ASN1Primitive>> {

    public ASN1TreeView() {
        this.setCellFactory(this);
    }

    public void setRoot(ASN1Primitive object) throws IOException {
        this.setRoot(this.build(object));
    }

    private TreeItem build(ASN1Primitive value) throws IOException {

        if (value != null) {

            final TreeItem result = new TreeItem(value);

            if (value instanceof Iterable) {
                result.setExpanded(true);

                Iterator<ASN1Primitive> iterator = ((Iterable) value).iterator();

                while (iterator.hasNext()) {
                    ASN1Primitive item = iterator.next();

                    result.getChildren().add(this.build(item));
                }
            } else if (value instanceof ASN1TaggedObject) {

                result.getChildren().add(this.build(((ASN1TaggedObject) value).getObject()));
            } else if (value instanceof ASN1ApplicationSpecific) {

                result.getChildren().add(this.build(((ASN1ApplicationSpecific) value).getObject()));
            }

            return result;
        } else {
            return null;
        }
    }

    @Override
    public TreeCell<ASN1Primitive> call(TreeView<ASN1Primitive> param) {
        return new TextFieldTreeCell<ASN1Primitive>(new StringConverter<ASN1Primitive>() {
            @Override
            public String toString(ASN1Primitive object) {
                ASN1ObjectInfo<ASN1Primitive> objectInfo = ASN1ObjectInfo.get((Class<ASN1Primitive>) object.getClass());

                return objectInfo.toString(object);
            }

            @Override
            public ASN1Primitive fromString(String string) {
                return null;
            }
        });
    }

    private static class TreeItem<T extends ASN1Primitive> extends javafx.scene.control.TreeItem<T> {

        public TreeItem() {
        }

        public TreeItem(T value) {
            super(value);
        }

        public TreeItem(T value, Node graphic) {
            super(value, graphic);
        }
    }
}
