package sample.Model.Graphs.GraphStructures;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import sample.Model.General.Alerts;

public class UWGraph extends WeightedGraph{
    public UWGraph(Pane pane) {
        super(pane);
    }

    public void addEdge(int x, int y, int weight){
        if(x >= numVertices || y >= numVertices || x < 0 || y < 0){
            Alerts.warningAlert("Vertices do not exist!","Choose a valid pair of vertices!");
            return;
        }
        else if(x == y){
            Alerts.warningAlert("Vertices chosen are the same!", "Choose another pair of vertices!");
            return;
        }
        if(AM[x][y] == 1){
            Alerts.warningAlert("Edge already exists!", "Choose another pair of vertices!");
            return;
        }
        Circle c1 = vertices.get(x), c2 = vertices.get(y);
        double x1 = c1.getCenterX(), y1 = c1.getCenterY();
        double x2 = c2.getCenterX(), y2 = c2.getCenterY();
        Line edge = new Line(x1,y1,x2,y2);
        edge.setStrokeWidth(2);
        Label label = new Label(""+weight);
        label.setLayoutX((x1+x2)/2);
        label.setLayoutY((y1+y2)/2);
        pane.getChildren().addAll(edge,label);
        EL.add(new int[]{x,y,weight});
        AM[x][y] = AM[y][x] = 1;
        AL.get(x).add(y);
        AL.get(y).add(x);
        edge.toBack();
        edges.add(edge);
    }
}
