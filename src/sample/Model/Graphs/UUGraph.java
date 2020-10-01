package sample.Model.Graphs;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import sample.Model.Alerts;

public class UUGraph extends UnweightedGraph{

    public UUGraph(Pane pane) {
        super(pane);
    }

    public void addEdge(int x, int y){
        if(x >= numVertices || y >= numVertices || x < 0 || y < 0){
            Alerts.warningAlert("Vertices do not exist!","Choose a valid pair of vertices!");
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
        Line edge = new Line(c1.getCenterX(),c1.getCenterY(),c2.getCenterX(),c2.getCenterY());
        pane.getChildren().add(edge);
        EL.add(new int[]{x,y});
        AM[x][y] = AM[y][x] = 1;
        AL.get(x).add(y);
        AL.get(y).add(x);
        edge.toBack();
    }
}
