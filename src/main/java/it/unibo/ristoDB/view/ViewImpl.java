package it.unibo.ristoDB.view;

import java.io.IOException;
import java.sql.Connection;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ViewImpl implements View{


    private final Stage stage;

    /**
     * Class constructor.
     * @param stage The main stage
     */
    public ViewImpl(final Stage stage) {
        stage.setTitle("RistoDB");
        stage.getIcons().add(new Image(ClassLoader.getSystemResource("images/icon.png").toString()));
        this.stage = stage;
        this.setLoginScene();
        stage.show();
    }

    /** {@inheritDoc} */
    @Override
    public void setLoginScene() {
        try {
            final var loader = new FXMLLoader(ClassLoader.getSystemResource("layouts/login.fxml"));
            loader.setController(new LoginController(this));
            final Parent root = loader.load();
            final Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** {@inheritDoc} */
    @Override
    public void setBackOfficeScene() {
        try {
            final var loader = new FXMLLoader(ClassLoader.getSystemResource("layouts/backOffice.fxml"));
            loader.setController(new BackOfficeController(this));//, features));
            final Parent root = loader.load();
            final Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** {@inheritDoc} */
    @Override
    public void setFrontOfficeScene() {
        try {
            final var loader = new FXMLLoader(ClassLoader.getSystemResource("layouts/frontOffice.fxml"));
            loader.setController(new FrontOfficeController(this, stage));//, features));
            final Parent root = loader.load();
            final Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** {@inheritDoc} */
    @Override
    public void addConnection(final Connection connection) {
        //features = new FeaturesImpl(connection);
    }

    
}
