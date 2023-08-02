package it.unibo.ristoDB.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class BackOfficeController {

    private ViewImpl view;
    public BackOfficeController(ViewImpl view) {
        this.view = view;
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
    private Button addProductButton1;

    @FXML
    private TextField categoryName;

    @FXML
    private ComboBox<?> comboBoxCategoryAddProduct;

    @FXML
    private ComboBox<?> comboBoxCategoryRemovingProduct;

    @FXML
    private ComboBox<?> comboBoxProductToRemove;

    @FXML
    private TextField employeeName;

    @FXML
    private TextField employeePassword;

    @FXML
    private TextField employeeSurname;

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
    void addCategory(ActionEvent event) {

    }

    @FXML
    void addEmployee(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert addCategoryButton != null : "fx:id=\"addCategoryButton\" was not injected: check your FXML file 'backOffice.fxml'.";
        assert addEmployeeButton != null : "fx:id=\"addEmployeeButton\" was not injected: check your FXML file 'backOffice.fxml'.";
        assert addProductButton != null : "fx:id=\"addProductButton\" was not injected: check your FXML file 'backOffice.fxml'.";
        assert addProductButton1 != null : "fx:id=\"addProductButton1\" was not injected: check your FXML file 'backOffice.fxml'.";
        assert categoryName != null : "fx:id=\"categoryName\" was not injected: check your FXML file 'backOffice.fxml'.";
        assert comboBoxCategoryAddProduct != null : "fx:id=\"comboBoxCategoryAddProduct\" was not injected: check your FXML file 'backOffice.fxml'.";
        assert comboBoxCategoryRemovingProduct != null : "fx:id=\"comboBoxCategoryRemovingProduct\" was not injected: check your FXML file 'backOffice.fxml'.";
        assert comboBoxProductToRemove != null : "fx:id=\"comboBoxProductToRemove\" was not injected: check your FXML file 'backOffice.fxml'.";
        assert employeeName != null : "fx:id=\"employeeName\" was not injected: check your FXML file 'backOffice.fxml'.";
        assert employeePassword != null : "fx:id=\"employeePassword\" was not injected: check your FXML file 'backOffice.fxml'.";
        assert employeeSurname != null : "fx:id=\"employeeSurname\" was not injected: check your FXML file 'backOffice.fxml'.";
        assert employeeUser != null : "fx:id=\"employeeUser\" was not injected: check your FXML file 'backOffice.fxml'.";
        assert productName != null : "fx:id=\"productName\" was not injected: check your FXML file 'backOffice.fxml'.";
        assert productPrice != null : "fx:id=\"productPrice\" was not injected: check your FXML file 'backOffice.fxml'.";
        assert viewBestEmployeeButton != null : "fx:id=\"viewBestEmployeeButton\" was not injected: check your FXML file 'backOffice.fxml'.";
        assert viewBestSellingProductsButton != null : "fx:id=\"viewBestSellingProductsButton\" was not injected: check your FXML file 'backOffice.fxml'.";
        assert viewBestSellingProductsTableView != null : "fx:id=\"viewBestSellingProductsTableView\" was not injected: check your FXML file 'backOffice.fxml'.";
        assert viewMostBusyMomentButton != null : "fx:id=\"viewMostBusyMomentButton\" was not injected: check your FXML file 'backOffice.fxml'.";
        assert viewMostBusyMomentLabel != null : "fx:id=\"viewMostBusyMomentLabel\" was not injected: check your FXML file 'backOffice.fxml'.";

    }

}
