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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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
        //categories = features.viewAllCategory();
        categories.forEach(c->comboBoxCategories.getItems().add(c.getName()));
        newQuantity.setDisable(true);
        comboBoxProducts.setDisable(true);
        addProductToOrderButton.setDisable(true);
        quantityToAdd.setDisable(true);
        updateOrderButton.setDisable(true);
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addProductToOrderButton;

    @FXML
    private Button backButton;

    @FXML
    private ComboBox<String> comboBoxCategories;

    @FXML
    private ComboBox<String> comboBoxProducts;

    @FXML
    private ComboBox<String> comboBoxSelectProductsToModify;

    @FXML
    private TextField newQuantity;

    @FXML
    private TableView<?> productsAlreadyOrdered;

    @FXML
    private Label quantityAlreadyOrdered;

    @FXML
    private TextField quantityToAdd;

    @FXML
    private Button updateOrderButton;

    @FXML
    void enableComboBoxProducts(ActionEvent event) {
        comboBoxProducts.setDisable(false);
        productsByCategory = features.viewProductsByCategory(categories.get(
            comboBoxCategories.getSelectionModel().getSelectedIndex()).getId());
        productsByCategory.forEach(p->comboBoxProducts.getItems().add(p.getName()));
    }

    @FXML
    void setDefaultQuantity(ActionEvent event) {
        quantityToAdd.setText("0");
        addProductToOrderButton.setDisable(false);
    }

    @FXML
    void addProductToOrder(ActionEvent event) {
        /*DEVO VERIFICARE SE ESISE UN ORDINE DI QUEL TAVOLO.
         * SE ESISTE DEVO FAR VEDERE L'ORDINE IN TABELLA E QUINDI FARE UNA MODIFICA DELL'ORDINE
         * SE NON ESISTE DEVO CREARE UN NUOVO ORDINE E COLLEGARGLI I VARI DETTAGLI ORDINI
         */
        int productId = productsByCategory.get(comboBoxProducts.getSelectionModel().getSelectedIndex()).getId();
        features.addOrderDetails(productId, Integer.parseInt(quantityToAdd.getText()));
    }

    
    @FXML
    void enableModifyOrderSection(ActionEvent event) {
        newQuantity.setDisable(false);
        newQuantity.setText("0");
        updateOrderButton.setDisable(false);
    }

    @FXML
    void modifyOrder(ActionEvent event) {

    }

    @FXML
    void setFrontOfficeScene(ActionEvent event) {
        view.setFrontOfficeScene();
    }
}
