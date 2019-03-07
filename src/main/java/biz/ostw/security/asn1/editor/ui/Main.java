package biz.ostw.security.asn1.editor.ui;

import biz.ostw.security.asn1.editor.ContentLoader;
import biz.ostw.security.asn1.editor.ui.render.Renderer;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.bouncycastle.asn1.ASN1Primitive;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class Main implements Initializable {

    private final Preferences preferences = Preferences.userNodeForPackage(Main.class);

    @FXML
    private Stage stage;

    @FXML
    private MenuBar menuBar;

    @FXML
    private MenuItem fileOpenMenuItem;

    @FXML
    private MenuItem fileSaveAsMenuItem;

    @FXML
    private MenuItem closeMenuItem;

    @FXML
    private MenuItem aboutMenuItem;

    @FXML
    private TreeView<Renderer<ASN1Primitive>> treeView;

    @FXML
    private SplitPane splitPane;

    private TreeAsn1Controller treeAsn1Controller;

    private ResourceBundle resources;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.treeAsn1Controller = new TreeAsn1Controller(this.treeView);
        this.resources = resources;
    }

    @Override
    public void initialize() {
        this.initMenu();
        this.initComponents();
    }

    private void initComponents() {

        this.stage.setTitle(FxmlLoader.getDefault().getResourceBundle().getString("window.main.title"));
        this.stage.getIcons().add(new Image(Main.class.getResourceAsStream("main.png")));

        this.stage.setX(this.preferences.getDouble("x", Main.getDefaultX()));
        this.stage.setY(this.preferences.getDouble("y", Main.getDefaultY()));
        this.stage.setWidth(this.preferences.getDouble("width", Main.getDefaultWidth()));
        this.stage.setHeight(this.preferences.getDouble("height", Main.getDefaultHeigh()));

        this.splitPane.setDividerPositions(this.preferences.getDouble("divpos", 0.8));
        this.stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Stage stage = (Stage) event.getSource();

                Main.this.preferences.putDouble("x", stage.getX());
                Main.this.preferences.putDouble("y", stage.getY());
                Main.this.preferences.putDouble("width", stage.getWidth());
                Main.this.preferences.putDouble("height", stage.getHeight());
                Main.this.preferences.putDouble("divpos", Main.this.splitPane.getDividerPositions()[0]);
            }
        });
    }

    private void initMenu() {
        this.fileOpenMenuItem.addEventHandler(EventType.ROOT, new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                Optional.of(new FileChooser())
                        .map(d -> {
                                    d.setTitle(Main.this.getResourceBundle().getString("window.openfile.title"));
                                    d.getExtensionFilters().addAll(new ExtensionFilterSupplier().get());

                                    return d.showOpenDialog(Main.this.menuBar.getScene().getWindow()).toPath();
                                }
                        )
                        .map(path -> {
                            try {
                                byte[] bytes = Files.readAllBytes(path);
                                ASN1Primitive asn = new ContentLoader().fromByteArray(bytes);
                                Main.this.treeAsn1Controller.setRoot(asn);

                                return null;
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        });
            }
        });

        this.fileSaveAsMenuItem.addEventHandler(EventType.ROOT, new EventHandler<Event>() {
            @Override
            public void handle(Event event) {

            }
        });

        this.closeMenuItem.addEventHandler(EventType.ROOT, new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                Main.this.menuBar.getScene().getWindow().hide();
            }
        });

        this.aboutMenuItem.addEventHandler(EventType.ROOT, new EventHandler<Event>() {
            @Override
            public void handle(Event event) {

            }
        });
    }

    public static Stage getInstance(Stage stage) throws IOException {
        FxmlLoader.getDefault().load(stage, About.class.getResourceAsStream("main.fxml"));

        return stage;
    }

    private static double getDefaultX() {
        return Main.getDefaultWidth() / 8;
    }

    private static double getDefaultY() {
        return Main.getDefaultHeigh() / 8;
    }

    private static double getDefaultWidth() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        return gd.getDisplayMode().getWidth() * 0.8;
    }

    private static double getDefaultHeigh() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        return gd.getDisplayMode().getHeight() * 0.8;
    }

    private ResourceBundle getResourceBundle() {
        return this.resources;
    }
}
