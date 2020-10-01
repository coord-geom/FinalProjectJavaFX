package sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Model.Alerts;
import sample.Model.Graphs.DUGraph;
import sample.Model.Graphs.DWGraph;
import sample.Model.Graphs.UUGraph;
import sample.Model.Graphs.UWGraph;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GraphRepControl implements Initializable {

    @FXML
    private AnchorPane anchorPane, uuAP, uwAP, duAP, dwAP;
    @FXML
    private TextField uuTF1, uuTF2, uwTF1, uwTF2, uwTFWeight, duTF1, duTF2, dwTF1, dwTF2, dwTFWeight;

    private UUGraph uuGraph;
    private DUGraph duGraph;
    private UWGraph uwGraph;
    private DWGraph dwGraph;

    public void initialize(URL url, ResourceBundle resourceBundle){
        uuAP.setOnMouseClicked(this::uuAddVertex);
        uwAP.setOnMouseClicked(this::uwAddVertex);
        duAP.setOnMouseClicked(this::duAddVertex);
        dwAP.setOnMouseClicked(this::dwAddVertex);
        uuGraph = new UUGraph(uuAP);
        duGraph = new DUGraph(duAP);
        uwGraph = new UWGraph(uwAP);
        dwGraph = new DWGraph(dwAP);
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

    public void uuAddVertex(MouseEvent e){ uuGraph.addVertex(e.getX(),e.getY()); }
    public void uuCreateEdge(){
        try{ uuGraph.addEdge(Integer.parseInt(uuTF1.getText()),Integer.parseInt(uuTF2.getText())); }
        catch(NumberFormatException e){
            Alerts.warningAlert("Input is in invalid format!");
        }
    }
    public void uuClearGraph(){ uuGraph.clearGraph(); }
    public void uuUpdateTables(){
        int[][] AM = uuGraph.getAM();
        ArrayList<ArrayList<Integer>> AL = uuGraph.getAL();
        ArrayList<int[]> EL = uuGraph.getEL();
    }

    public void uwAddVertex(MouseEvent e){ uwGraph.addVertex(e.getX(),e.getY()); }
    public void uwCreateEdge(){
        try{ uwGraph.addEdge(Integer.parseInt(uwTF1.getText()),Integer.parseInt(uwTF2.getText()),Integer.parseInt(uwTFWeight.getText())); }
        catch(NumberFormatException e){
            Alerts.warningAlert("Input is in invalid format!");
        }
    }
    public void uwClearGraph(){ uwGraph.clearGraph(); }
    public void uwUpdateTables(){
        int[][] AM = uwGraph.getAM();
        ArrayList<ArrayList<Integer>> AL = uwGraph.getAL();
        ArrayList<int[]> EL = uwGraph.getEL();
    }

    public void duAddVertex(MouseEvent e){ duGraph.addVertex(e.getX(),e.getY()); }
    public void duCreateEdge(){
        try{ duGraph.addEdge(Integer.parseInt(duTF1.getText()),Integer.parseInt(duTF2.getText())); }
        catch(NumberFormatException e){
            Alerts.warningAlert("Input is in invalid format!");
        }
    }
    public void duClearGraph(){ duGraph.clearGraph(); }
    public void duUpdateTables(){
        int[][] AM = duGraph.getAM();
        ArrayList<ArrayList<Integer>> AL = duGraph.getAL();
        ArrayList<int[]> EL = duGraph.getEL();
    }

    public void dwAddVertex(MouseEvent e){ dwGraph.addVertex(e.getX(),e.getY()) ;}
    public void dwCreateEdge(){
        try{ dwGraph.addEdge(Integer.parseInt(dwTF1.getText()),Integer.parseInt(dwTF2.getText()),Integer.parseInt(dwTFWeight.getText())); }
        catch(NumberFormatException e){
            Alerts.warningAlert("Input is in invalid format!");
        }
    }
    public void dwClearGraph(){ dwGraph.clearGraph(); }
    public void dwUpdateTables(){
        int[][] AM = dwGraph.getAM();
        ArrayList<ArrayList<Integer>> AL = dwGraph.getAL();
        ArrayList<int[]> EL = dwGraph.getEL();
    }
}
