package sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.io.IOException;

public class GraphControl {

    @FXML
    private TabPane tabPane;

    public void returnBack(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/sample/View/fxmlFiles/algoPage.fxml"));
            Stage stage = (Stage) tabPane.getScene().getWindow();
            stage.setTitle("Algorithms");
            stage.setScene(new Scene(root));
            stage.show();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}
