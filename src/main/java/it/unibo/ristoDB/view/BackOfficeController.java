package it.unibo.ristoDB.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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
    ObservableList<Product> products = FXCollections.observableArrayList();
    ObservableList<Product> productsByCategory = FXCollections.observableArrayList();
    ObservableList<Date> dates = FXCollections.observableArrayList();

    public BackOfficeController(ViewImpl view, Features features) {
        this.view = view;
        this.features = features;
    }

    @FXML
    void initialize() {
        comboBoxCategoryAddProduct.getItems().clear();
        categories = features.viewAllCategory();
        comboBoxCategoryRemovingProduct.getItems().clear();
        products = features.viewAllProducts();
        dates = features.viewAllDate();
        System.out.println("fatto query");
        comboBoxChooseDate.getItems().addAll(dates);
        System.out.println("caricato combo");
        /*categories.add(new Category(0, "uno", new ArrayList<>()));
        categories.add(new Category(1, "due", new ArrayList<>()));
        Date d = new Date();
        dates.add(d);*/
        comboBoxCategoryAddProduct.getItems().addAll(categories.stream().map(c->c.getName()).collect(Collectors.toList()));
        comboBoxCategoryRemovingProduct.getItems().addAll(categories.stream().map(c->c.getName()).collect(Collectors.toList()));
    }

    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private Button addCategoryButton;
    @FXML private Button addEmployeeButton;
    @FXML private Text errorMessage;
    @FXML private Button addProductButton;
    @FXML private Button goToFOButton;
    @FXML private Button removeProductButton;
    @FXML private TextField categoryName;
    @FXML private ComboBox<String> comboBoxCategoryAddProduct;
    @FXML private ComboBox<String> comboBoxCategoryRemovingProduct;
    @FXML private ComboBox<String> comboBoxProductToRemove;
    @FXML private TextField employeeName;
    @FXML private TextField employeePassword;
    @FXML private TextField employeeLastname;
    @FXML private TextField employeeUser;
    @FXML private TextField productName;
    @FXML private TextField productPrice;
    @FXML private Button viewBestEmployeeButton;
    @FXML private Button viewBestSellingProductsButton;
    @FXML private TableView<?> viewBestSellingProductsTableView;
    @FXML private Button viewAvarageExpenseButton;
    @FXML private ComboBox<Date> comboBoxChooseDate;
    @FXML private Label bestEmployeeLabel;
    @FXML private Label viewTotalCoveredLabel;
    @FXML private Label viewAvarageExpenseLabel;
    @FXML private Button viewAvaragePersPerTableButton;
    @FXML private Label viewAvaragePersPerTableLabel;    
    @FXML private Button manageWorkshiftButton;
    @FXML private Button viewTotalCoveredButton;

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
        features.addCategory(categoryName.getText());
        this.initialize();
        categoryName.clear();
    }

    @FXML
    void addProduct(ActionEvent event) {
        features.addProduct(productName.getText(),
                             Float.parseFloat(productPrice.getText()),
                             categories.get(comboBoxCategoryAddProduct.getSelectionModel().getSelectedIndex()).getName());
        productName.clear();
        productPrice.clear();
        comboBoxCategoryAddProduct.getSelectionModel().clearSelection();
        this.initialize();
    }

    @FXML
    void updateProductComboBox(ActionEvent event) {
            productsByCategory = features.viewProductsByCategory(
                comboBoxCategoryRemovingProduct.getSelectionModel().getSelectedItem());
        productsByCategory.forEach(p->comboBoxProductToRemove.getItems().add(p.getName()));
    }

    @FXML
    void removeProduct(ActionEvent event) {
        features.removeProduct(productsByCategory.get(comboBoxProductToRemove
            .getSelectionModel().getSelectedIndex()).getId());
        comboBoxProductToRemove.getItems().clear();
        this.initialize();
    }

    /*possibile controllo aggiuntivo del parimerito */
    @FXML
    void viewBestEmployee(ActionEvent event) {
        var bestEmployee = features.viewBestEmployee();
        if(!bestEmployee.isEmpty()){
            String employee = bestEmployee.keySet().iterator().next();
            bestEmployeeLabel.setText(employee + " " + Float.toString(bestEmployee.get(employee)));
        }
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

    @FXML
    void openManageWorkshiftScene(ActionEvent event) {
        view.setManageWorkshiftScene();
    }

    @FXML
    void viewTotalCovered(ActionEvent event) {

    }

}
