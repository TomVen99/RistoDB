package it.unibo.ristoDB.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import it.unibo.ristoDB.db.Category;
import it.unibo.ristoDB.db.Product;
import it.unibo.ristoDB.db.Table;
import it.unibo.ristoDB.model.Features;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
        comboBoxChooseDate.getItems().addAll(dates);
        errorMessage.setOpacity(0);
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
    @FXML private TableView<BestProducts> viewBestSellingProductsTableView;
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

    @FXML
    void viewBestEmployee(ActionEvent event) {
        if(verifyDate(comboBoxChooseDate.getSelectionModel().getSelectedItem())){
            var bestEmployee = features.viewBestEmployee(comboBoxChooseDate.getSelectionModel().getSelectedItem());
            if(!bestEmployee.isEmpty()){
                String employee = bestEmployee.keySet().iterator().next();
                bestEmployeeLabel.setText(employee + " " + Float.toString(bestEmployee.get(employee)));
                errorMessage.setOpacity(0);
            }
        }else{
            errorMessage.setOpacity(100);
        }
    }

    @FXML
    void viewBestSellingProducts(ActionEvent event) {
        showBestSellingProducts(viewBestSellingProductsTableView, features.viewBestSellingProducts());
    }

    private void showBestSellingProducts(final TableView<BestProducts> view, final ObservableList<BestProducts> data) {
            view.getColumns().clear();
            final TableColumn<BestProducts, String> name = new TableColumn<>("Nome");
            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            final TableColumn<BestProducts, Integer> total = new TableColumn<>("Totale");
            total.setCellValueFactory(new PropertyValueFactory<>("total"));
            view.getColumns().addAll(name, total);
            view.setItems(data);
            System.out.println("stampato");
        }

    @FXML
    void viewAvarageExpense(ActionEvent event) {
        if(verifyDate(comboBoxChooseDate.getSelectionModel().getSelectedItem())){
            viewAvarageExpenseLabel.setText(Float.toString(
                features.viewAvarageExpense(comboBoxChooseDate.getSelectionModel().getSelectedItem())));
                errorMessage.setOpacity(0);
        }else{
            errorMessage.setOpacity(100);   
        }
    }   

    @FXML
    void viewAvaragePersPerTable(ActionEvent event) {
        if(verifyDate(comboBoxChooseDate.getSelectionModel().getSelectedItem())){
            viewAvaragePersPerTableLabel.setText(Float.toString(
                features.viewAvaragePeoplePerTable(comboBoxChooseDate.getSelectionModel().getSelectedItem())));   
                errorMessage.setOpacity(0);
        }else{
            errorMessage.setOpacity(100);   
        }
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
        if(verifyDate(comboBoxChooseDate.getSelectionModel().getSelectedItem())){
            viewTotalCoveredLabel.setText(Integer.toString(
                features.viewTotalCovered(comboBoxChooseDate.getSelectionModel().getSelectedItem())));
                errorMessage.setOpacity(0);
        }else{
            errorMessage.setOpacity(100);   
        }
    }

    private boolean verifyDate(Date date) {
        return date != null;
    }

}
