package it.unibo.ristoDB;

import javafx.application.Application;

/** Main application entry-point's class. */
public final class RistoDB {
    private RistoDB() { }

    /**
     * Main application entry-point.
     * @param args
     */
    public static void main(final String[] args) {
        Application.launch(RistoDBApp.class, args);
    }
}
