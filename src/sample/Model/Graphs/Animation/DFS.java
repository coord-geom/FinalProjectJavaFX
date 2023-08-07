package sample.Model.Graphs.Animation;

import javafx.animation.FillTransition;
import javafx.animation.StrokeTransition;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import sample.Model.General.Alerts;
import sample.Model.Graphs.GraphStructures.Graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class DFS extends GraphTraversal{

    private Button edgeBtn, clearBtn, bfsBtn, dfsBtn;
    private ArrayList<Circle> vertices;
    private ArrayList<ArrayList<Integer>> AL;
    private ArrayList<Line> edges;
    private ArrayList<int[]> EL;
    private Pane pane;
    private int[] visited;

    public DFS(Graph graph, Slider slider,
               Button edgeBtn, Button clearBtn, Button bfsBtn, Button dfsBtn, Button uploadBtn) {
        super(graph,slider,edgeBtn,clearBtn,bfsBtn,dfsBtn,uploadBtn);
    }

    public void animate(int src){
        if(src >= graph.getNumVertices() || src < 0){
            Alerts.warningAlert("Source node chosen is not on graph!","Please choose another graph!");
            return;
        }
        new Thread(() -> {
            vertices = graph.getVertices();
            AL = graph.getAL();
            edges = graph.getEdges();
            EL = graph.getEL();
            pane = graph.getPane();
            visited = new int[graph.getNumVertices()];
            for(Line l: edges){
                Platform.runLater(()->l.setStroke(Color.BLACK));
            }
            for(Label l: labels)
                Platform.runLater(()->pane.getChildren().remove(l));
            labels.clear();
            Platform.runLater(()->{
                pane.setDisable(true);
                disableButtons(true);
            });
            dfs(src);
            Platform.runLater(()->{
                pane.setDisable(false);
                disableButtons(false);
            });
        }).start();
    }

    public void dfs(int u){
        Circle c = vertices.get(u);
        Platform.runLater(() -> {
            new FillTransition(Duration.millis(speed),c,Color.WHITE,Color.ORANGE).play();
            c.setFill(Color.ORANGE);
        });
        try { Thread.sleep((long) speed); }
        catch (InterruptedException e) { e.printStackTrace(); }
        if(visited[u] == 1){
            Platform.runLater(() -> {
                new FillTransition(Duration.millis(speed),c,Color.ORANGE,Color.WHITE).play();
                c.setFill(Color.WHITE);
            });
            try { Thread.sleep((long) speed); }
            catch (InterruptedException e) { e.printStackTrace(); }
            return;
        }
        visited[u] = 1;
        for(int v: AL.get(u)){
            Line line = null;
            for(int i=0;i<EL.size();++i){
                int[] arr = EL.get(i);
                if(arr[0] == u && arr[1] == v){
                    line = edges.get(i);
                    break;
                }
            }
            Line finalLine = line;
            Platform.runLater(()-> {
                new StrokeTransition(Duration.millis(speed),finalLine,Color.BLACK,Color.ORANGE).play();
                finalLine.setStroke(Color.ORANGE);
            });
            try { Thread.sleep((long) speed); }
            catch (InterruptedException e) { e.printStackTrace(); }
            if(visited[v] == 1){
                Platform.runLater(()-> {
                    new StrokeTransition(Duration.millis(speed),finalLine,Color.ORANGE,Color.GREY).play();
                    finalLine.setStroke(Color.GREY);
                });
                try { Thread.sleep((long) speed); }
                catch (InterruptedException e) { e.printStackTrace(); }
            }
            else{
                Platform.runLater(()-> {
                    new StrokeTransition(Duration.millis(speed),finalLine,Color.ORANGE,Color.FIREBRICK).play();
                    finalLine.setStroke(Color.FIREBRICK);
                });
                try { Thread.sleep((long) speed); }
                catch (InterruptedException e) { e.printStackTrace(); }
                dfs(v);
            }
        }
        Platform.runLater(() -> {
            new FillTransition(Duration.millis(speed),c,Color.ORANGE,Color.WHITE).play();
            c.setFill(Color.WHITE);
        });
        try { Thread.sleep((long) speed); }
        catch (InterruptedException e) { e.printStackTrace(); }
    }
}
