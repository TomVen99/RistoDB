package it.unibo.ristoDB.view;

import java.net.URL;
import java.util.ArrayList;
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

public class BackOfficeController {

    private final Features features;
    private ViewImpl view;
    ObservableList<Category> categories = FXCollections.observableArrayList();
    ObservableList<Product> products;
    ObservableList<Product> productsByCategory;

    public BackOfficeController(ViewImpl view, Features features) {
        this.view = view;
        this.features = features;
    }

    @FXML
    void initialize() {
        /*categories = features.viewAllCategory();
        products = features.viewAllProducts();
        /*categories.add(new Category(0, "uno", new ArrayList<>()));
        categories.add(new Category(1, "due", new ArrayList<>()));*/
        categories.forEach(c-> {
            comboBoxCategoryAddProduct.getItems().add(c.getName());
            comboBoxCategoryRemovingProduct.getItems().add(c.getName());
        });

    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addCategoryButton;

    @FXML
    private Button addEmployeeButton;

    @FXML
    private Button addProductButton;

    @FXML
    private Button goToFOButton;

    @FXML
    private Button removeProductButton;

    @FXML
    private TextField categoryName;

    @FXML
    private ComboBox<String> comboBoxCategoryAddProduct;

    @FXML
    private ComboBox<String> comboBoxCategoryRemovingProduct;

    @FXML
    private ComboBox<String> comboBoxProductToRemove;

    @FXML
    private TextField employeeName;

    @FXML
    private TextField employeePassword;

    @FXML
    private TextField employeeLastname;

    @FXML
    private TextField employeeUser;

    @FXML
    private TextField productName;

    @FXML
    private TextField productPrice;

    @FXML
    private Button viewBestEmployeeButton;

    @FXML
    private Button viewBestSellingProductsButton;

    @FXML
    private TableView<?> viewBestSellingProductsTableView;

    @FXML
    private Button viewMostBusyMomentButton;

    @FXML
    private Label viewMostBusyMomentLabel;

    @FXML
    void addEmployee(ActionEvent event) {
        features.addEmployee(
            employeeName.getText(),
            employeeLastname.getText(),
            employeeUser.getText(),
            employeePassword.getText());
        
        employeeName.clear();
        employeeLastname.clear();
        employeeUser.clear();
        employeePassword.clear();
    }

    @FXML
    void addCategory(ActionEvent event) {
        System.out.println(categoryName.getText());
        categoryName.clear();
        features.addCategory(categoryName.getText());
    }

    @FXML
    void addProduct(ActionEvent event) {
        /*features.addProduct(productName.getText(),
                             Float.parseFloat(productPrice.getText()),
                             categories.get(comboBoxCategoryAddProduct.getSelectionModel().getSelectedIndex()).getId());*/
        productName.clear();
        productPrice.clear();
        comboBoxCategoryAddProduct.getSelectionModel().clearSelection();
        this.initialize();
    }

    @FXML
    void updateProductComboBox(ActionEvent event) {
            /*productsByCategory = features.viewProductsByCategory(
                categories.get(comboBoxCategoryRemovingProduct.getSelectionModel().getSelectedIndex()).getId());*/
        productsByCategory.forEach(p->comboBoxProductToRemove.getItems().add(p.getName()));
    }

    @FXML
    void removeProduct(ActionEvent event) {
        features.removeProduct(productsByCategory.get(comboBoxProductToRemove
            .getSelectionModel().getSelectedIndex()).getId());
        comboBoxProductToRemove.getItems().clear();
        this.initialize();
    }

    @FXML
    void viewBestEmployee(ActionEvent event) {
        features.viewBestEmployee(); /*mi ritornerebbe una mappa */
    }

    @FXML
    void viewBestSellingProducts(ActionEvent event) {
        /*viewBestSellingProductsTableView.
        features.viewBestSellingProducts();*/
    }

    @FXML
    void viewMostBusyMoment(ActionEvent event) {

    }

    @FXML
    void goToFrontOffice(ActionEvent event) {
        view.setFrontOfficeScene();
    }

}
