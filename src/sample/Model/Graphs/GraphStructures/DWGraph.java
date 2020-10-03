package sample.Model.Graphs.GraphStructures;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import sample.Model.General.Alerts;

public class DWGraph extends WeightedGraph{
    public DWGraph(Pane pane) {
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
        Line edge, arr1, arr2;
        if(swap){
            edge = new Line(x2-ratio*dx,y2-ratio*dy,x1+ratio*dx,y1+ratio*dy);
            arr1 = new Line(x1+ratio*dx,y1+ratio*dy,x1+(ratio+rat)*dx,y1+(ratio+rat)*dy);
            arr2 = new Line(x1+ratio*dx,y1+ratio*dy,x1+(ratio+rat)*dx,y1+(ratio+rat)*dy);
            arr1.getTransforms().add(new Rotate(angle,x1+ratio*dx,y1+ratio*dy));
            arr2.getTransforms().add(new Rotate(-angle,x1+ratio*dx,y1+ratio*dy));
        }
        else{
            edge = new Line(x1+ratio*dx,y1+ratio*dy,x2-ratio*dx,y2-ratio*dy);
            arr1 = new Line(x2-ratio*dx,y2-ratio*dy,x2-(ratio+rat)*dx,y2-(ratio+rat)*dy);
            arr2 = new Line(x2-ratio*dx,y2-ratio*dy,x2-(ratio+rat)*dx,y2-(ratio+rat)*dy);
            arr1.getTransforms().add(new Rotate(angle,x2-ratio*dx,y2-ratio*dy));
            arr2.getTransforms().add(new Rotate(-angle,x2-ratio*dx,y2-ratio*dy));
        }
        edge.setStrokeWidth(2);
        arr1.setStrokeWidth(2);
        arr2.setStrokeWidth(2);
        Label label = new Label(""+weight);
        label.setLayoutX((x1+x2)/2);
        label.setLayoutY((y1+y2)/2);
        label.setStyle("-fx-font-weight: bold; -fx-text-fill: black;");
        pane.getChildren().addAll(edge,arr1,arr2,label);
        EL.add(new int[]{x,y,weight});
        AM[x][y] = 1;
        ALW.get(x).add(new int[]{y,weight});
        edges.add(edge);
        edge.toBack();
        arr1.toBack();
        arr2.toBack();
    }
}
