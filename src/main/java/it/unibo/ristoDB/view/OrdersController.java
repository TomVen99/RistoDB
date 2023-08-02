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

public class OrdersController {

    private ViewImpl view;

    public OrdersController(ViewImpl view){
        this.view = view;
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addOrderButton;

    @FXML
    private Button backButton;

    @FXML
    private ComboBox<?> comboBoxCategories;

    @FXML
    private ComboBox<?> comboBoxProducts;

    @FXML
    private ComboBox<?> comboBoxSelectProductsToModify;

    @FXML
    private TextField newQuatityToAdd;

    @FXML
    private TableView<?> productsAlreadyOrdered;

    @FXML
    private Label quantityAlreadyOrdered;

    @FXML
    private TextField quantityToAdd;

    @FXML
    private Button updateOrderButton;

    @FXML
    void addProductToOrder(ActionEvent event) {

    }

    @FXML
    void modifyOrder(ActionEvent event) {

    }

    @FXML
    void setFrontOfficeScene(ActionEvent event) {
        view.setFrontOfficeScene();
    }

    @FXML
    void initialize() {
    }

}
