package sample.Model.Graphs.Animation;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import sample.Model.Graphs.GraphStructures.Graph;

import java.util.ArrayList;

public abstract class GraphTraversal extends GraphAnimation {

    protected Button edgeBtn, clearBtn, bfsBtn, dfsBtn, uploadBtn;

    public GraphTraversal(Graph graph, Slider slider,
               Button edgeBtn, Button clearBtn, Button dfsBtn, Button bfsBtn, Button uploadBtn) {
        super(graph,slider);
        this.edgeBtn = edgeBtn;
        this.clearBtn = clearBtn;
        this.bfsBtn = bfsBtn;
        this.dfsBtn = dfsBtn;
        this.uploadBtn = uploadBtn;
    }

    public void disableButtons(boolean b){
        edgeBtn.setDisable(b);
        clearBtn.setDisable(b);
        dfsBtn.setDisable(b);
        bfsBtn.setDisable(b);
        uploadBtn.setDisable(b);
    }
}
