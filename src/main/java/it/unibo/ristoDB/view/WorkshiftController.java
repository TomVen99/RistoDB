package it.unibo.ristoDB.view;

import it.unibo.ristoDB.db.Employee;
import it.unibo.ristoDB.model.Features;
import it.unibo.ristoDB.utils.Utils;

import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

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

public class WorkshiftController {

    private enum shiftTipologyEnum {Pranzo, Cena;}
    private final ObservableList<String> shiftTipology = FXCollections.observableArrayList();
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
    @FXML private TextField employeeUserAssociate;
    @FXML private TableView<String> employeeView;
    @FXML private ComboBox<String> comboBoxShiftAssociate;
    @FXML private ComboBox<String> comboBoxDayMoment;
    @FXML private TextField shiftDate;
    @FXML private TextField shiftDateAssociate;
    @FXML private Button viewEmployeesOnShiftButton;
    
    @FXML
    void addEmployee(ActionEvent event) {
        if(features.addEmployee(employeeName.getText(), employeeLastname.getText(),
                employeeUser.getText(), employeePassword.getText())) {
                    System.out.println("utente aggiunto");
                }else {
                    System.out.println("errore");
                }
        employeeName.clear();
        employeeLastname.clear();
        employeeUser.clear();
        employeePassword.clear();
    }

    @FXML
    void associateEmployeeShift(ActionEvent event) {
        if(features.findShift(shiftDateAssociate.getText(),comboBoxShiftAssociate.getSelectionModel().getSelectedItem())
            && features.findUser(employeeUserAssociate.getText())){
                features.associateEmployeeShift(shiftDateAssociate.getText(),
                    comboBoxShiftAssociate.getSelectionModel().getSelectedItem(), employeeUserAssociate.getText());
                    shiftDateAssociate.clear();
                    employeeUserAssociate.clear();                    
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
        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date date = sdf1.parse(shiftDate.getText());
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            System.out.println(sqlDate);
            showEmployees(employeeView, features.viewEmployeesOnShift( sqlDate,
                comboBoxDayMoment.getSelectionModel().getSelectedItem()));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    void initialize() {
        for(int i = 0; i < shiftTipologyEnum.values().length; i++) {
            shiftTipology.add(shiftTipologyEnum.values()[i].toString());
        }
        comboBoxDayMoment.getItems().addAll(shiftTipology);
        comboBoxShiftAssociate.getItems().addAll(shiftTipology);
    }

    private void showEmployees(final TableView<String> view, final ObservableList<String> data) {
        view.getColumns().clear();
        final TableColumn<String, String> name = new TableColumn<>("name");
        //name.setCellValueFactory(new PropertyValueFactory<>("name"));
        final TableColumn<String, String> username = new TableColumn<>("username");
        //username.setCellValueFactory(new PropertyValueFactory<>("username"));
        final TableColumn<String, String> lastname = new TableColumn<>("lastname");
        //lastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        final TableColumn<String, String> date = new TableColumn<>("date");
        //date.setText("pippo");
        //date.setCellValueFactory(new PropertyValueFactory<>("date"));
        final TableColumn<String, String> dayMoment = new TableColumn<>("day_Moment");
        //dayMoment.setCellValueFactory(new PropertyValueFactory<>("day_Momnet"));
        view.getColumns().addAll(username, name, lastname, date, dayMoment);
        //view.setItems(data);
    }

}

