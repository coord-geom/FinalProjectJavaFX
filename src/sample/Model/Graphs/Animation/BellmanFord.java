package sample.Model.Graphs.Animation;

import javafx.animation.FillTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.StrokeTransition;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import sample.Model.General.Alerts;
import sample.Model.Graphs.GraphStructures.Graph;

import java.util.ArrayList;

public class BellmanFord extends GraphAnimation{

    public BellmanFord(Graph graph, Slider slider, TextField srcTF, Button edgeBtn,
                       Button clearBtn, Button dijkBtn, Button bfBtn, Button uploadBtn) {
        super(graph, slider, srcTF, edgeBtn, clearBtn, dijkBtn, bfBtn, uploadBtn);
    }

    public void animate(int src) {
        if(src >= graph.getNumVertices() || src < 0){
            Alerts.warningAlert("Source node chosen is not on graph!","Please choose another graph!");
            return;
        }
        new Thread(() -> {
            ArrayList<Circle> vertices = graph.getVertices();
            ArrayList<Line> edges = graph.getEdges();
            ArrayList<int[]> EL = graph.getEL();
            Pane pane = graph.getPane();
            for(Line l: edges)
                Platform.runLater(()->l.setStroke(Color.BLACK));
            ArrayList<Label> lbl = new ArrayList<>();
            for(Node node: pane.getChildren()){
                if(node instanceof Label)
                    if(node.getStyle().equals("-fx-font-weight: bold; -fx-text-fill: red;"))
                        lbl.add((Label) node);
            }
            Platform.runLater(()->pane.getChildren().removeAll(lbl));
            Platform.runLater(()->setDisables(true));
            labels.clear();
            for(int i=0;i<graph.getNumVertices();++i){
                Label label = new Label("INF");
                label.setLayoutX(vertices.get(i).getCenterX()-3.7);
                label.setLayoutY(vertices.get(i).getCenterY()+15);
                label.setStyle("-fx-font-weight: bold; -fx-text-fill: red;");
                labels.add(label);
            }
            labels.get(src).setText("0");
            Platform.runLater(()->pane.getChildren().addAll(labels));
            int[] dist = new int[graph.getNumVertices()];
            for(int i=0;i<graph.getNumVertices();++i){
                if(i == src) continue;
                dist[i] = Integer.MAX_VALUE;
            }

            for(int i=0;i<graph.getNumVertices()-1;++i){
                for(int[] arr: EL){
                    int u = arr[0], v = arr[1], w = arr[2];

                    Circle c = vertices.get(u);
                    Line line = null;
                    for(int k=0;i<EL.size();++k){
                        int[] a = EL.get(k);
                        if(a[0] == u && a[1] == v){
                            line = edges.get(k);
                            break;
                        }
                    }
                    Line finalLine = line;
                    Circle d = vertices.get(u);

                    Platform.runLater(() -> {
                        new ParallelTransition(
                                new FillTransition(Duration.millis(speed),c,Color.WHITE,Color.ORANGE),
                                new FillTransition(Duration.millis(speed),d,Color.WHITE,Color.ORANGE),
                                new StrokeTransition(Duration.millis(speed),finalLine,Color.BLACK,Color.ORANGE)
                        ).play();
                        c.setFill(Color.ORANGE);
                        d.setFill(Color.ORANGE);
                        finalLine.setStroke(Color.ORANGE);
                    });
                    try { Thread.sleep((long) speed); }
                    catch (InterruptedException e) { Alerts.errorAlert("interruptedException caught","i don't know how it got here"); }


                    if(dist[v] - dist[u] > w){
                        dist[v] = dist[u] + w;
                        Platform.runLater(()->labels.get(v).setText(""+dist[v]));
                    }

                    Platform.runLater(() -> {
                        new ParallelTransition(
                                new FillTransition(Duration.millis(speed),c,Color.ORANGE,Color.WHITE),
                                new FillTransition(Duration.millis(speed),d,Color.ORANGE,Color.WHITE),
                                new StrokeTransition(Duration.millis(speed),finalLine,Color.ORANGE,Color.BLACK)
                        ).play();
                        c.setFill(Color.WHITE);
                        d.setFill(Color.WHITE);
                        finalLine.setStroke(Color.BLACK);
                    });
                    try { Thread.sleep((long) speed); }
                    catch (InterruptedException e) { Alerts.errorAlert("interruptedException caught","i don't know how it got here"); }

                }
            }

            Platform.runLater(()->setDisables(false));
        }).start();
    }
}
