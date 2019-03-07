package biz.ostw.security.asn1.editor.ui;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class About {

    @FXML
    public void onCloseClick(Event event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    public static Stage getInstance() throws IOException {
        final Stage stage = FxmlLoader.getDefault().load(About.class.getResourceAsStream("about.fxml"));

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);

        stage.setTitle(FxmlLoader.getDefault().getResourceBundle().getString("window.about.title"));

        return stage;
    }
}
