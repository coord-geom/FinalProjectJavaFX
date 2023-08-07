package sample.Model.Sorting;

import javafx.animation.FadeTransition;
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
import java.util.List;

public class CountingSorter extends Sorter {
    public CountingSorter(int[] array, Pane pane, Slider slider, Button start, Button random, Button custom) {
        super(array, pane, slider, start, random, custom);
        rectangles = createRectangles(array);
        pane.getChildren().addAll(rectangles);
    }

    public ArrayList<Rectangle> createRectangles(int[] array){
        ArrayList<Rectangle> res = new ArrayList<>();
        int maxNum = 10;
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
            // reset the rectangles
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
            ArrayList<Rectangle> rects = new ArrayList<>();
            ParallelTransition pll = new ParallelTransition();
            for(int i=1;i<=10;++i){
                double height = (double)i/10 * maxHeight;
                Rectangle r = new Rectangle(145+45*i,310+maxHeight-height,35,height);
                r.setOpacity(0);
                rects.add(r);
                FadeTransition ft = new FadeTransition(Duration.millis(speed),r);
                ft.setFromValue(0); ft.setToValue(0.2);
                pll.getChildren().add(ft);
            }
            Platform.runLater(() -> pane.getChildren().addAll(rects));
            Platform.runLater(pll::play);
            try{ Thread.sleep((long) speed/2); }
            catch(InterruptedException e){ e.printStackTrace(); }
            // code goes here
            List<ArrayList<Rectangle>> store = new ArrayList<>();
            for(int i=0;i<10;++i){
                store.add(new ArrayList<>());
            }
            for(int i=0;i<arrCopy.length;++i){
                Rectangle r = copy.get(i);
                int finalI = i;
                Platform.runLater(() -> new PathTransition(
                        Duration.millis(speed),
                        new Path(
                                new MoveTo(r.getX()+r.getWidth()/2,r.getY()+r.getHeight()/2),
                                new LineTo(145+45*arrCopy[finalI]+(double)35/2,r.getY()+r.getHeight()/2+210)
                        ),
                        r
                ).play());
                Platform.runLater(() -> {
                    r.setX(145+45*arrCopy[finalI]);
                    r.setY(r.getY()+200);
                });
                try{ Thread.sleep((long) speed/2); }
                catch(InterruptedException e){ e.printStackTrace(); }
                store.get(arrCopy[i]-1).add(r);
            }
            try{ Thread.sleep((long) speed/2); }
            catch(InterruptedException e){ e.printStackTrace(); }
            int counter = 0;
            for(ArrayList<Rectangle> lst: store){
                for(Rectangle r: lst){
                    int finalCounter = counter;
                    Platform.runLater(() -> new PathTransition(
                            Duration.millis(speed),
                            new Path(
                                    new MoveTo(r.getX()+r.getWidth()/2,r.getY()+r.getHeight()/2),
                                    new LineTo(100+45*finalCounter+(double)35/2,r.getY()+r.getHeight()/2-200)
                            ),
                            r
                    ).play());
                    ++counter;
                    Platform.runLater(() -> {
                        r.setX(100+45*finalCounter);
                        r.setY(r.getY()-200);
                    });
                    try{ Thread.sleep((long) speed/2); }
                    catch(InterruptedException e){ e.printStackTrace(); }
                }
            }
            pll.getChildren().clear();
            for(Rectangle r: rects){
                FadeTransition ft = new FadeTransition(Duration.millis(speed), r);
                ft.setFromValue(0.2); ft.setToValue(0);
                pll.getChildren().add(ft);
            }
            Platform.runLater(pll::play);
            try{ Thread.sleep((long) speed/2); }
            catch(InterruptedException e){ e.printStackTrace(); }
            Platform.runLater(() -> pane.getChildren().removeAll(rects));
            // code ends here
            rectangles2 = copy;
            Platform.runLater(() -> {
                start.setText("Restart!");
                setDisables(false);
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
