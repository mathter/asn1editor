package biz.ostw.security.asn1.editor;

import javafx.application.Application;
import javafx.stage.Stage;

import java.security.Security;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        biz.ostw.security.asn1.editor.ui.Main.getInstance(primaryStage).show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }
}
