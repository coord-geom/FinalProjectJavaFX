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

public class MergeSorter extends Sorter {

    public MergeSorter(int[] array, Pane pane, Slider slider, Button start, Button random, Button custom) {
        super(array, pane, slider, start, random, custom);
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

    public void start(){
        Runnable task = this::sort;
        Thread bgThread = new Thread(task);
        bgThread.setDaemon(true);
        bgThread.start();
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
            int[] num = new int[array.length];
            for(int i=0;i<num.length;++i) num[i] = i;
            System.arraycopy(array, 0, arrCopy, 0, array.length);

            // animation
            Platform.runLater(() -> {
                start.setDisable(true);
                random.setDisable(true);
                custom.setDisable(true);
            });
            mergesort(arrCopy,arrCopy.length,num);
            rectangles2 = copy;
            Platform.runLater(() -> {
                start.setDisable(false);
                start.setText("Restart!");
                custom.setDisable(false);
                random.setDisable(false);
            });
        }).start();
    }

    public void mergesort(int[] a,int n,int[] lst){
        if(n < 2) return;
        int mid = (n+1)/2;
        int[] l = new int[mid];
        int[] r = new int[n - mid];
        int[] l_lst = new int[mid];
        int[] r_lst = new int[n-mid];
        System.arraycopy(lst, 0, l_lst, 0, mid);
        System.arraycopy(lst, mid, r_lst, 0, n - mid);
        System.arraycopy(a, 0, l, 0, mid);
        System.arraycopy(a, mid, r, 0, n - mid);

        mergesort(l,mid,l_lst);
        mergesort(r,n-mid,r_lst);

        mergeArr(a,l,r,mid,n-mid,l_lst,r_lst,lst);
    }
    public void mergeArr(int[] a,int[] l,int[] r,int left,int right,int[] l_lst,int[] r_lst,int[] lst){
        int i=0,j=0,k=0;
        double X = 1023456789;
        double xx = 1023456789;
        for (int value : lst) {
            Rectangle rect = copy.get(value);
            X = Math.min(X, rect.getX() + rect.getWidth() / 2);
            xx = Math.min(xx, rect.getX());
        }
        while(i<left && j<right){
            if(l[i] <= r[j]) {
                Rectangle rect = copy.get(l_lst[i]);
                int finalK = k;
                double finalX1 = X;
                Platform.runLater(() -> new ParallelTransition(
                        new FillTransition(Duration.millis(speed),rect, Color.LIGHTSKYBLUE,Color.RED),
                        new PathTransition(Duration.millis(speed),new Path(
                                new MoveTo(rect.getX()+rect.getWidth()/2,rect.getY()+rect.getHeight()/2),
                                new LineTo(finalX1 +45* finalK,rect.getY()+rect.getHeight()/2+200)
                        ),rect)
                ).play());
                double finalXx = xx;
                Platform.runLater(() -> {
                    rect.setX(finalXx +45*finalK);
                    rect.setY(rect.getY()+200);
                });
                try{ Thread.sleep((long) speed/2); }
                catch(InterruptedException e){ e.printStackTrace(); }
                a[k] = l[i];
                lst[k++] = l_lst[i++];
            }
            else{
                Rectangle rect = copy.get(r_lst[j]);
                int finalK = k;
                double finalX2 = X;
                Platform.runLater(() -> new ParallelTransition(
                        new FillTransition(Duration.millis(speed),rect,Color.LIGHTSKYBLUE,Color.RED),
                        new PathTransition(Duration.millis(speed),new Path(
                                new MoveTo(rect.getX()+rect.getWidth()/2,rect.getY()+rect.getHeight()/2),
                                new LineTo(finalX2 +45* finalK,rect.getY()+rect.getHeight()/2+200)
                        ),rect)
                ).play());
                double finalXx1 = xx;
                Platform.runLater(() -> {
                    rect.setX(finalXx1 +45*finalK);
                    rect.setY(rect.getY()+200);
                });
                try{ Thread.sleep((long) speed/2); }
                catch(InterruptedException e){ e.printStackTrace(); }
                a[k] = r[j];
                lst[k++] = r_lst[j++];
            }
        }
        while(i<left){
            Rectangle rect = copy.get(l_lst[i]);
            int finalK = k;
            double finalX3 = X;
            Platform.runLater(() -> new ParallelTransition(
                    new FillTransition(Duration.millis(speed),rect,Color.LIGHTSKYBLUE,Color.RED),
                    new PathTransition(Duration.millis(speed),new Path(
                            new MoveTo(rect.getX()+rect.getWidth()/2,rect.getY()+rect.getHeight()/2),
                            new LineTo(finalX3 +45*finalK,rect.getY()+rect.getHeight()/2+200)
                    ),rect)
            ).play());
            double finalXx2 = xx;
            Platform.runLater(() -> {
                rect.setX(finalXx2 +45*finalK);
                rect.setY(rect.getY()+200);
            });
            try{ Thread.sleep((long) speed/2); }
            catch(InterruptedException e){ e.printStackTrace(); }
            lst[k] = l_lst[i];
            a[k++] = l[i++];
        }
        while(j<right){
            Rectangle rect = copy.get(r_lst[j]);
            int finalK = k;
            double finalX4 = X;
            Platform.runLater(() -> new ParallelTransition(
                    new FillTransition(Duration.millis(speed),rect,Color.LIGHTSKYBLUE,Color.RED),
                    new PathTransition(Duration.millis(speed),new Path(
                            new MoveTo(rect.getX()+rect.getWidth()/2,rect.getY()+rect.getHeight()/2),
                            new LineTo(finalX4 +45*finalK,rect.getY()+rect.getHeight()/2+200)
                    ),rect)
            ).play());
            double finalXx3 = xx;
            Platform.runLater(() -> {
                rect.setX(finalXx3 +45*finalK);
                rect.setY(rect.getY()+200);
            });
            try{ Thread.sleep((long) speed/2); }
            catch(InterruptedException e){ e.printStackTrace(); }
            lst[k] = r_lst[j];
            a[k++] = r[j++];
        }
        for (int value : lst) {
            Rectangle rect = copy.get(value);
            Platform.runLater(() -> new ParallelTransition(
                    new FillTransition(Duration.millis(speed), rect, Color.RED, Color.LIGHTSKYBLUE),
                    new PathTransition(Duration.millis(speed), new Path(
                            new MoveTo(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2),
                            new LineTo(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2 - 200)
                    ), rect)
            ).play());
            Platform.runLater(() -> rect.setY(rect.getY() - 200));
            try {
                Thread.sleep((long) speed/2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
