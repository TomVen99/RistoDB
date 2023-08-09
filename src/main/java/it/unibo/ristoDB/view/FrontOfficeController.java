package it.unibo.ristoDB.view;

import java.net.URL;
import java.util.ResourceBundle;

import it.unibo.ristoDB.db.Category;
import it.unibo.ristoDB.db.Table;
import it.unibo.ristoDB.db.User;
import it.unibo.ristoDB.model.Features;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class FrontOfficeController {

    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private Button orderButton;
    @FXML private ComboBox<Integer> comboBoxSelectTable;
    @FXML private Button receiptButton;
    @FXML private Label total;
    @FXML private Button goToBOButton;
    @FXML private TableView<ReceiptsOrder> orderListTableView;
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
        features.setSelectedTableNumber(comboBoxSelectTable.getSelectionModel().getSelectedItem());
        view.setOrderScene(comboBoxSelectTable.getSelectionModel().getSelectedItem());
    }

    @FXML
    void showReceipt(ActionEvent event) {
        int table = comboBoxSelectTable.getSelectionModel().getSelectedItem();
        totalLabel.setText(Float.toString(features.showReceiptTotal(table)));
        showReceiptOrder(orderListTableView, features.showReceiptOrder(table));
    }

    private void showReceiptOrder(final TableView view, final ObservableList<ReceiptsOrder> data) {
        view.getColumns().clear();
        final TableColumn<ReceiptsOrder, String> productName = new TableColumn<>("Prodotto");
        productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        final TableColumn<ReceiptsOrder, Integer> quantity = new TableColumn<>("Quantità");
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        final TableColumn<ReceiptsOrder, String> price = new TableColumn<>("Prezzo");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        view.getColumns().addAll(productName, quantity, price);
        view.setItems(data);
        System.out.println("stampato");
    }

    @FXML
    void goToBackOffice(ActionEvent event) {
        view.setBackOfficeScene();
    }

    @FXML
    void viewOpenedTables(ActionEvent event) {

    }

}
