package sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Model.General.Alerts;
import sample.Model.Graphs.Animation.BFS;
import sample.Model.Graphs.Animation.DFS;
import sample.Model.Graphs.GraphStructures.DUGraph;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GraphTraversalControl implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField TF1, TF2, srcTF;
    @FXML
    private Slider slider;
    @FXML
    private Button createEdgeBtn, clearGraphBtn, dfsBtn, bfsBtn;

    private DUGraph graph;
    private BFS bfs;
    private DFS dfs;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        graph = new DUGraph(anchorPane);
        bfs = new BFS(graph,slider,createEdgeBtn,clearGraphBtn,dfsBtn,bfsBtn);
        dfs = new DFS(graph,slider,createEdgeBtn,clearGraphBtn,dfsBtn,bfsBtn);
        anchorPane.setOnMouseClicked(this::createVertex);
    }

    public void returnBack(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/sample/View/fxmlFiles/graphs.fxml"));
            Stage stage = (Stage) anchorPane.getScene().getWindow();
            stage.setTitle("Graph Theory");
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/sample/View/materials.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void createVertex(MouseEvent e){ graph.addVertex(e.getX(),e.getY()); }

    public void createEdge(){
        try{ graph.addEdge(Integer.parseInt(TF1.getText()),Integer.parseInt(TF2.getText())); }
        catch(NumberFormatException e){ Alerts.warningAlert("Input is in wrong format!"); }
    }

    public void clearGraph(){ graph.clearGraph(); }

    public void bfs(){
        try{ bfs.animate(Integer.parseInt(srcTF.getText())); }
        catch(NumberFormatException e){ Alerts.warningAlert("Input is in the wrong format!","Please enter a valid source vertex!"); }
    }

    public void dfs(){
        try{ dfs.animate(Integer.parseInt(srcTF.getText())); }
        catch(NumberFormatException e){ Alerts.warningAlert("Input is in the wrong format!","Please enter a valid source vertex!"); }
    }

    public void loadInfo(){}
    public void loadCode(){} // TODO: 3/10/2020 make info and code pages for bfs/dfs 
}
