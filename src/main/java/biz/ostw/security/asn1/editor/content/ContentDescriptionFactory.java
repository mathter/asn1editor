package biz.ostw.security.asn1.editor.content;

import java.util.ResourceBundle;

class ContentDescriptionFactory {

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("biz/ostw/security/asn1/editor/content/message");

    public static String getDescription(String key) {
        return RESOURCE_BUNDLE.getString(key);
    }
}
