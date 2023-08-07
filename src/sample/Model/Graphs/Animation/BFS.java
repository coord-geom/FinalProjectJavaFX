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

public class BFS extends GraphTraversal{

    public BFS(Graph graph, Slider slider,
               Button edgeBtn, Button clearBtn, Button dfsBtn, Button bfsBtn, Button uploadBtn) {
        super(graph,slider,edgeBtn,clearBtn,dfsBtn,bfsBtn,uploadBtn);
    }

    public void animate(int src){
        if(src >= graph.getNumVertices() || src < 0){
            Alerts.warningAlert("Source node chosen is not on graph!","Please choose another graph!");
            return;
        }
        new Thread(() -> {
            ArrayList<Circle> vertices = graph.getVertices();
            ArrayList<ArrayList<Integer>> AL = graph.getAL();
            ArrayList<Line> edges = graph.getEdges();
            ArrayList<int[]> EL = graph.getEL();
            Pane pane = graph.getPane();
            for(Line l: edges)
                Platform.runLater(()->l.setStroke(Color.BLACK));
            for(Label l: labels)
                Platform.runLater(()->pane.getChildren().remove(l));
            Platform.runLater(()->{
                pane.setDisable(true);
                disableButtons(true);
            });
            labels.clear();
            for(int i=0;i< graph.getNumVertices();++i){
                Label label = new Label("INF");
                label.setLayoutX(vertices.get(i).getCenterX()-3.7);
                label.setLayoutY(vertices.get(i).getCenterY()+15);
                label.setStyle("-fx-font-weight: bold; -fx-text-fill: red;");
                labels.add(label);
            }
            labels.get(src).setText("0");
            Platform.runLater(()->pane.getChildren().addAll(labels));
            int[] visited = new int[graph.getNumVertices()];
            for(int i=0;i<graph.getNumVertices();++i) visited[i] = -1;
            Queue<Integer> q = new LinkedList<>();
            q.add(src);
            visited[src] = 0;
            while(!q.isEmpty()){
                int u = q.poll();
                Circle c = vertices.get(u);
                Platform.runLater(() -> {
                    new FillTransition(Duration.millis(speed),c,Color.WHITE,Color.ORANGE).play();
                    c.setFill(Color.ORANGE);
                });
                try { Thread.sleep((long) speed); }
                catch (InterruptedException e) { e.printStackTrace(); }
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
                    if(visited[v] != -1){
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
                        q.add(v);
                        visited[v] = visited[u] + 1;
                        Platform.runLater(()->labels.get(v).setText(""+visited[v]));
                    }
                }
                Platform.runLater(() -> {
                    new FillTransition(Duration.millis(speed),c,Color.ORANGE,Color.WHITE).play();
                    c.setFill(Color.WHITE);
                });
                try { Thread.sleep((long) speed); }
                catch (InterruptedException e) { e.printStackTrace(); }
            }
            Platform.runLater(()->{
                pane.setDisable(false);
                disableButtons(false);
            });
        }).start();
    }


}
