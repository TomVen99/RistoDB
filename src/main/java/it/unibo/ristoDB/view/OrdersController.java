package it.unibo.ristoDB.view;

import java.net.URL;
import java.util.ResourceBundle;

import it.unibo.ristoDB.db.Category;
import it.unibo.ristoDB.db.Product;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class OrdersController {

    private ViewImpl view;
    private final Features features;
    private ObservableList<Category> categories = FXCollections.observableArrayList();
    private ObservableList<Product> products;
    private ObservableList<Product> productsByCategory;
    private int selectedTable;

    public OrdersController(ViewImpl view, Features features){
        this.view = view;
        this.features = features;
    }

    public void setSelectedTable(int selectedTable) {
        this.selectedTable = selectedTable;
        System.out.println(selectedTable);
    }

    @FXML
    void initialize() {
        categories = features.viewAllCategory();
        categories.forEach(c->comboBoxCategories.getItems().add(c.getName()));
        quantity.setDisable(true);
        comboBoxProducts.setDisable(true);
        modifyOrderButton.setDisable(true);
        showReceiptOrder(productsAlreadyOrdered, features.showReceiptOrder(selectedTable));
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

    @FXML private Button backButton;
    @FXML private ComboBox<String> comboBoxCategories;
    @FXML private ComboBox<String> comboBoxProducts;
    @FXML private Button modifyOrderButton;
    @FXML private TextField quantity;
    @FXML private TableView<ReceiptsOrder> productsAlreadyOrdered;
    
    @FXML
    void enableComboBoxProducts(ActionEvent event) {
        comboBoxProducts.setDisable(false);
        comboBoxProducts.getItems().clear();;
        productsByCategory = features.viewProductsByCategory(categories.get(
            comboBoxCategories.getSelectionModel().getSelectedIndex()).getName());
        productsByCategory.forEach(p->comboBoxProducts.getItems().add(p.getName()));
    }

    @FXML
    void enableQuantity(ActionEvent event) {
        quantity.setText("0");
        quantity.setDisable(false);
        modifyOrderButton.setDisable(false);
    }

    @FXML
    void modifyOrder(ActionEvent event) {
        /*DEVO VERIFICARE SE ESISE UN ORDINE DI QUEL TAVOLO.
         * SE ESISTE DEVO FAR VEDERE L'ORDINE IN TABELLA E QUINDI FARE UNA MODIFICA DELL'ORDINE
         * SE NON ESISTE DEVO CREARE UN NUOVO ORDINE E COLLEGARGLI I VARI DETTAGLI ORDINI
         */
        int productId = productsByCategory.get(comboBoxProducts.getSelectionModel().getSelectedIndex()).getId();
        System.out.println("Codice prodotto " + productId);
        features.addOrderDetails(productId,Integer.parseInt(quantity.getText()));
        this.initialize();
        comboBoxCategories.getSelectionModel().selectFirst();
        comboBoxProducts.getItems().clear();
    }

    
    @FXML
    void enableModifyOrderSection(ActionEvent event) {
        quantity.setDisable(false);
        quantity.setText("0");
        modifyOrderButton.setDisable(false);
    }

    @FXML
    void setFrontOfficeScene(ActionEvent event) {
        view.setFrontOfficeScene();
    }
}
