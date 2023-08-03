package it.unibo.ristoDB.view;

import java.net.URL;
import java.util.ResourceBundle;

import it.unibo.ristoDB.db.Table;
import it.unibo.ristoDB.model.Features;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class FrontOfficeController {

    private ViewImpl view;
    private final Features features;
    ObservableList<Table> tables;

    public FrontOfficeController(ViewImpl view, final Features features) {
        this.view = view;
        this.features = features;
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
    private Button goToBOButton;

    @FXML
    void openOrderScene(ActionEvent event) {
        view.setOrderScene();
    }

    @FXML
    void showReceipt(ActionEvent event) {

    }

    @FXML
    void initialize() {
        tables = features.viewAllTables();
    }

    @FXML
    void goToBackOffice(ActionEvent event) {
        view.setBackOfficeScene();
    }

}
