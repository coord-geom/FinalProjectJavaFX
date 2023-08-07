package sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Model.General.Alerts;
import sample.Model.Graphs.Animation.BellmanFord;
import sample.Model.Graphs.Animation.Dijkstra;
import sample.Model.Graphs.GraphStructures.DWGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class GraphSPAControl implements Initializable {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button createEdgeBtn, clearGraphBtn, dijkstraBtn, bellmanFordBtn, uploadEdgesBtn;
    @FXML
    private TextField TF1, TF2, srcTF, weightTF;
    @FXML
    private Slider slider;

    private DWGraph graph;
    private Dijkstra dijkstra;
    private BellmanFord bellmanFord;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        graph = new DWGraph(anchorPane);
        dijkstra = new Dijkstra(graph,slider,srcTF,createEdgeBtn,clearGraphBtn,dijkstraBtn,bellmanFordBtn,uploadEdgesBtn);
        bellmanFord = new BellmanFord(graph,slider,srcTF,createEdgeBtn,clearGraphBtn,dijkstraBtn,bellmanFordBtn,uploadEdgesBtn);
        anchorPane.setOnMouseClicked(this::createVertex);
        uploadEdgesBtn.setTooltip(new Tooltip("Many edges to add? Key them into a file and load them here!"));
    }

    public void createVertex(MouseEvent e){ graph.addVertex(e.getX(),e.getY()); }

    public void createEdge(){
        try{
            int weight = Integer.parseInt(weightTF.getText());
            if(weight < 0)
                Alerts.warningAlert("Negative weights not allowed!","Please key in a positive weight!");
            else
                graph.addEdge(Integer.parseInt(TF1.getText()),Integer.parseInt(TF2.getText()),weight);
        }
        catch(NumberFormatException e){ Alerts.warningAlert("Input is in wrong format!"); }
    }

    public void uploadEdges(){
        Stage stage = new Stage();
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV files (*.csv)","*.csv"),
                new FileChooser.ExtensionFilter("TXT files (*.txt)","*.txt")
        );
        File file = fc.showOpenDialog(stage);
        if(file == null) return;
        try {
            Scanner s = new Scanner(file);
            while(s.hasNext()){
                String str = s.nextLine();
                if(!str.matches("\\d,\\d,(0|[1-9]\\d*)")) {
                    Alerts.warningAlert("File is in wrong format!");
                    return;
                }
                String[] data = str.split(",");
                int x = Integer.parseInt(data[0]), x1 = Integer.parseInt(data[1]), w = Integer.parseInt(data[2]);
                if(graph.inGraph(x) || graph.inGraph(x1)){
                    Alerts.warningAlert("Vertex not found in graph!");
                    return;
                }
                if(w < 0){
                    Alerts.warningAlert("Weight cannot be negative!");
                    return;
                }
            }
            s = new Scanner(file);
            while(s.hasNext()){
                String[] data = s.nextLine().split(",");
                int x = Integer.parseInt(data[0]), y = Integer.parseInt(data[1]), w = Integer.parseInt(data[2]);
                graph.addEdge(x,y,w);
            }
        } catch (FileNotFoundException e) {
            Alerts.errorAlert("File not found!","Please choose a valid file!");
        } catch (NumberFormatException e) {
            Alerts.warningAlert("Data is in wrong format!");
        }
    }

    public void clearGraph(){ graph.clearGraph(); }

    public void dijkstra(){ dijkstra.start(); }

    public void bellmanFord(){ bellmanFord.start(); }

}
