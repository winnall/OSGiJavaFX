package net.winnall.osgi.javafx;

import java.util.function.Consumer;
import java.util.function.Function;
import javafx.stage.Stage;

public interface JavaFXApplication {

    void onStage(Consumer<Stage> action);

    <R> R onStage(Function<Stage, R> action);
}
