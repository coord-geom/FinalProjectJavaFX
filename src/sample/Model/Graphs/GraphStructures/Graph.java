package sample.Model.Graphs.GraphStructures;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import sample.Model.General.Alerts;
import javafx.scene.text.Font;

import java.awt.*;
import java.util.ArrayList;

public abstract class Graph {
    protected int[][] AM;
    protected ArrayList<ArrayList<Integer>> AL;
    protected ArrayList<ArrayList<int[]>> ALW;
    protected ArrayList<int[]> EL;
    protected int numVertices = 0;
    protected ArrayList<Circle> vertices;
    protected ArrayList<Line> edges;
    protected Pane pane;
    protected final int RADIUS = 15;

    public Graph(Pane pane){
        AM = new int[10][10];
        AL = new ArrayList<>();
        EL = new ArrayList<>();
        ALW = new ArrayList<>();
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
        this.pane = pane;
    }

    public Pane getPane(){ return pane; }

    public void addVertex(double posX, double posY){
        if(numVertices == 10){
            Alerts.warningAlert("Maximum number of vertices reached!","Please clear the graph to add more vertices!");
            return;
        }
        Circle vertex = new Circle(posX,posY,RADIUS, Color.WHITE);
        vertex.setStroke(Color.ORANGE);
        Label label = new Label(""+numVertices++);
        label.setStyle("-fx-font-weight: bold;");
        label.setLayoutX(posX-3.7);
        label.setLayoutY(posY-10);
        pane.getChildren().addAll(vertex,label);
        AL.add(new ArrayList<>());
        ALW.add(new ArrayList<>());
        vertices.add(vertex);
    }

    public boolean inGraph(int x){ return x >= 0 && x < numVertices; }

    public void clearGraph(){
        AM = new int[10][10];
        AL.clear();
        EL.clear();
        numVertices = 0;
        pane.getChildren().clear();
        vertices.clear();
        edges.clear();
    }

    public ArrayList<ArrayList<int[]>> getALW(){ return ALW; }
    public int getNumVertices(){ return numVertices; }
    public ArrayList<Circle> getVertices(){ return vertices; }
    public int[][] getAM(){ return AM; }
    public ArrayList<ArrayList<Integer>> getAL(){ return AL; }
    public ArrayList<int[]> getEL(){ return EL; }
    public ArrayList<Line> getEdges(){ return edges; }
}
