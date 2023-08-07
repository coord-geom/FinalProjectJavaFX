package sample.Model.Sorting;

import javafx.animation.FillTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;

public class InsertionSorter extends Sorter {

    public InsertionSorter(int[] array, Pane pane, Slider slider, Button start, Button random, Button custom) {
        super(array, pane, slider, start, random, custom);
        rectangles = createRectangles(array);
        pane.getChildren().addAll(rectangles);
    }

    public void updateRectangles(int[] array) {
        if(!rectangles2.isEmpty()) pane.getChildren().removeAll(rectangles2);
        else pane.getChildren().removeAll(this.rectangles);
        rectangles.clear();
        rectangles2.clear();
        this.array = new int[array.length];
        System.arraycopy(array,0,this.array,0,array.length);
        rectangles = createRectangles(this.array);
        pane.getChildren().addAll(rectangles);
    }

    public ArrayList<Rectangle> createRectangles(int[] array) {
        ArrayList<Rectangle> res = new ArrayList<>();
        int maxNum = Arrays.stream(array).max().getAsInt();
        for (int i = 0; i < array.length; i++) {
            double height = array[i]/(double)maxNum * maxHeight;
            Rectangle rect = new Rectangle(100+45*i,100+maxHeight-height,35,height);
            rect.setFill(Color.LIGHTSKYBLUE);
            res.add(rect);
        }
        return res;
    }

    public void start(){
        Runnable task = this::sort;
        Thread bgThread = new Thread(task);
        bgThread.setDaemon(true);
        bgThread.start();
    }

    public void sort() {
        new Thread(() -> {
            copy = new ArrayList<>(rectangles);
            if(!rectangles2.isEmpty()){
                Platform.runLater(() -> pane.getChildren().removeAll(rectangles2));
                if(!pane.getChildren().containsAll(copy))
                    Platform.runLater(() -> pane.getChildren().addAll(copy));
            }
            rectangles = new ArrayList<>();
            for (Rectangle r : copy) {
                r = new Rectangle(r.getX(), r.getY(), r.getWidth(), r.getHeight());
                r.setFill(Color.LIGHTSKYBLUE);
                rectangles.add(r);
            }
            int[] arrCopy = new int[array.length];
            System.arraycopy(array, 0, arrCopy, 0, array.length);

            // animation
            Platform.runLater(() -> setDisables(true));
            Platform.runLater(() -> new FillTransition(
                    Duration.millis(speed), copy.get(0), Color.LIGHTSKYBLUE, Color.ORANGE
            ).play());
            try{ Thread.sleep((long) speed/2);}
            catch(InterruptedException e){ e.printStackTrace(); }
            for(int i=1;i<arrCopy.length;++i){
                Rectangle r = copy.get(i);
                Platform.runLater(() -> new ParallelTransition(
                        new FillTransition(Duration.millis(speed), r, Color.LIGHTSKYBLUE, Color.RED),
                        new PathTransition(Duration.millis(speed), new Path(
                                new MoveTo(r.getX()+r.getWidth()/2,r.getY()+r.getHeight()/2),
                                new LineTo(r.getX()+r.getWidth()/2,r.getY()+r.getHeight()/2 + 200)
                        ), r)
                ).play());
                Platform.runLater(() -> r.setY(r.getY()+200));
                try{ Thread.sleep((long) speed/2);}
                catch(InterruptedException e){ e.printStackTrace(); }
                int key = arrCopy[i];
                int k = i-1;
                while(k >= 0 && arrCopy[k] > key){
                    Rectangle r1 = copy.get(k);
                    Platform.runLater(() -> new FillTransition(Duration.millis(speed), r1, Color.ORANGE, Color.GREEN).play());
                    try{ Thread.sleep((long) speed/2);}
                    catch(InterruptedException e){ e.printStackTrace(); }
                    Platform.runLater(() -> new ParallelTransition(
                            new PathTransition(Duration.millis(speed), new Path(
                                    new MoveTo(r.getX()+r.getWidth()/2,r.getY()+r.getHeight()/2),
                                    new LineTo(r.getX()+r.getWidth()/2-45,r.getY()+r.getHeight()/2)
                            ), r),
                            new PathTransition(Duration.millis(speed), new Path(
                                    new MoveTo(r1.getX()+r1.getWidth()/2,r1.getY()+r1.getHeight()/2),
                                    new LineTo(r1.getX()+r1.getWidth()/2+45,r1.getY()+r1.getHeight()/2)
                            ), r1)
                    ).play());
                    Platform.runLater(() -> {
                        r.setX(r.getX()-45);
                        r1.setX(r1.getX()+45);
                    });
                    try{ Thread.sleep((long) speed/2);}
                    catch(InterruptedException e){ e.printStackTrace(); }
                    Platform.runLater(() -> new FillTransition(Duration.millis(speed), r1, Color.GREEN, Color.ORANGE).play());
                    arrCopy[k+1] = arrCopy[k];
                    copy.set(k+1,copy.get(k));
                    k--;
                }
                if(k > 0){
                    Rectangle r1 = copy.get(k);
                    Platform.runLater(() -> new FillTransition(Duration.millis(speed), r1, Color.ORANGE, Color.GREEN).play());
                    try{ Thread.sleep((long) speed/2);}
                    catch(InterruptedException e){ e.printStackTrace(); }
                }
                Platform.runLater(() -> new ParallelTransition(
                        new FillTransition(Duration.millis(speed), r, Color.RED, Color.ORANGE),
                        new PathTransition(Duration.millis(speed), new Path(
                                new MoveTo(r.getX()+r.getWidth()/2,r.getY()+r.getHeight()/2),
                                new LineTo(r.getX()+r.getWidth()/2,r.getY()+r.getHeight()/2 - 200)
                        ), r)
                ).play());
                if(k > 0){
                    Rectangle r1 = copy.get(k);
                    Platform.runLater(() -> new FillTransition(Duration.millis(speed), r1, Color.GREEN, Color.ORANGE).play());
                    try{ Thread.sleep((long) speed/2);}
                    catch(InterruptedException e){ e.printStackTrace(); }
                }
                Platform.runLater(() -> r.setY(r.getY() - 200));
                try{ Thread.sleep((long) speed/2);}
                catch(InterruptedException e){ e.printStackTrace(); }
                arrCopy[k+1] = key;
                copy.set(k+1,r);
            }
            rectangles2 = copy;
            Platform.runLater(() -> {
                start.setText("Restart!");
                setDisables(false);
            });
        }).start();
    }
}
