package biz.ostw.security.editor.ui;

import biz.ostw.security.editor.content.ContentLoader;
import biz.ostw.security.editor.ui.control.Asn1View;
import biz.ostw.security.editor.ui.util.ExtensionFilterSupplier;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class Main extends AnchorPane {

    private final Preferences preferences = Preferences.userNodeForPackage(Main.class);

    private final Stage stage;

    @FXML
    private MenuBar menuBar;

    @FXML
    private MenuItem fileOpenMenuItem;

    @FXML
    private MenuItem fileSaveAsDerMenuItem;

    @FXML
    private MenuItem fileSaveAsPemMenuItem;

    @FXML
    private MenuItem closeMenuItem;

    @FXML
    private MenuItem aboutMenuItem;

    @FXML
    private Asn1View asn1View;

    private Path path;

    private SimpleObjectProperty<ASN1Primitive> valueProperty = new SimpleObjectProperty<>(this, "value", null);

    final private ResourceBundle resources = ResourceBundle.getBundle("biz.ostw.security.editor.ui.message");

    public Main(Stage stage) {
        this.stage = stage;

        final FXMLLoader loader = new FXMLLoader(Main.class.getResource("main.fxml"));

        loader.setRoot(this);
        loader.setResources(this.resources);
        loader.setController(this);
        loader.setLocation(Main.class.getResource("main.fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.initMenu();
        this.initComponents();
    }

    public void setValue(ASN1Primitive asn1) {
        this.valueProperty.setValue(asn1);
    }

    public ASN1Primitive getValue() {
        return this.valueProperty.getValue();
    }

    public ObjectProperty<ASN1Primitive> valueProperty() {
        return this.valueProperty;
    }

    private void initComponents() {

        this.stage.setTitle(String.format(this.resources.getString("window.main.title"), "", ""));
        this.stage.getIcons().add(new Image(Main.class.getResourceAsStream("main.png")));

        this.stage.setX(this.preferences.getDouble("x", Main.getDefaultX()));
        this.stage.setY(this.preferences.getDouble("y", Main.getDefaultY()));
        this.stage.setWidth(this.preferences.getDouble("width", Main.getDefaultWidth()));
        this.stage.setHeight(this.preferences.getDouble("height", Main.getDefaultHeigh()));
        this.stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Stage stage = (Stage) event.getSource();

                Main.this.preferences.putDouble("x", stage.getX());
                Main.this.preferences.putDouble("y", stage.getY());
                Main.this.preferences.putDouble("width", stage.getWidth());
                Main.this.preferences.putDouble("height", stage.getHeight());
            }
        });


        this.asn1View.typeNameProperty().addListener((observable, oldValue, newValue) -> {
            Main.this.stage.setTitle(String.format(this.resources.getString("window.main.title"), newValue, Main.this.path != null ? Main.this.path : ""));
        });
        this.asn1View.valueProperty().bind(this.valueProperty());
    }

    private void initDescriptionTableView() {
    }

    private void initMenu() {
        this.fileOpenMenuItem.addEventHandler(EventType.ROOT, event -> Optional.of(new FileChooser())
                .map(d -> {
                            d.setTitle(Main.this.getResourceBundle().getString("window.openfile.title"));
                            d.getExtensionFilters().addAll(new ExtensionFilterSupplier().get());

                            // Set initial derectory.
                            try {
                                File directory = new File(Main.this.preferences.get("lastpath", System.getProperty("user.home")));
                                d.setInitialDirectory(directory);
                            } catch (Exception e) {
                            }

                            return d.showOpenDialog(Main.this.menuBar.getScene().getWindow()).toPath();
                        }
                )
                .ifPresent(path -> {
                    try {
                        Main.this.path = path;

                        // Save last opened path.
                        String directory = path.getParent().toString();
                        Main.this.preferences.put("lastpath", directory);

                        // Read content.
                        byte[] bytes = Files.readAllBytes(path);
                        ASN1Primitive asn = new ContentLoader().fromByteArray(bytes);
                        Main.this.setValue(asn);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }));

        this.fileSaveAsDerMenuItem.addEventHandler(EventType.ROOT, event -> {
            Optional.of(new FileChooser())
                    .map(dialog -> {
                        dialog.setTitle(Main.this.getResourceBundle().getString("window.menubar.file.saveasder.tilte"));
                        dialog.getExtensionFilters().addAll(new ExtensionFilterSupplier().get());

                        // Set initial derectory.
                        try {
                            File directory = new File(Main.this.preferences.get("lastpath", System.getProperty("user.home")));
                            dialog.setInitialDirectory(directory);
                        } catch (Exception e) {
                        }

                        return dialog.showSaveDialog(Main.this.menuBar.getScene().getWindow()).toPath();
                    })
                    .ifPresent(path -> {
                        try {
                            Main.this.path = path;

                            // Save last opened path.
                            String directory = path.getParent().toString();
                            Main.this.preferences.put("lastpath", directory);

                            byte[] bytes = asn1View.getValue().getEncoded();

                            try (OutputStream os = new FileOutputStream(path.toFile())) {
                                os.write(bytes);
                                os.flush();
                            }
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
        });

        this.fileSaveAsPemMenuItem.addEventHandler(EventType.ROOT, event -> {
            Optional.of(new FileChooser())
                    .map(dialog -> {
                        dialog.setTitle(Main.this.getResourceBundle().getString("window.menubar.file.saveaspem.tilte"));
                        dialog.getExtensionFilters().addAll(new ExtensionFilterSupplier().get());

                        // Set initial derectory.
                        try {
                            File directory = new File(Main.this.preferences.get("lastpath", System.getProperty("user.home")));
                            dialog.setInitialDirectory(directory);
                        } catch (Exception e) {
                        }

                        return dialog.showSaveDialog(Main.this.menuBar.getScene().getWindow()).toPath();
                    })
                    .ifPresent(path -> {
                        try {
                            Main.this.path = path;

                            // Save last opened path.
                            String directory = path.getParent().toString();
                            Main.this.preferences.put("lastpath", directory);

                            try (OutputStream os = new FileOutputStream(path.toFile())) {
                                try (OutputStreamWriter osw = new OutputStreamWriter(os)) {
                                    try (JcaPEMWriter pemWriter = new JcaPEMWriter(osw)) {
                                        pemWriter.writeObject(asn1View.getValue());
                                        pemWriter.flush();
                                    }
                                }
                            }
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
        });

        this.closeMenuItem.addEventHandler(EventType.ROOT, event -> Main.this.menuBar.getScene().getWindow().hide());

        this.aboutMenuItem.addEventHandler(EventType.ROOT, event -> {
            About.getInstance().show();
        });
    }


    public static Main getInstance(Stage stage) {
        final Main instance = new Main(stage);
        stage.setScene(new Scene(instance));

        return instance;
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
