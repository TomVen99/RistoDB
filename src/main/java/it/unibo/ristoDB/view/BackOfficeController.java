package it.unibo.ristoDB.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.text.Text;

public class BackOfficeController {

    private final Features features;
    private ViewImpl view;
    ObservableList<Category> categories = FXCollections.observableArrayList();
    ObservableList<Product> products;
    ObservableList<Product> productsByCategory;
    ObservableList<Date> dates;

    public BackOfficeController(ViewImpl view, Features features) {
        this.view = view;
        this.features = features;
    }

    @FXML
    void initialize() {
        /*
        categories = features.viewAllCategory();
        products = features.viewAllProducts();
        categories.add(new Category(0, "uno", new ArrayList<>()));
        categories.add(new Category(1, "due", new ArrayList<>()));
        dates = features.viewAllDate();
        dates.forEach(d->comboBoxChooseDate.getItems().add(d));      
        categories.forEach(c-> {
            comboBoxCategoryAddProduct.getItems().add(c.getName());
            comboBoxCategoryRemovingProduct.getItems().add(c.getName());
        });*/


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
    private Text errorMessage;

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
    private Button viewAvarageExpenseButton;

    @FXML
    private ComboBox<Date> comboBoxChooseDate;

    @FXML
    private Label viewTotalCoveredLabel;

    @FXML
    private Label viewAvarageExpenseLabel;

    @FXML
    private Button viewAvaragePersPerTableButton;

    @FXML
    private Label viewAvaragePersPerTableLabel;

    @FXML
    void addEmployee(ActionEvent event) {
        if(!features.addEmployee(
            employeeName.getText(),
            employeeLastname.getText(),
            employeeUser.getText(),
            employeePassword.getText())) {
                errorMessage.setOpacity(100);
            }
        
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
    void viewAvarageExpense(ActionEvent event) {

    }   

    @FXML
    void viewAvaragePersPerTable(ActionEvent event) {

    }

    @FXML
    void goToFrontOffice(ActionEvent event) {
        view.setFrontOfficeScene();
    }

}
