package it.unibo.ristoDB.view;

import it.unibo.ristoDB.model.Features;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class WorkshiftController {

    private final Features features;
    private ViewImpl view;
    public WorkshiftController(ViewImpl view, Features features) {
        this.features = features;
        this.view = view;
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addEmployeeButton;

    @FXML
    private Button associateButton;

    @FXML
    private Button backButton;

    @FXML
    private TextField employeeLastname;

    @FXML
    private TextField employeeName;

    @FXML
    private TextField employeePassword;

    @FXML
    private TextField employeeUser;

    @FXML
    private TextField employeeUserAssociate;

    @FXML
    private TableView<?> employeeView;

    @FXML
    private ComboBox<?> shiftAssociateComboBox;

    @FXML
    private ComboBox<?> shiftComboBox;

    @FXML
    private TextField shiftDate;

    @FXML
    private TextField shiftDateAssociate;

    @FXML
    void addEmployee(ActionEvent event) {

    }

    @FXML
    void associateEmployeeShift(ActionEvent event) {

    }

    @FXML
    void goToBO(ActionEvent event) {
        view.setBackOfficeScene();
    }

    @FXML
    void viewEmployeesOnShiftOnClick(ActionEvent event) {

    }

    @FXML
    void initialize() {

    }

}

