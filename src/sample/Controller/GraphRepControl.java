package sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Model.General.Alerts;
import sample.Model.Graphs.GraphStructures.DUGraph;
import sample.Model.Graphs.GraphStructures.DWGraph;
import sample.Model.Graphs.GraphStructures.UUGraph;
import sample.Model.Graphs.GraphStructures.UWGraph;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class GraphRepControl implements Initializable {

    @FXML
    private AnchorPane anchorPane, uuAP, uwAP, duAP, dwAP;
    @FXML
    private TextField uuTF1, uuTF2, uwTF1, uwTF2, uwTFWeight, duTF1, duTF2, dwTF1, dwTF2, dwTFWeight;
    @FXML
    private TextArea edgeList, adjacencyList, adjacencyMatrix;

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
        edgeList.setText("");
        adjacencyList.setText("");
        adjacencyMatrix.setText("    ");
        for(int[] arr: EL)
            edgeList.setText(edgeList.getText()+"("+arr[0]+", "+arr[1]+")\n");
        for(int i=0;i<uuGraph.getNumVertices();++i){
            adjacencyList.setText(adjacencyList.getText()+i+": ");
            for(int j=0;j<AL.get(i).size();++j){
                if(j < AL.get(i).size()-1)
                    adjacencyList.setText(adjacencyList.getText()+AL.get(i).get(j)+", ");
                else
                    adjacencyList.setText(adjacencyList.getText()+AL.get(i).get(j));
            }
            adjacencyList.setText(adjacencyList.getText()+"\n");
        }
        for(int i=0;i<uuGraph.getNumVertices();++i)
            adjacencyMatrix.setText(adjacencyMatrix.getText()+i+"   ");
        adjacencyMatrix.setText(adjacencyMatrix.getText()+"\n");
        for(int i=0;i<uuGraph.getNumVertices();++i){
            adjacencyMatrix.setText(adjacencyMatrix.getText()+i+"   ");
            for(int j=0;j<uuGraph.getNumVertices();++j)
                adjacencyMatrix.setText(adjacencyMatrix.getText()+AM[i][j]+"   ");
            adjacencyMatrix.setText(adjacencyMatrix.getText()+"\n");
        }

    }

    public void uwAddVertex(MouseEvent e){ uwGraph.addVertex(e.getX(),e.getY()); }
    public void uwCreateEdge(){
        try{
            int w = Integer.parseInt(uwTFWeight.getText());
            if(w > 999 || w < -99)
                Alerts.warningAlert("Weight not within range","For ease of implementation, weights can only be between -99 and 999");
            else
                uwGraph.addEdge(Integer.parseInt(uwTF1.getText()),Integer.parseInt(uwTF2.getText()),w);
        }
        catch(NumberFormatException e){
            Alerts.warningAlert("Input is in invalid format!");
        }
    }
    public void uwClearGraph(){ uwGraph.clearGraph(); }
    public void uwUpdateTables(){
        int[][] AM = uwGraph.getAM();
        ArrayList<ArrayList<int[]>> AL = uwGraph.getALW();
        ArrayList<int[]> EL = uwGraph.getEL();
        edgeList.setText("");
        adjacencyList.setText("");
        adjacencyMatrix.setText("    ");
        for(int[] arr: EL)
            edgeList.setText(edgeList.getText()+"("+arr[0]+", "+arr[1]+", "+arr[2]+")\n");
        for(int i=0;i<uwGraph.getNumVertices();++i){
            adjacencyList.setText(adjacencyList.getText()+i+": ");
            for(int j=0;j<AL.get(i).size();++j){
                if(j < AL.get(i).size()-1)
                    adjacencyList.setText(adjacencyList.getText()+ Arrays.toString(AL.get(i).get(j)) +", ");
                else
                    adjacencyList.setText(adjacencyList.getText()+ Arrays.toString(AL.get(i).get(j)));
            }
            adjacencyList.setText(adjacencyList.getText()+"\n");
        }
        for(int i=0;i<uwGraph.getNumVertices();++i)
            adjacencyMatrix.setText(adjacencyMatrix.getText()+i+"   ");
        adjacencyMatrix.setText(adjacencyMatrix.getText()+"\n");
        for(int i=0;i<uwGraph.getNumVertices();++i){
            adjacencyMatrix.setText(adjacencyMatrix.getText()+i+"   ");
            for(int j=0;j<uwGraph.getNumVertices();++j){
                String str = "" + AM[i][j];
                str += " ".repeat(4-str.length());
                adjacencyMatrix.setText(adjacencyMatrix.getText()+str);
            }
            adjacencyMatrix.setText(adjacencyMatrix.getText()+"\n");
        }
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
        edgeList.setText("");
        adjacencyList.setText("");
        adjacencyMatrix.setText("    ");
        for(int[] arr: EL)
            edgeList.setText(edgeList.getText()+"("+arr[0]+", "+arr[1]+")\n");
        for(int i=0;i<duGraph.getNumVertices();++i){
            adjacencyList.setText(adjacencyList.getText()+i+": ");
            for(int j=0;j<AL.get(i).size();++j){
                if(j < AL.get(i).size()-1)
                    adjacencyList.setText(adjacencyList.getText()+AL.get(i).get(j)+", ");
                else
                    adjacencyList.setText(adjacencyList.getText()+AL.get(i).get(j));
            }
            adjacencyList.setText(adjacencyList.getText()+"\n");
        }
        for(int i=0;i<duGraph.getNumVertices();++i)
            adjacencyMatrix.setText(adjacencyMatrix.getText()+i+"   ");
        adjacencyMatrix.setText(adjacencyMatrix.getText()+"\n");
        for(int i=0;i<duGraph.getNumVertices();++i){
            adjacencyMatrix.setText(adjacencyMatrix.getText()+i+"   ");
            for(int j=0;j<duGraph.getNumVertices();++j)
                adjacencyMatrix.setText(adjacencyMatrix.getText()+AM[i][j]+"   ");
            adjacencyMatrix.setText(adjacencyMatrix.getText()+"\n");
        }
    }

    public void dwAddVertex(MouseEvent e){ dwGraph.addVertex(e.getX(),e.getY()) ;}
    public void dwCreateEdge(){
        int w = Integer.parseInt(dwTFWeight.getText());
        if(w > 999 || w < -99)
            Alerts.warningAlert("Weight not within range","For ease of implementation, weights can only be between -99 and 999");
        else
            dwGraph.addEdge(Integer.parseInt(dwTF1.getText()),Integer.parseInt(dwTF2.getText()),w);
    }
    public void dwClearGraph(){ dwGraph.clearGraph(); }
    public void dwUpdateTables(){
        int[][] AM = dwGraph.getAM();
        ArrayList<ArrayList<int[]>> AL = dwGraph.getALW();
        ArrayList<int[]> EL = dwGraph.getEL();
        edgeList.setText("");
        adjacencyList.setText("");
        adjacencyMatrix.setText("    ");
        for(int[] arr: EL)
            edgeList.setText(edgeList.getText()+"("+arr[0]+", "+arr[1]+", "+arr[2]+")\n");
        for(int i=0;i<dwGraph.getNumVertices();++i){
            adjacencyList.setText(adjacencyList.getText()+i+": ");
            for(int j=0;j<AL.get(i).size();++j){
                if(j < AL.get(i).size()-1)
                    adjacencyList.setText(adjacencyList.getText()+ Arrays.toString(AL.get(i).get(j)) +", ");
                else
                    adjacencyList.setText(adjacencyList.getText()+ Arrays.toString(AL.get(i).get(j)));
            }
            adjacencyList.setText(adjacencyList.getText()+"\n");
        }
        for(int i=0;i<dwGraph.getNumVertices();++i)
            adjacencyMatrix.setText(adjacencyMatrix.getText()+i+"   ");
        adjacencyMatrix.setText(adjacencyMatrix.getText()+"\n");
        for(int i=0;i<dwGraph.getNumVertices();++i){
            adjacencyMatrix.setText(adjacencyMatrix.getText()+i+"   ");
            for(int j=0;j<dwGraph.getNumVertices();++j){
                String str = "" + AM[i][j];
                str += " ".repeat(4-str.length());
                adjacencyMatrix.setText(adjacencyMatrix.getText()+str);
            }
            adjacencyMatrix.setText(adjacencyMatrix.getText()+"\n");
        }
    }
}
