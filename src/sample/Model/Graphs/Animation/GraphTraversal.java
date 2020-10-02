package sample.Model.Graphs.Animation;

import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import sample.Model.Graphs.GraphStructures.Graph;

public abstract class GraphTraversal extends GraphAnimation {

    private Button edgeBtn, clearBtn, bfsBtn, dfsBtn;

    public GraphTraversal(Graph graph, Slider slider,
               Button edgeBtn, Button clearBtn, Button dfsBtn, Button bfsBtn) {
        super(graph,slider);
        this.edgeBtn = edgeBtn;
        this.clearBtn = clearBtn;
        this.bfsBtn = bfsBtn;
        this.dfsBtn = dfsBtn;
    }

    public void disableButtons(boolean b){
        edgeBtn.setDisable(b);
        clearBtn.setDisable(b);
        dfsBtn.setDisable(b);
        bfsBtn.setDisable(b);
    }
}
