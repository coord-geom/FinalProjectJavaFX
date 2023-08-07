package sample.Model.Graphs.Animation;

import javafx.animation.FillTransition;
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

public class Dijkstra extends GraphAnimation{

    public Dijkstra(Graph graph, Slider slider, TextField srcTF, Button edgeBtn,
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
            ArrayList<ArrayList<int[]>> AL = graph.getALW();
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
            for(int i=0;i< graph.getNumVertices();++i){
                Label label = new Label("INF");
                label.setLayoutX(vertices.get(i).getCenterX()-3.7);
                label.setLayoutY(vertices.get(i).getCenterY()+15);
                label.setStyle("-fx-font-weight: bold; -fx-text-fill: red;");
                labels.add(label);
            }
            labels.get(src).setText("0");
            Platform.runLater(()->pane.getChildren().addAll(labels));
            ArrayList<int[]> pq = new ArrayList<>();
            int[] dist = new int[graph.getNumVertices()];
            for(int i=0;i<graph.getNumVertices();++i){
                if(i == src) continue;
                dist[i] = -1;
            }
            pq.add(new int[]{0,src});
            while(!pq.isEmpty()){
                int[] arr = pq.get(0); pq.remove(0);
                int d = arr[0], u = arr[1];
                Circle c = vertices.get(u);
                Platform.runLater(() -> {
                    new FillTransition(Duration.millis(speed),c,Color.WHITE,Color.ORANGE).play();
                    c.setFill(Color.ORANGE);
                });
                try { Thread.sleep((long) speed); }
                catch (InterruptedException e) { Alerts.errorAlert("interruptedException caught","i don't know how it got here"); }

                if(dist[u] != d) {
                    Platform.runLater(() -> {
                        new FillTransition(Duration.millis(speed),c,Color.ORANGE,Color.WHITE).play();
                        c.setFill(Color.WHITE);
                    });
                    try { Thread.sleep((long) speed); }
                    catch (InterruptedException e) { Alerts.errorAlert("interruptedException caught","i don't know how it got here"); }
                    continue;
                }

                for(int[] ar: AL.get(u)){
                    int v = ar[0], dis = ar[1];

                    Line line = null;
                    for(int i=0;i<EL.size();++i){
                        int[] a = EL.get(i);
                        if(a[0] == u && a[1] == v){
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
                    catch (InterruptedException e) { Alerts.errorAlert("interruptedException caught","i don't know how it got here"); }

                    if(dist[v] == -1 || dist[v] > d + dis){
                        dist[v] = d + dis;
                        Platform.runLater(()->labels.get(v).setText(""+dist[v]));
                        pq.add(new int[]{dist[v],v});
                    }

                    Platform.runLater(()-> {
                        new StrokeTransition(Duration.millis(speed),finalLine,Color.ORANGE,Color.BLACK).play();
                        finalLine.setStroke(Color.GREY);
                    });
                    try { Thread.sleep((long) speed); }
                    catch (InterruptedException e) { Alerts.errorAlert("interruptedException caught","i don't know how it got here"); }
                }

                Platform.runLater(() -> {
                    new FillTransition(Duration.millis(speed),c,Color.ORANGE,Color.WHITE).play();
                    c.setFill(Color.WHITE);
                });
                try { Thread.sleep((long) speed); }
                catch (InterruptedException e) { Alerts.errorAlert("interruptedException caught","i don't know how it got here"); }
                pq.sort(new PQComparator());
            }

            Platform.runLater(()->setDisables(false));

        }).start();
    }
}
