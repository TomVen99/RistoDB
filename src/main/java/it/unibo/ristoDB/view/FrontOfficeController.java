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
import javafx.scene.text.Text;

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
    @FXML private Text errorMessage;

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
        errorMessage.setOpacity(0);
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
        System.out.println("fatto");
    }

    @FXML
    void showReceipt(ActionEvent event) {
        int table = comboBoxSelectTable.getSelectionModel().getSelectedItem();
        if(features.verifyCovered(table)) {
            totalLabel.setText(Float.toString(features.showReceiptTotal(table)));
            showReceiptOrder(orderListTableView, features.showReceiptOrder(table));
            features.addReceipt(table);
            features.closeTable(table);
        }else {
            errorMessage.setOpacity(100);
        }
    }

    private void showReceiptOrder(final TableView view, final ObservableList<ReceiptsOrder> data) {
        view.getColumns().clear();
        final TableColumn<ReceiptsOrder, String> productName = new TableColumn<>("Prodotto");
        productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        final TableColumn<ReceiptsOrder, Integer> quantity = new TableColumn<>("Quantità");
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        final TableColumn<ReceiptsOrder, String> price = new TableColumn<>("Prezzo unitario");
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
        showOpenedTables(openedTableTableView,features.viewOpenedTables());
    }

    private void showOpenedTables(final TableView view, final ObservableList<Table> data) {
        view.getColumns().clear();
        final TableColumn<Table, String> number = new TableColumn<>("Numero");
        number.setCellValueFactory(new PropertyValueFactory<>("number"));
        final TableColumn<Table, Integer> maxPeople = new TableColumn<>("Max_pers");
        maxPeople.setCellValueFactory(new PropertyValueFactory<>("maxPeople"));
        view.getColumns().addAll(number, maxPeople);
        view.setItems(data);
        System.out.println("stampato");
    }

}
