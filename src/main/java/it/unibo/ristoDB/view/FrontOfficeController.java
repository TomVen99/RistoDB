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
import javafx.scene.control.TableView;

public class FrontOfficeController {

    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private Button orderButton;
    @FXML private ComboBox<Integer> comboBoxSelectTable;
    @FXML private Button receiptButton;
    @FXML private Label total;
    @FXML private Button goToBOButton;
    @FXML private TableView<?> orderListTableView;
    @FXML private TableView<?> openedTableTableView;
    @FXML private Label totalLabel;
    @FXML private Button openedTablesButton;

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
        tables = features.viewAllTables();
        /*tables.add(new Table(0, false, 0));
        tables.add(new Table(1, false, 0));*/
        tables.forEach(t->comboBoxSelectTable.getItems().add(t.getNumber()));
    }

    
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

    @FXML
    void viewOpenedTables(ActionEvent event) {

    }

}
