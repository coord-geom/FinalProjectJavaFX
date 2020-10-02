package sample.Model.Graphs.GraphStructures;

import javafx.scene.layout.Pane;

public abstract class WeightedGraph extends Graph{
    public WeightedGraph(Pane pane){ super(pane); }
    public abstract void addEdge(int x, int y, int w);
}
