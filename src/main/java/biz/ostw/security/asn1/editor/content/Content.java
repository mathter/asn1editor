package biz.ostw.security.asn1.editor.content;

import java.util.ResourceBundle;

public abstract class Content<T> {

    private T object;

    protected Content(T object) {
        this.object = object;
    }

    public T getObject() {
        return this.object;
    }

    public abstract String getDescription();
}
