package it.unibo.ristoDB.view;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

import it.unibo.ristoDB.db.ConnectionProvider;
import it.unibo.ristoDB.model.Features;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/** The LoginController class is responsible for handling the title login scene and related actions. */
public class LoginController {

    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private Button bologinbutton;
    @FXML private Text errorMessage;
    @FXML private Button fologinbutton;
    @FXML private PasswordField password;
    @FXML private TextField username;

    /** The database name used to login. */
    private static final String DB_NAME = "RistoDB";
    private Features features;
    private final ViewImpl view;

    public LoginController(ViewImpl view) {
        this.view = view;
        try {
            final ConnectionProvider prov = new ConnectionProvider("admin", "admin", DB_NAME);
            final Connection connection = prov.getMySQLConnection();
            view.addConnection(connection);
            System.out.println("connessione avvenuta");
        } catch (IllegalStateException exception) {
            errorMessage.setText("Errore in connessione al db");
            errorMessage.setOpacity(100);
        }
        this.features = view.getFeatures();   
    }

    public void setFeatures(Features features) {
        this.features = features;
    }

    @FXML
    void backOfficeLogin(ActionEvent event) {
        System.out.println("*************************" + features);
        if(features.findUser(username.getText(), password.getText())) {
            view.setBackOfficeScene();
        }else {
            errorMessage.setText("password o nome utente errati");
            errorMessage.setOpacity(100);
        }
    }

    @FXML
    void frontOfficeLogin(ActionEvent event) {
        if(features.findUser(username.getText(), password.getText())) {
            view.setFrontOfficeScene();
        }else {
            errorMessage.setText("password o nome utente errati");
            errorMessage.setOpacity(100);
        }
        
        
        /*try {
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
