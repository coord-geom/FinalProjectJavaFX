package sample.Model;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;

public class Edge extends Line {
    private Vertex u, v;
    private boolean directed;
    private int weight;
    public Edge(Vertex u, Vertex v, boolean directed, int weight){
        super(u.getCenterX(),u.getCenterY(),
                v.getCenterX(),v.getCenterY());
        startXProperty().bind(u.centerXProperty());
        startYProperty().bind(u.centerYProperty());
        endXProperty().bind(v.centerXProperty());
        endYProperty().bind(v.centerYProperty());
        setStroke(Color.BLACK);
        setStrokeWidth(2);
        this.u = u;
        this.v = v;
        this.weight = weight;
        this.directed = directed;
    }

    public Edge(Vertex u, Vertex v, boolean directed){
        this(u,v,directed,1);
    }
}
