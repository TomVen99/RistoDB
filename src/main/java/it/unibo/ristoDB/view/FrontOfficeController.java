package it.unibo.ristoDB.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class FrontOfficeController {

    private ViewImpl view;
    private Stage stage;

    public FrontOfficeController(ViewImpl view, Stage stage) {
        this.view = view;
        this.stage = stage;
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button OrderButton;

    @FXML
    private ComboBox<?> comboBoxSelectTable;

    @FXML
    private Button receiptButton;

    @FXML
    private Label total;

    @FXML
    void openOrderScene(ActionEvent event) {
        try {
            final var loader = new FXMLLoader(ClassLoader.getSystemResource("layouts/orders.fxml"));
            loader.setController(new OrdersController(view));
            final Parent root = loader.load();
            final Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void showReceipt(ActionEvent event) {

    }

    @FXML
    void initialize() {
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
