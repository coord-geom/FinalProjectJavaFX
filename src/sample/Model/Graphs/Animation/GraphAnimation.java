package sample.Model.Graphs.Animation;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import sample.Model.Animation;
import sample.Model.General.Alerts;
import sample.Model.Graphs.GraphStructures.Graph;

import java.util.ArrayList;

public abstract class GraphAnimation implements Animation {
    protected Graph graph;
    protected double speed = 800;
    protected Slider slider;
    protected ArrayList<Label> labels;
    protected TextField srcTF;
    protected Button edgeBtn, clearBtn, act1Btn, act2Btn, uploadBtn;

    public GraphAnimation(Graph graph, Slider slider, TextField srcTF,
                          Button edgeBtn, Button clearBtn, Button act1Btn, Button act2Btn, Button uploadBtn){
        this.graph = graph;
        this.slider = slider;
        this.slider.valueProperty().addListener(((observableValue, number, t1) -> speed = (slider.getMax()+slider.getMin() - t1.doubleValue()) * 800 + 200));
        this.labels = new ArrayList<>();
        this.srcTF = srcTF;
        this.edgeBtn = edgeBtn;
        this.clearBtn = clearBtn;
        this.act1Btn = act1Btn;
        this.act2Btn = act2Btn;
        this.uploadBtn = uploadBtn;
    }

    public GraphAnimation(Graph graph, Slider slider){
        this.graph = graph;
        this.slider = slider;
    }

    public void setDisables(boolean b){
        edgeBtn.setDisable(b);
        clearBtn.setDisable(b);
        act1Btn.setDisable(b);
        act2Btn.setDisable(b);
        uploadBtn.setDisable(b);
        graph.getPane().setDisable(b);
    }

    public void start(){
        if(srcTF.getText().equals("")){
            Alerts.warningAlert("No source node input!","Please key in a source node!");
            return;
        }
        try{
            int x = Integer.parseInt(srcTF.getText());
            animate(x);
        }catch(NumberFormatException e){
            Alerts.warningAlert("Input is in the wrong format!","Please enter a valid source vertex!");
        }
    }

    public abstract void animate(int src);
}
