package sample.Model.Graphs.GraphStructures;

import javafx.scene.layout.Pane;

public abstract class UnweightedGraph extends Graph{
    public UnweightedGraph(Pane pane){ super(pane); }
    public abstract void addEdge(int x, int y);
}
