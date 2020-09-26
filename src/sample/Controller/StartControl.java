package sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartControl implements Initializable {

    @FXML
    private ImageView logoImgView;
    @FXML
    private Button logoutButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        logoutButton.setTooltip(new Tooltip("Log out of this application"));
    }

    public void profileAction(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/sample/View/fxmlFiles/profile.fxml"));
            Stage stage = (Stage) logoImgView.getScene().getWindow();
            stage.setTitle("Profile");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        }catch(IOException e){

        }
    }

    public void algoAction(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/sample/View/fxmlFiles/algoPage.fxml"));
            Stage stage = (Stage) logoImgView.getScene().getWindow();
            stage.setTitle("Algorithms");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        }catch(IOException e){

        }
    }

    public void settingsAction(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/sample/View/fxmlFiles/settings.fxml"));
            Stage stage = (Stage) logoImgView.getScene().getWindow();
            stage.setTitle("Settings");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        }catch(IOException e){

        }
    }

    public void logoutAction(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/sample/View/fxmlFiles/Login.fxml"));
            Stage stage = (Stage) logoImgView.getScene().getWindow();
            stage.setTitle("Login to ConspicioAlgo");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        }catch(IOException e){

        }
    }
}
