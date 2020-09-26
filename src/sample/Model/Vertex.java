package sample.Model;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class Vertex extends Circle{
    private int index;
    public static int numberOfVertices = 0;
    private ArrayList<Vertex> adjacencyList;
    public Vertex(double x, double y){
        super(x,y,20,Paint.valueOf("#fac528"));
        index = numberOfVertices++;
        adjacencyList = new ArrayList<>();
        setStrokeWidth(0);
        setStroke(Color.BLACK);
        setOnMousePressed(e -> {
            setStrokeWidth(2);
        });
        setOnMouseDragged(e -> {
            moveTo(e.getX(),e.getY());
        });
        setOnMouseReleased(e -> {
            setStrokeWidth(0);
        });
    }

    public void moveTo(double x, double y){
        setCenterX(x);
        setCenterY(y);
    }

    public boolean equalsTo(Vertex v){
        return this.index == v.index;
    }

    public boolean addNeighbour(Vertex v){
        if(adjacencyList.contains(v))
            return false;
        else
            adjacencyList.add(v);
            return true;
    }

    public ArrayList<Vertex> getAdjacencyList(){ return adjacencyList; }

    public int getIndex(){
        return index;
    }
}
