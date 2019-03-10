package biz.ostw.security.asn1.editor.ui.util;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class FxmlLoader {

    private final FXMLLoader loader;

    private final ResourceBundle resourceBundle;

    public FxmlLoader() {
        this(ResourceBundle.getBundle("biz/ostw/security/asn1/editor/ui/message"));
    }

    public FxmlLoader(ResourceBundle resourceBundle) {
        this(new FXMLLoader(), resourceBundle);
    }

    public FxmlLoader(FXMLLoader loader, ResourceBundle resourceBundle) {
        this.loader = loader;
        this.resourceBundle = resourceBundle;

        this.loader.setResources(this.resourceBundle);
    }

    public Stage load(Stage stage, InputStream is) throws IOException {

        final Parent parent = loader.load(is);
        final Scene scene = new Scene(parent);

        stage.setScene(scene);

        this.processAnnotations(Arrays.asList(stage, scene, this.loader).stream().map(e -> new Object[]{e.getClass(), e}).collect(Collectors.toMap(e -> (Class<?>) e[0], e -> e[1])));
        if (this.loader.getController() instanceof Initializable) {
            ((Initializable) this.loader.getController()).initialize();
        }

        return stage;
    }

    public Stage load(InputStream is) throws IOException {

        return this.load(new Stage(), is);
    }

    private void processAnnotations(Map<Class<?>, Object> objects) {

        Object controller = this.loader.getController();

        for (Field field : controller.getClass().getDeclaredFields()) {

            for (Annotation annotation : field.getAnnotations()) {
                if (annotation instanceof FXML) {
                    Class<?> fieldType = field.getType();
                    Object object = objects.get(fieldType);

                    if (object != null) {
                        try {
                            field.setAccessible(true);
                            field.set(controller, object);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public static final FxmlLoader getDefault() {

        return new FxmlLoader();
    }
}
