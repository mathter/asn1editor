package biz.ostw.security.editor.ui.util;

import javafx.stage.FileChooser.ExtensionFilter;

import java.util.ResourceBundle;
import java.util.function.Supplier;

public class ExtensionFilterSupplier implements Supplier<ExtensionFilter[]> {

    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("biz.ostw.security.editor.ui.message");

    @Override
    public ExtensionFilter[] get() {
        return new ExtensionFilter[]{
                new ExtensionFilter(resourceBundle.getString("window.openfile.filefilter.all.desc"), "*.*"),
                new ExtensionFilter(resourceBundle.getString("window.openfile.filefilter.pem.desc"), "*.pem"),
                new ExtensionFilter(resourceBundle.getString("window.openfile.filefilter.der.desc"), "*.der"),
                new ExtensionFilter(resourceBundle.getString("window.openfile.filefilter.der.cer"), "*.cer"),
                new ExtensionFilter(resourceBundle.getString("window.openfile.filefilter.der.crl"), "*.crl"),
                new ExtensionFilter(resourceBundle.getString("window.openfile.filefilter.der.pkcs7"), "*.p7b", "*.cms")
        };
    }
}
