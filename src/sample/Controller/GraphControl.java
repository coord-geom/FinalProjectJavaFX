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
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/sample/View/materials.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void loadGraphRep(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/sample/View/fxmlFiles/graphReps.fxml"));
            Stage stage = (Stage) tabPane.getScene().getWindow();
            stage.setTitle("Graph Representations");
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/sample/View/materials.css").toExternalForm());
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadGraphTrav(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/sample/View/fxmlFiles/graphTraversal.fxml"));
            Stage stage = (Stage) tabPane.getScene().getWindow();
            stage.setTitle("Graph Traversal");
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/sample/View/materials.css").toExternalForm());
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadGraphSPA(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/sample/View/fxmlFiles/graphSPA.fxml"));
            Stage stage = (Stage) tabPane.getScene().getWindow();
            stage.setTitle("Single Source Shortest Path Algorithms");
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/sample/View/materials.css").toExternalForm());
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
