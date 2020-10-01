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

public class BubbleSorter extends Sorter {
    public BubbleSorter(int[] array, Pane pane, Slider slider,
                  Button start, Button random, Button custom){
        super(array,pane,slider,start,random,custom);
        rectangles = createRectangles(array);
        pane.getChildren().addAll(rectangles);
    }

    public ArrayList<Rectangle> createRectangles(int[] array){
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

    public void updateRectangles(int[] array){
        if(!rectangles2.isEmpty()) pane.getChildren().removeAll(rectangles2);
        else pane.getChildren().removeAll(this.rectangles);
        rectangles.clear();
        rectangles2.clear();
        this.array = new int[array.length];
        System.arraycopy(array,0,this.array,0,array.length);
        rectangles = createRectangles(this.array);
        pane.getChildren().addAll(rectangles);
    }

    public void sort() {
        new Thread(() -> {
            copy = new ArrayList<>(rectangles);
            if (!rectangles2.isEmpty()) {
                Platform.runLater(() -> pane.getChildren().removeAll(rectangles2));
                if (!pane.getChildren().containsAll(copy))
                    Platform.runLater(() -> pane.getChildren().addAll(copy));
            }
            rectangles = new ArrayList<>();
            for (Rectangle r : copy) {
                r = new Rectangle(r.getX(), r.getY(), r.getWidth(), r.getHeight());
                r.setFill(Color.LIGHTSKYBLUE);
                rectangles.add(r);
            }
            arrayCopy = new int[array.length];
            System.arraycopy(array, 0, arrayCopy, 0, array.length);
            Platform.runLater(() -> setDisables(true));
            for (int i = 0; i < arrayCopy.length - 1; ++i) {
                for (int j = 0; j < arrayCopy.length - i - 1; ++j) {
                    Rectangle r1 = copy.get(j), r2 = copy.get(j + 1);
                    Platform.runLater(() -> new ParallelTransition(
                            new FillTransition(Duration.millis(speed), r1, Color.LIGHTSKYBLUE, Color.RED),
                            new FillTransition(Duration.millis(speed), r2, Color.LIGHTSKYBLUE, Color.RED)
                    ).play());
                    try {
                        Thread.sleep((long) speed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (arrayCopy[j] > arrayCopy[j + 1]) {
                        MoveTo m1 = new MoveTo(r1.getX() + r1.getWidth() / 2, r1.getY() + r1.getHeight() / 2),
                                m2 = new MoveTo(r2.getX() + r2.getWidth() / 2, r2.getY() + r2.getHeight() / 2);
                        LineTo l1 = new LineTo(r1.getX() + 45 + r1.getWidth() / 2, r1.getY() + r1.getHeight() / 2),
                                l2 = new LineTo(r2.getX() - 45 + r2.getWidth() / 2, r2.getY() + r2.getHeight() / 2);
                        Platform.runLater(() -> new ParallelTransition(
                                new PathTransition(Duration.millis(speed), new Path(m1, l1), r1),
                                new PathTransition(Duration.millis(speed), new Path(m2, l2), r2)
                        ).play());
                        try {
                            Thread.sleep((long) speed);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Platform.runLater(() -> {
                            r1.setX(r1.getX() + 45);
                            r2.setX(r2.getX() - 45);
                        });
                        copy.set(j, r2);
                        copy.set(j + 1, r1);
                        int temp = arrayCopy[j + 1];
                        arrayCopy[j + 1] = arrayCopy[j];
                        arrayCopy[j] = temp;
                    }
                    Platform.runLater(() -> new ParallelTransition(
                            new FillTransition(Duration.millis(speed), r1, Color.RED, Color.LIGHTSKYBLUE),
                            new FillTransition(Duration.millis(speed), r2, Color.RED, Color.LIGHTSKYBLUE)
                    ).play());
                    try {
                        Thread.sleep((long) speed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                int finalI = i;
                Platform.runLater(() -> new FillTransition(
                        Duration.millis(speed), copy.get(arrayCopy.length - 1 - finalI), Color.LIGHTSKYBLUE, Color.ORANGE
                ).play());
                try {
                    Thread.sleep((long) speed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Platform.runLater(() -> new FillTransition(
                    Duration.millis(speed), copy.get(0), Color.LIGHTSKYBLUE, Color.ORANGE
            ).play());
            try {
                Thread.sleep((long) speed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            rectangles2 = copy;
            Platform.runLater(() -> {
                setDisables(false);
                start.setText("Restart!");
            });
        }).start();
    }
    
    public void start(){
        Runnable task = this::sort;
        Thread bgThread = new Thread(task);
        bgThread.setDaemon(true);
        bgThread.start();
    }
}
