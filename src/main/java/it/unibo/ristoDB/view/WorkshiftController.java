package it.unibo.ristoDB.view;

import it.unibo.ristoDB.db.User;
import it.unibo.ristoDB.model.Features;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class WorkshiftController {

    private enum shiftTipologyEnum {Pranzo, Cena;}
    private final ObservableList<String> shiftTipology = FXCollections.observableArrayList();
    private ObservableList<User> users = FXCollections.observableArrayList();
    private final Features features;
    private ViewImpl view;

    public WorkshiftController(ViewImpl view, Features features) {
        this.features = features;
        this.view = view;
    }

    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private Button addEmployeeButton;
    @FXML private Button associateButton;
    @FXML private Button backButton;
    @FXML private TextField employeeLastname;
    @FXML private TextField employeeName;
    @FXML private TextField employeePassword;
    @FXML private TextField employeeUser;
    @FXML private ComboBox<String> comboBoxEmployeeUserAssociate;
    @FXML private TableView<String> employeeView;
    @FXML private ComboBox<String> comboBoxShiftAssociate;
    @FXML private ComboBox<String> comboBoxDayMoment;
    @FXML private TextField shiftDateAssociate;
    @FXML private Button viewEmployeesOnShiftButton;
    @FXML private Text errorMessage;
    @FXML private ComboBox<Date> comboBoxShiftDate;
    @FXML private ComboBox<Date> comboBoxShiftDateAssociate;
    
    @FXML
    void addEmployee(ActionEvent event) {
        if(features.addEmployee(employeeName.getText(), employeeLastname.getText(),
                employeeUser.getText(), employeePassword.getText())) {
                    System.out.println("utente aggiunto");
                }else {
                    errorMessage.setOpacity(100);
                }
        employeeName.clear();
        employeeLastname.clear();
        employeeUser.clear();
        employeePassword.clear();
        this.initialize();
    }

    @FXML
    void associateEmployeeShift(ActionEvent event) {
        java.sql.Date sqlDate = new java.sql.Date(comboBoxShiftDateAssociate.getSelectionModel().getSelectedItem().getTime());
        if(features.findShift(sqlDate,comboBoxShiftAssociate.getSelectionModel().getSelectedItem())
            && features.findUser(comboBoxEmployeeUserAssociate.getSelectionModel().getSelectedItem())){
                features.associateEmployeeShift(sqlDate,
                    comboBoxShiftAssociate.getSelectionModel().getSelectedItem(),
                    comboBoxEmployeeUserAssociate.getSelectionModel().getSelectedItem());         
        }else{
            System.out.println("errore ricerca utente o turno non presente");
        }
    }

    @FXML
    void goToBO(ActionEvent event) {
        view.setBackOfficeScene();
    }

    @FXML
    void viewEmployeesOnShift(ActionEvent event) {
        java.sql.Date sqlDate = new java.sql.Date(comboBoxShiftDate.getSelectionModel().getSelectedItem().getTime());
        showEmployees(employeeView, features.viewEmployeesOnShift( sqlDate,
            comboBoxDayMoment.getSelectionModel().getSelectedItem()));
    }

    @FXML
    void initialize() {
        shiftTipology.clear();
        for(int i = 0; i < shiftTipologyEnum.values().length; i++) {
            shiftTipology.add(shiftTipologyEnum.values()[i].toString());
        }
        comboBoxDayMoment.getItems().clear();
        comboBoxShiftAssociate.getItems().clear();
        comboBoxDayMoment.getItems().addAll(shiftTipology);
        comboBoxShiftAssociate.getItems().addAll(shiftTipology);
        users = features.viewAllUsers();
        users.remove(0);
        comboBoxEmployeeUserAssociate.getItems().clear();
        comboBoxEmployeeUserAssociate.getItems().addAll(users.stream().map(u->u.getUsername()).collect(Collectors.toList()));
        comboBoxShiftDate.getItems().addAll(features.getAllWorkshift());
        comboBoxShiftDateAssociate.getItems().addAll(features.getAllWorkshift());
    }

    private void showEmployees(final TableView view, final ObservableList<User> data) {
        view.getColumns().clear();
        final TableColumn<User, String> name = new TableColumn<>("Nome");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        final TableColumn<User, Integer> lastname = new TableColumn<>("Cognome");
        lastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        final TableColumn<User, String> username = new TableColumn<>("Username");
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        view.getColumns().addAll(name, lastname, username);
        System.out.println("stampato");
        view.setItems(data);
    }

}

