package controllers;

import client.ClientController;
import clientserverdata.Reply;
import cmd.Command;
import consolehandler.ClientInterpreter;
import consolehandler.CommandInterpreter;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUp {
    @FXML
    public Text logtext;

    @FXML
    public Text passwtext;

    @FXML
    private TextField portField;

    @FXML
    private Text statusCon;

    @FXML
    private TextField dstPort;

    @FXML
    private Button connectBut;

    @FXML
    private TextField loginvalue;

    @FXML
    private PasswordField password;

    @FXML
    private Button signInBut;

    @FXML
    private Button signUpBut;

    @FXML
    void cnnct(ActionEvent event) {

        try {
            ClientController.setPort(Integer.parseInt(portField.getText()));
            ClientController.setDestPort(Integer.parseInt(dstPort.getText()));
        }
        catch (Exception e){
            statusCon.setText("WrongPort");
            statusCon.setVisible(true);
            logtext.setVisible(false);
            loginvalue.setVisible(false);
            passwtext.setVisible(false);
            password.setVisible(false);
            signInBut.setVisible(false);
            signUpBut.setVisible(false);
            loginvalue.setDisable(true);
            password.setDisable(true);
            signInBut.setDisable(true);
            signUpBut.setDisable(true);
            return;
        }

        if(ClientController.connect()){
            statusCon.setText("ConnectionStabled");
            logtext.setVisible(true);
            loginvalue.setVisible(true);
            passwtext.setVisible(true);
            password.setVisible(true);
            signInBut.setVisible(true);
            signUpBut.setVisible(true);
            loginvalue.setDisable(false);
            password.setDisable(false);
            signInBut.setDisable(false);
            signUpBut.setDisable(false);

        }
        else {
            statusCon.setText("ConnectionFailed");
            logtext.setVisible(false);
            loginvalue.setVisible(false);
            passwtext.setVisible(false);
            password.setVisible(false);
            signInBut.setVisible(false);
            signUpBut.setVisible(false);
            loginvalue.setDisable(true);
            password.setDisable(true);
            signInBut.setDisable(true);
            signUpBut.setDisable(true);
        }
        statusCon.setVisible(true);
    }

    @FXML
    void login(ActionEvent event) {
        ClientInterpreter interpreter = new ClientInterpreter();
        Reply result = interpreter.handle(new String[] {"login", loginvalue.getText(), password.getText()});
        if ("Approved".equals(result.getAnswer().split(",")[0])){
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/gui/fxmls/mainWindow.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = new Stage();
            signInBut.getScene().getWindow().hide();
            stage.setTitle("Lab 7 Reg");
            stage.setScene(new Scene(root));
            stage.show();
        }
        else {
            logtext.setText("Wrong");
        }
    }

    @FXML
    void register(ActionEvent event) {
        ClientInterpreter interpreter = new ClientInterpreter();
        Reply result = interpreter.handle(new String[] {"register", loginvalue.getText(), password.getText()});
        if ("Approved".equals(result.getAnswer().split(",")[0])){
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/gui/fxmls/mainWindow.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            signUpBut.getScene().getWindow().hide();
            Stage stage = new Stage();
            stage.setTitle("Lab7Reg");
            stage.setScene(new Scene(root));
            stage.show();

        }
    }

}
