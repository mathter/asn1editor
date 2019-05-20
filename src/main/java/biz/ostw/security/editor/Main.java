package biz.ostw.security.editor;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.security.Security;

public class Main {

    public static void main(String[] args) {
        Application.launch(Local.class, args);
    }

    public static class Local extends Application {
        @Override
        public void start(Stage primaryStage) throws Exception {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            biz.ostw.security.editor.ui.Main.getInstance(primaryStage);

            primaryStage.show();
        }

        @Override
        public void stop() throws Exception {
            super.stop();
        }
    }
}
