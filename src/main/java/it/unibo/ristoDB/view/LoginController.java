package it.unibo.ristoDB.view;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

import it.unibo.ristoDB.db.ConnectionProvider;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/** The LoginController class is responsible for handling the title login scene and related actions. */
public class LoginController {

    /** The database name used to login. */
    private static final String DB_NAME = "ristodb";
    private final ViewImpl view;

    public LoginController(ViewImpl view) {
        this.view = view;   
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button bologinbutton;

    @FXML
    private Text errorMessage;

    @FXML
    private Button fologinbutton;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    void backOfficeLogin(ActionEvent event) {
        view.setBackOfficeScene();/*
        try {
            final ConnectionProvider prov = new ConnectionProvider(username.getText(), password.getText(), DB_NAME);
            final Connection connection = prov.getMySQLConnection();
            view.addConnection(connection);
            view.setBackOfficeScene();
        } catch (IllegalStateException exception) {
            errorMessage.setOpacity(100);
        }*/
    }

    @FXML
    void frontOfficeLogin(ActionEvent event) {
        
        view.setFrontOfficeScene();/*
        try {
            final ConnectionProvider prov = new ConnectionProvider(username.getText(), password.getText(), DB_NAME);
            final Connection connection = prov.getMySQLConnection();
            view.addConnection(connection);
            view.setFrontOfficeScene();
        } catch (IllegalStateException exception) {
            errorMessage.setOpacity(100);
        }*/
    }

    @FXML
    void initialize() {
        

    }

}
