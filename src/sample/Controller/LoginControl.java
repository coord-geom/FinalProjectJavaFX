package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import sample.Model.AES;
import sample.Model.Alerts;
import sample.Model.CorruptFileException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;


public class LoginControl implements Initializable {

    private Scanner s;

    @FXML
    private TextField usernameTF;
    @FXML
    private PasswordField passwordField;

    public static ArrayList<String> usernames = new ArrayList<>();
    public static ArrayList<String> passwords = new ArrayList<>();
    public static AES aes = new AES();

    @Override //TODO: Encrypt with AES to make secure!!!
    public void initialize(URL url, ResourceBundle resourceBundle){
        try {
            s = new Scanner(new File(System.getProperty("user.dir") + "/users.csv"));
            while(s.hasNext()){
                String str = aes.decrypt(s.nextLine(),"supercalifragilisticexpialidocious");
                if(!str.matches("[A-Za-z0-9_.]+,.{5,}"))
                    throw new CorruptFileException("");
                String[] data = str.split(",");
                if(data[0].matches(".*[^A-Za-z0-9_.].*"))
                    throw new CorruptFileException("");
                usernames.add(data[0]);
                passwords.add(data[1]);
            }
        }
        catch (FileNotFoundException e) {
            Alerts.errorAlert("File cannot be found!","oh noes you corruptz!!!!");
        }
        catch (CorruptFileException e) {
            Alerts.errorAlert("File LE KORUPZ!!!!","ahgh$%9@!&$#help$3dj&");
        }
    }
    // TODO: what if two repeats in file?
    public void loginAction(){
        String name = usernameTF.getText();
        String password = passwordField.getText();
        if(name.equals(""))
            Alerts.warningAlert("No username input!","Please key in a valid username!");
        else if(password.equals(""))
            Alerts.warningAlert("No password input!","Please key in a valid password!");
        else{
            int id = -1;
            for(int i=0;i<usernames.size();++i){
                if(usernames.get(i).equals(name)){
                    id = i;
                    break;
                }
            }
            if(id == -1)
                Alerts.warningAlert("Username not found!");
            else if(!password.equals(passwords.get(id)))
                Alerts.warningAlert("Wrong password!");
            else{
                //Alerts.successAlert("Login successful!","Welcome to ConspicioAlgo!");
                try{
                    Parent root = FXMLLoader.load(getClass().getResource("/sample/View/fxmlFiles/StartPage.fxml"));
                    Stage stage = (Stage) usernameTF.getScene().getWindow();
                    stage.setTitle("Welcome!");
                    stage.setScene(new Scene(root));
                    stage.setResizable(false);
                    stage.show();
                } catch(IOException e){
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public void newAccountDisplayAction(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/sample/View/fxmlFiles/CreateAccount.fxml"));
            Stage stage = (Stage) usernameTF.getScene().getWindow();
            stage.setTitle("Create an account");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        }catch(IOException e){

        }
    }

    public void enterLogin(KeyEvent event){
        if(event.getCode().equals(KeyCode.ENTER))
            loginAction();
    }

}
