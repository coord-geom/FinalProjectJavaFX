package sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AlgoControl{

    @FXML
    private Button sortButton;

    public void viewSort() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/sample/View/fxmlFiles/sorting.fxml"));
            Stage stage = (Stage) sortButton.getScene().getWindow();
            stage.setTitle("Sorting");
            //stage.setMaximized(true);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void viewGraph(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/sample/View/fxmlFiles/graphs.fxml"));
            Stage stage = (Stage) sortButton.getScene().getWindow();
            stage.setTitle("Graph Theory");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void viewDS(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/sample/View/fxmlFiles/dataStruct.fxml"));
            Stage stage = (Stage) sortButton.getScene().getWindow();
            stage.setTitle("Data Structures");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void returnBack(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/sample/View/fxmlFiles/StartPage.fxml"));
            Stage stage = (Stage) sortButton.getScene().getWindow();
            stage.setTitle("Welcome!");
            stage.setScene(new Scene(root));
            stage.show();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

}
