package it.unibo.ristoDB.view;

import java.net.URL;
import java.util.ResourceBundle;

import it.unibo.ristoDB.db.Table;
import it.unibo.ristoDB.model.Features;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class FrontOfficeController {

    private ViewImpl view;
    private final Features features;
    ObservableList<Table> tables = FXCollections.observableArrayList();

    public FrontOfficeController(ViewImpl view, final Features features) {
        this.view = view;
        this.features = features;
    }

        @FXML
    void initialize() {
        orderButton.setDisable(true);
        receiptButton.setDisable(true);
        tables.add(new Table(0, false, 0));/*features.viewAllTables();*/
        tables.add(new Table(1, false, 0));
        tables.forEach(t->comboBoxSelectTable.getItems().add(t.getNumber()));
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button orderButton;

    @FXML
    private ComboBox<Integer> comboBoxSelectTable;

    @FXML
    private Button receiptButton;

    @FXML
    private Label total;

    @FXML
    private Button goToBOButton;

    
    @FXML
    void enableButtons(ActionEvent event) {
        orderButton.setDisable(false);
        receiptButton.setDisable(false);
    }

    @FXML
    void openOrderScene(ActionEvent event) {
        view.setOrderScene(comboBoxSelectTable.getSelectionModel().getSelectedItem());
    }

    @FXML
    void showReceipt(ActionEvent event) {
        
    }

    @FXML
    void goToBackOffice(ActionEvent event) {
        view.setBackOfficeScene();
    }

}
