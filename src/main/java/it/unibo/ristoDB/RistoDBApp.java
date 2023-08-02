package it.unibo.ristoDB;

import it.unibo.ristoDB.view.ViewImpl;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Sample JavaFX application.
 */
public final class RistoDBApp extends Application {

    @Override
    public void start(final Stage primaryStage) throws Exception {
        new ViewImpl(primaryStage);
    }
}
