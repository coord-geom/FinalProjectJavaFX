package sample.Model.Sorting;

import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import sample.Model.Animation;

import java.util.ArrayList;

public abstract class Sorter implements Animation{
    protected Button start, random, custom;
    protected ArrayList<Rectangle> copy;
    protected ArrayList<Rectangle> rectangles;
    protected ArrayList<Rectangle> rectangles2;
    protected final Pane pane;
    protected int[] array;
    protected int[] arrayCopy;
    protected double speed = 800;
    protected final int maxHeight = 200;
    protected Slider slider;

    public Sorter(int[] array, Pane pane, Slider slider,
                  Button start, Button random, Button custom){
        rectangles2 = new ArrayList<>();
        this.array = new int[array.length];
        System.arraycopy(array,0,this.array,0,array.length);
        this.pane = pane;
        this.start = start;
        this.custom = custom;
        this.random = random;
        this.slider = slider;
        this.slider.valueProperty().addListener(((observableValue, number, t1) -> speed = (slider.getMax()+slider.getMin() - t1.doubleValue()) * 1000 + 300));
    }

    public void setDisables(boolean b){
        start.setDisable(b);
        custom.setDisable(b);
        random.setDisable(b);
    }
    public void start(){
        Runnable task = this::sort;
        Thread bgThread = new Thread(task);
        bgThread.setDaemon(true);
        bgThread.start();
    }
    public abstract void updateRectangles(int[] array);
    public abstract ArrayList<Rectangle> createRectangles(int[] array);
    public abstract void sort();
}
