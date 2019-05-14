package biz.ostw.security.asn1.editor.ui;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ResourceBundle;

public class About extends AnchorPane {

    private ResourceBundle resources = ResourceBundle.getBundle("biz.ostw.security.asn1.editor.ui.message");

    private final Stage stage;

    public About(Stage stage) {
        final FXMLLoader loader = new FXMLLoader(Main.class.getResource("about.fxml"));

        loader.setRoot(this);
        loader.setResources(this.resources);
        loader.setController(this);
        loader.setLocation(Main.class.getResource("about.fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.stage = stage;
        this.stage.setTitle(this.resources.getString("window.about.title"));
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.initStyle(StageStyle.UNDECORATED);
    }


    @FXML
    public void onCloseClick(Event event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    public static Stage getInstance() {
        final Stage stage = new Stage();
        final About instance = new About(stage);
        stage.setScene(new Scene(instance));

        return stage;
    }
}
