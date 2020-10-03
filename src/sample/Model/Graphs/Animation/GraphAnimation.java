package sample.Model.Graphs.Animation;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import sample.Model.Graphs.GraphStructures.Graph;

import java.util.ArrayList;

public abstract class GraphAnimation {
    protected Graph graph;
    protected double speed = 800;
    protected Slider slider;
    protected ArrayList<Label> labels;

    public GraphAnimation(Graph graph, Slider slider){
        this.graph = graph;
        this.slider = slider;
        this.slider.valueProperty().addListener(((observableValue, number, t1) -> speed = (slider.getMax()+slider.getMin() - t1.doubleValue()) * 800 + 200));
        this.labels = new ArrayList<>();
    }

    public abstract void animate(int src);
}
