package sample.Model.Graphs.Animation;

import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import sample.Model.Graphs.GraphStructures.Graph;

public abstract class GraphSPA extends GraphAnimation {

    private Button edgeBtn, clearBtn, dijkBtn, bfBtn, uploadBtn;

    public GraphSPA(Graph graph, Slider slider,
                          Button edgeBtn, Button clearBtn, Button dijkBtn, Button bfBtn, Button uploadBtn) {
        super(graph,slider);
        this.edgeBtn = edgeBtn;
        this.clearBtn = clearBtn;
        this.bfBtn = bfBtn;
        this.dijkBtn = dijkBtn;
        this.uploadBtn = uploadBtn;
    }

    public void disableButtons(boolean b){
        edgeBtn.setDisable(b);
        clearBtn.setDisable(b);
        dijkBtn.setDisable(b);
        bfBtn.setDisable(b);
        uploadBtn.setDisable(b);
    }
}
