package net.winnall.osgi.javafx;

import java.util.function.Consumer;
import java.util.function.Function;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractJavaFXApplication extends Application implements JavaFXApplication {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    protected Stage primaryStage;
    protected BundleContext bundleContext;

    @Override
    public void init() {
        try {
            ServiceRegistration<JavaFXApplication> registration = FrameworkUtil.getBundle(JavaFXLauncher.class).
                    getBundleContext().
                    registerService(JavaFXApplication.class, this, null);
            bundleContext = registration.getReference().
                    getBundle().
                    getBundleContext();
        } catch (Exception ex) {
            log.warn("cannot osgify JavaFX Application: {}", ex);
        }
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public void onStage(Consumer<Stage> action) {
        if (Platform.isFxApplicationThread()) {
            action.accept(primaryStage);
        } else {
            Platform.runLater(() -> action.accept(primaryStage));
        }
    }

    @Override
    public <R> R onStage(Function<Stage, R> action) {
        return action.apply(primaryStage);
    }

}
