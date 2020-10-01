package sample.Model.Graphs;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import sample.Model.Alerts;

public class DUGraph extends UnweightedGraph{

    public DUGraph(Pane pane) {
        super(pane);
    }

    public void addEdge(int x, int y) {
        if(x >= numVertices || y >= numVertices || x < 0 || y < 0){
            Alerts.warningAlert("Vertices do not exist!","Choose a valid pair of vertices!");
        }
        else if(x == y){
            Alerts.warningAlert("Vertices chosen are the same!", "Choose another pair of vertices!");
            return;
        }
        else if(AM[x][y] == 1){
            Alerts.warningAlert("Edge already exists!", "Choose another pair of vertices!");
            return;
        }
        Circle c1 = vertices.get(x), c2 = vertices.get(y);
        double x1 = c1.getCenterX(), y1 = c1.getCenterY();
        double x2 = c2.getCenterX(), y2 = c2.getCenterY();
        boolean swap = false;
        if(x1 > x2){
            double temp = x2; x2 = x1; x1 = temp;
            temp = y2; y2 = y1; y1 = temp;
            swap = true;
        }
        double dx = x2 - x1, dy = y2 - y1;
        double hyp = Math.sqrt(Math.pow(y2-y1,2)+Math.pow(x2-x1,2));
        double ratio = RADIUS / hyp, rat = 7 / hyp, angle = 30;
        Line edge = new Line(x1+ratio*dx,y1+ratio*dy,x2-ratio*dx,y2-ratio*dy);
        Line arr1 = null, arr2 = null;
        if(swap){
            arr1 = new Line(x1+ratio*dx,y1+ratio*dy,x1+(ratio+rat)*dx,y1+(ratio+rat)*dy);
            arr2 = new Line(x1+ratio*dx,y1+ratio*dy,x1+(ratio+rat)*dx,y1+(ratio+rat)*dy);
            arr1.getTransforms().add(new Rotate(angle,x1+ratio*dx,y1+ratio*dy));
            arr2.getTransforms().add(new Rotate(-angle,x1+ratio*dx,y1+ratio*dy));
        }
        else{
            arr1 = new Line(x2-ratio*dx,y2-ratio*dy,x2-(ratio+rat)*dx,y2-(ratio+rat)*dy);
            arr2 = new Line(x2-ratio*dx,y2-ratio*dy,x2-(ratio+rat)*dx,y2-(ratio+rat)*dy);
            arr1.getTransforms().add(new Rotate(angle,x2-ratio*dx,y2-ratio*dy));
            arr2.getTransforms().add(new Rotate(-angle,x2-ratio*dx,y2-ratio*dy));
        }
        pane.getChildren().addAll(edge,arr1,arr2);
        EL.add(new int[]{x,y});
        AM[x][y] = 1;
        AL.get(x).add(y);
    }
}
