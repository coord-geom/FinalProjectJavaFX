package sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Model.AES;
import sample.Model.Alerts;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CreateAccountControl {

    @FXML
    private TextField usernameTF;
    @FXML
    private PasswordField passwordField, confirmPasswordField;

    public ArrayList<String> usernames = LoginControl.usernames;
    public ArrayList<String> passwords = LoginControl.passwords;
    public AES aes = LoginControl.aes;

    public void createAccountAction(){
        String name = usernameTF.getText();
        String password = passwordField.getText();
        if(name.equals(""))
            Alerts.warningAlert("Username is empty!","Please ensure your username has at least one alphanumeric character, underscore or period!");
        else if(name.matches(".*[^A-Za-z0-9_.].*"))
            Alerts.warningAlert("Username has invalid characters!","Please ensure your username only has alphanumeric characters, underscores and periods only!");
        else if(password.length() < 5)
            Alerts.warningAlert("Password has less than 5 characters!","Please create a password with more than 5 characters!");
        else if(password.contains(","))
            Alerts.warningAlert("Password cannot contain commas!","Please create another password!");
        else if(!confirmPasswordField.getText().equals(password))
            Alerts.warningAlert("Passwords do not match!","Please retype the password correctly!");
        else{
            boolean repeat = false;
            for(String user: usernames){
                if(name.equals(user)){
                    repeat = true;
                    break;
                }
            }
            if(repeat)
                Alerts.warningAlert("Username has been used!", "Please choose another username!");
            else{
                try{
                    PrintWriter pw = new PrintWriter(new FileWriter(System.getProperty("user.dir") + "/users.csv", true));
                    pw.println(aes.encrypt(name+","+password,"supercalifragilisticexpialidocious"));
                    pw.close();
                    usernames.add(name);
                    passwords.add(password);
                    Alerts.successAlert("Account created successfully!","You can login to ConspicioAlgo now!");
                    returnLoginAction();
                } catch (IOException e) {
                    Alerts.errorAlert("Error in file writing!");
                }
            }
        }
    }

    public void returnLoginAction(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/sample/View/fxmlFiles/Login.fxml"));
            Stage stage = (Stage) usernameTF.getScene().getWindow();
            stage.setTitle("Create an account");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
