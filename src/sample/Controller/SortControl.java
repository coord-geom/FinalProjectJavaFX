package sample.Controller;

import javafx.animation.FillTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.Model.Alerts;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class SortControl implements Initializable {
    @FXML
    private TabPane tabPane;
    @FXML
    private AnchorPane bubblePane, insertionPane, countingPane, mergePane;
    @FXML
    private Button bubbleStartButton, bubbleCustomButton, bubbleRandomButton;
    @FXML
    private Button insertStartButton, insertCustomButton, insertRandomButton;
    @FXML
    private Button mergeStartButton, mergeCustomButton, mergeRandomButton;
    @FXML
    private Button countStartButton, countCustomButton, countRandomButton;
    @FXML
    private Slider bubbleSlider, insertSlider, mergeSlider, countSlider;
    @FXML
    private TextField bubbleCustomTF, insertCustomTF, mergeCustomTF, countCustomTF;

    private Random random = new Random();
    private ArrayList<Rectangle> bubbles = new ArrayList<>();
    private ArrayList<Rectangle> bubbles2 = new ArrayList<>();
    private ArrayList<Rectangle> insertions = new ArrayList<>();
    private ArrayList<Rectangle> insertions2 = new ArrayList<>();
    private ArrayList<Rectangle> merges = new ArrayList<>();
    private ArrayList<Rectangle> merges2 = new ArrayList<>();
    private ArrayList<Rectangle> counting = new ArrayList<>();
    private ArrayList<Rectangle> counting2 = new ArrayList<>();


    int[] bubs = new int[15];
    int[] insert = new int[15];
    int[] merge = new int[15];
    int[] count = new int[15];

    private double bubbleSpeed = 1000;
    private double insertSpeed = 1000;
    private double mergeSpeed = 1000;
    private double countSpeed = 1000;
    private final double maxH = 200;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        double max = 0, maxCnt = 0;
        for(int i=0;i<15;++i){
            bubs[i] = random.nextInt(50) + 1;
            insert[i] = merge[i] = bubs[i];
            count[i] = random.nextInt(10) + 1;
            max = Math.max(max,bubs[i]);
            maxCnt = Math.max(maxCnt,count[i]);
        }
        for(int i=0;i<15;++i){
            double height = bubs[i]/max * maxH;
            Rectangle rect;
            rect = new Rectangle(100+45*i,100+maxH-height,35,height);
            rect.setFill(Color.LIGHTSKYBLUE);
            bubbles.add(rect);
            rect = new Rectangle(100+45*i,100+maxH-height,35,height);
            rect.setFill(Color.LIGHTSKYBLUE);
            insertions.add(rect);
            rect = new Rectangle(100+45*i,100+maxH-height,35,height);
            rect.setFill(Color.LIGHTSKYBLUE);
            merges.add(rect);


            height = count[i]/maxCnt * maxH;
            rect = new Rectangle(100+45*i,100+maxH-height,35,height);
            rect.setFill(Color.LIGHTSKYBLUE);
            counting.add(rect);
        }
        bubblePane.getChildren().addAll(bubbles);
        insertionPane.getChildren().addAll(insertions);
        mergePane.getChildren().addAll(merges);
        countingPane.getChildren().addAll(counting);

        bubbleSlider.valueProperty().addListener((observableValue, number, t1) -> bubbleSpeed = (bubbleSlider.getMax()+bubbleSlider.getMin() - t1.doubleValue()) * 1000 + 300);
        insertSlider.valueProperty().addListener((observableValue, number, t1) -> insertSpeed = (insertSlider.getMax()+insertSlider.getMin() - t1.doubleValue()) * 1000 + 300);
        mergeSlider.valueProperty().addListener((observableValue, number, t1) -> mergeSpeed = (mergeSlider.getMax()+mergeSlider.getMin() - t1.doubleValue()) * 1000 + 300);
        countSlider.valueProperty().addListener((observableValue, number, t1) -> countSpeed = (countSlider.getMax()+countSlider.getMin() - t1.doubleValue()) * 1000 + 300);
    }

    public void returnBack(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/sample/View/fxmlFiles/algoPage.fxml"));
            Stage stage = (Stage) tabPane.getScene().getWindow();
            stage.setTitle("Algorithms");
            stage.setScene(new Scene(root));
            stage.show();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void bubbleSort() {
        new Thread(() -> {

            // reset the rectangles
            ArrayList<Rectangle> bubbleCopy = new ArrayList<>(bubbles);
            if(!bubbles2.isEmpty()){
                Platform.runLater(() -> bubblePane.getChildren().removeAll(bubbles2));
                if(!bubblePane.getChildren().containsAll(bubbleCopy))
                    Platform.runLater(() -> bubblePane.getChildren().addAll(bubbleCopy));
            }
            bubbles = new ArrayList<>();
            for (Rectangle r : bubbleCopy) {
                r = new Rectangle(r.getX(), r.getY(), r.getWidth(), r.getHeight());
                r.setFill(Color.LIGHTSKYBLUE);
                bubbles.add(r);
            }
            int[] arrCopy = new int[bubs.length];
            System.arraycopy(bubs, 0, arrCopy, 0, bubs.length);

            // animation
            Platform.runLater(() -> {
                bubbleStartButton.setDisable(true);
                bubbleRandomButton.setDisable(true);
                bubbleCustomButton.setDisable(true);
            });
            for (int i = 0; i < arrCopy.length - 1; ++i) {
                for (int j = 0; j < arrCopy.length - i - 1; ++j) {
                    Rectangle r1 = bubbleCopy.get(j), r2 = bubbleCopy.get(j + 1);
                    Platform.runLater(() -> new ParallelTransition(
                            new FillTransition(Duration.millis(bubbleSpeed), r1, Color.LIGHTSKYBLUE, Color.RED),
                            new FillTransition(Duration.millis(bubbleSpeed), r2, Color.LIGHTSKYBLUE, Color.RED)
                    ).play());
                    try { Thread.sleep((long) bubbleSpeed); }
                    catch (InterruptedException e) { e.printStackTrace(); }
                    if (arrCopy[j] > arrCopy[j + 1]) {
                        MoveTo m1 = new MoveTo(r1.getX()+r1.getWidth()/2,r1.getY()+r1.getHeight()/2),
                                m2 = new MoveTo(r2.getX()+r2.getWidth()/2,r2.getY()+r2.getHeight()/2);
                        LineTo l1 = new LineTo(r1.getX()+45+r1.getWidth()/2,r1.getY()+r1.getHeight()/2),
                                l2 = new LineTo(r2.getX()-45+r2.getWidth()/2,r2.getY()+r2.getHeight()/2);
                        Platform.runLater(() -> new ParallelTransition(
                                new PathTransition(Duration.millis(bubbleSpeed), new Path(m1, l1), r1),
                                new PathTransition(Duration.millis(bubbleSpeed), new Path(m2, l2), r2)
                        ).play());
                        try { Thread.sleep((long) bubbleSpeed); }
                        catch (InterruptedException e) { e.printStackTrace(); }
                        Platform.runLater(() -> {
                            r1.setX(r1.getX() + 45);
                            r2.setX(r2.getX() - 45);
                        });
                        bubbleCopy.set(j, r2);
                        bubbleCopy.set(j + 1, r1);
                        int temp = arrCopy[j + 1];
                        arrCopy[j + 1] = arrCopy[j];
                        arrCopy[j] = temp;
                    }
                    Platform.runLater(() -> new ParallelTransition(
                            new FillTransition(Duration.millis(bubbleSpeed), r1, Color.RED, Color.LIGHTSKYBLUE),
                            new FillTransition(Duration.millis(bubbleSpeed), r2, Color.RED, Color.LIGHTSKYBLUE)
                    ).play());
                    try{ Thread.sleep((long) bubbleSpeed); }
                    catch(InterruptedException e){ e.printStackTrace(); }
                }
                int finalI = i;
                Platform.runLater(() -> new FillTransition(
                        Duration.millis(bubbleSpeed), bubbleCopy.get(arrCopy.length-1-finalI), Color.LIGHTSKYBLUE, Color.ORANGE
                ).play());
                try{ Thread.sleep((long) bubbleSpeed); }
                catch(InterruptedException e){ e.printStackTrace(); }
            }
            Platform.runLater(() -> new FillTransition(
                    Duration.millis(bubbleSpeed), bubbleCopy.get(0), Color.LIGHTSKYBLUE, Color.ORANGE
            ).play());
            try{ Thread.sleep((long) bubbleSpeed); }
            catch(InterruptedException e){ e.printStackTrace(); }
            bubbles2 = bubbleCopy;
            Platform.runLater(() -> {
                bubbleStartButton.setDisable(false);
                bubbleStartButton.setText("Restart!");
                bubbleCustomButton.setDisable(false);
                bubbleRandomButton.setDisable(false);
            });
        }).start();
    }
    public void bubbleStart(){
        Runnable task = this::bubbleSort;
        Thread bgThread = new Thread(task);
        bgThread.setDaemon(true);
        bgThread.start();
    }
    public void bubbleRandom(){
        int[] newArr = new int[random.nextInt(6)+10];
        double max = 0;
        for (int i = 0; i < newArr.length; i++){
            newArr[i] = random.nextInt(50) + 1;
            max = Math.max(max,newArr[i]);
        }
        if(!bubbles2.isEmpty()) bubblePane.getChildren().removeAll(bubbles2);
        else bubblePane.getChildren().removeAll(bubbles);
        bubbles.clear();
        bubbles2.clear();
        bubs = new int[newArr.length];
        for(int i=0;i< newArr.length;++i){
            bubs[i] = newArr[i];
            double height = newArr[i]/max * maxH;
            Rectangle rect;
            rect = new Rectangle(100+45*i,100+maxH-height,35,height);
            rect.setFill(Color.LIGHTSKYBLUE);
            bubbles.add(rect);
        }
        bubblePane.getChildren().addAll(bubbles);
    }
    public void bubbleCustom(){
        String text = bubbleCustomTF.getText();
        if(!text.matches("\\d+(,\\d+)*"))
            Alerts.warningAlert("Custom input is in the wrong format!");
        else{
            String[] numbers = text.split(",");
            if(numbers.length > 15){
                Alerts.warningAlert("max 15 allow!");
                return;
            }
            int[] newArr = new int[numbers.length];
            double max = 0;
            for (int i = 0; i < numbers.length; i++){
                newArr[i] = Integer.parseInt(numbers[i]);
                if(newArr[i] < 1 || newArr[i] > 50){
                    Alerts.warningAlert("number must be within 1 and 50!");
                    return;
                }
                max = Math.max(max,newArr[i]);
            }
            if(!bubbles2.isEmpty()) bubblePane.getChildren().removeAll(bubbles2);
            else bubblePane.getChildren().removeAll(bubbles);
            bubbles.clear();
            bubbles2.clear();
            bubs = new int[newArr.length];
            for(int i=0;i< newArr.length;++i){
                bubs[i] = newArr[i];
                double height = newArr[i]/max * maxH;
                Rectangle rect;
                rect = new Rectangle(100+45*i,100+maxH-height,35,height);
                rect.setFill(Color.LIGHTSKYBLUE);
                bubbles.add(rect);
            }
            bubblePane.getChildren().addAll(bubbles);
        }

    }

    public void insertionSort(){
        new Thread(() -> {
            // reset
            ArrayList<Rectangle> insertCopy = new ArrayList<>(insertions);
            if(!insertions2.isEmpty()){
                Platform.runLater(() -> insertionPane.getChildren().removeAll(insertions2));
                if(!insertionPane.getChildren().containsAll(insertCopy))
                    Platform.runLater(() -> insertionPane.getChildren().addAll(insertCopy));
            }
            insertions = new ArrayList<>();
            for (Rectangle r : insertCopy) {
                r = new Rectangle(r.getX(), r.getY(), r.getWidth(), r.getHeight());
                r.setFill(Color.LIGHTSKYBLUE);
                insertions.add(r);
            }
            int[] arrCopy = new int[insert.length];
            System.arraycopy(insert, 0, arrCopy, 0, insert.length);

            // animation
            Platform.runLater(() -> {
                insertStartButton.setDisable(true);
                insertCustomButton.setDisable(true);
                insertRandomButton.setDisable(true);
            });
            Platform.runLater(() -> new FillTransition(
                    Duration.millis(insertSpeed), insertCopy.get(0), Color.LIGHTSKYBLUE, Color.ORANGE
            ).play());
            try{ Thread.sleep((long) insertSpeed);}
            catch(InterruptedException e){ e.printStackTrace(); }
            for(int i=1;i<arrCopy.length;++i){
                Rectangle r = insertCopy.get(i);
                Platform.runLater(() -> new ParallelTransition(
                        new FillTransition(Duration.millis(insertSpeed), r, Color.LIGHTSKYBLUE, Color.RED),
                        new PathTransition(Duration.millis(insertSpeed), new Path(
                                new MoveTo(r.getX()+r.getWidth()/2,r.getY()+r.getHeight()/2),
                                new LineTo(r.getX()+r.getWidth()/2,r.getY()+r.getHeight()/2 + 200)
                        ), r)
                ).play());
                Platform.runLater(() -> r.setY(r.getY()+200));
                try{ Thread.sleep((long) insertSpeed);}
                catch(InterruptedException e){ e.printStackTrace(); }
                int key = arrCopy[i];
                int k = i-1;
                while(k >= 0 && arrCopy[k] > key){
                    Rectangle r1 = insertCopy.get(k);
                    Platform.runLater(() -> new FillTransition(Duration.millis(insertSpeed), r1, Color.ORANGE, Color.GREEN).play());
                    try{ Thread.sleep((long) insertSpeed);}
                    catch(InterruptedException e){ e.printStackTrace(); }
                    Platform.runLater(() -> new ParallelTransition(
                            new PathTransition(Duration.millis(insertSpeed), new Path(
                                    new MoveTo(r.getX()+r.getWidth()/2,r.getY()+r.getHeight()/2),
                                    new LineTo(r.getX()+r.getWidth()/2-45,r.getY()+r.getHeight()/2)
                            ), r),
                            new PathTransition(Duration.millis(insertSpeed), new Path(
                                    new MoveTo(r1.getX()+r1.getWidth()/2,r1.getY()+r1.getHeight()/2),
                                    new LineTo(r1.getX()+r1.getWidth()/2+45,r1.getY()+r1.getHeight()/2)
                            ), r1)
                    ).play());
                    Platform.runLater(() -> {
                        r.setX(r.getX()-45);
                        r1.setX(r1.getX()+45);
                    });
                    try{ Thread.sleep((long) insertSpeed);}
                    catch(InterruptedException e){ e.printStackTrace(); }
                    Platform.runLater(() -> new FillTransition(Duration.millis(insertSpeed), r1, Color.GREEN, Color.ORANGE).play());
                    arrCopy[k+1] = arrCopy[k];
                    insertCopy.set(k+1,insertCopy.get(k));
                    k--;
                }
                if(k > 0){
                    Rectangle r1 = insertCopy.get(k);
                    Platform.runLater(() -> new FillTransition(Duration.millis(insertSpeed), r1, Color.ORANGE, Color.GREEN).play());
                    try{ Thread.sleep((long) insertSpeed);}
                    catch(InterruptedException e){ e.printStackTrace(); }
                }
                Platform.runLater(() -> new ParallelTransition(
                        new FillTransition(Duration.millis(insertSpeed), r, Color.RED, Color.ORANGE),
                        new PathTransition(Duration.millis(insertSpeed), new Path(
                                new MoveTo(r.getX()+r.getWidth()/2,r.getY()+r.getHeight()/2),
                                new LineTo(r.getX()+r.getWidth()/2,r.getY()+r.getHeight()/2 - 200)
                        ), r)
                ).play());
                if(k > 0){
                    Rectangle r1 = insertCopy.get(k);
                    Platform.runLater(() -> new FillTransition(Duration.millis(insertSpeed), r1, Color.GREEN, Color.ORANGE).play());
                    try{ Thread.sleep((long) insertSpeed);}
                    catch(InterruptedException e){ e.printStackTrace(); }
                }
                Platform.runLater(() -> r.setY(r.getY() - 200));
                try{ Thread.sleep((long) insertSpeed);}
                catch(InterruptedException e){ e.printStackTrace(); }
                arrCopy[k+1] = key;
                insertCopy.set(k+1,r);
            }
            insertions2 = insertCopy;
            Platform.runLater(() -> {
                insertStartButton.setText("Restart!");
                insertStartButton.setDisable(false);
                insertCustomButton.setDisable(false);
                insertRandomButton.setDisable(false);
            });
        }).start();
    }
    public void insertStart(){
        Runnable task = this::insertionSort;
        Thread bgThread = new Thread(task);
        bgThread.setDaemon(true);
        bgThread.start();
    }
    public void insertCustom(){
        String text = insertCustomTF.getText();
        if(!text.matches("\\d+(,\\d+)*"))
            Alerts.warningAlert("Custom input is in the wrong format!");
        else{
            String[] numbers = text.split(",");
            if(numbers.length > 15){
                Alerts.warningAlert("max 15 allow!");
                return;
            }
            int[] newArr = new int[numbers.length];
            double max = 0;
            for (int i = 0; i < numbers.length; i++){
                newArr[i] = Integer.parseInt(numbers[i]);
                if(newArr[i] < 1 || newArr[i] > 50){
                    Alerts.warningAlert("number must be within 1 and 50!");
                    return;
                }
                max = Math.max(max,newArr[i]);
            }
            if(!insertions2.isEmpty()) insertionPane.getChildren().removeAll(insertions2);
            else insertionPane.getChildren().removeAll(insertions);
            insertions.clear();
            insertions2.clear();
            insert = new int[newArr.length];
            for(int i=0;i< newArr.length;++i){
                insert[i] = newArr[i];
                double height = newArr[i]/max * maxH;
                Rectangle rect;
                rect = new Rectangle(100+45*i,100+maxH-height,35,height);
                rect.setFill(Color.LIGHTSKYBLUE);
                insertions.add(rect);
            }
            insertionPane.getChildren().addAll(insertions);
        }
    }
    public void insertRandom(){
        int[] newArr = new int[random.nextInt(6)+10];
        double max = 0;
        for (int i = 0; i < newArr.length; i++){
            newArr[i] = random.nextInt(50) + 1;
            max = Math.max(max,newArr[i]);
        }
        if(!insertions2.isEmpty()) insertionPane.getChildren().removeAll(insertions2);
        else insertionPane.getChildren().removeAll(insertions);
        insertions.clear();
        insertions2.clear();
        insert = new int[newArr.length];
        for(int i=0;i< newArr.length;++i){
            insert[i] = newArr[i];
            double height = newArr[i]/max * maxH;
            Rectangle rect;
            rect = new Rectangle(100+45*i,100+maxH-height,35,height);
            rect.setFill(Color.LIGHTSKYBLUE);
            insertions.add(rect);
        }
        insertionPane.getChildren().addAll(insertions);
    }

    public void mergeSort(){
        new Thread(() -> {

            // reset the rectangles
            ArrayList<Rectangle> mergeCopy = new ArrayList<>(merges);
            if(!merges2.isEmpty()){
                Platform.runLater(() -> mergePane.getChildren().removeAll(merges2));
                if(!mergePane.getChildren().containsAll(mergeCopy))
                    Platform.runLater(() -> mergePane.getChildren().addAll(mergeCopy));
            }
            merges = new ArrayList<>();
            for (Rectangle r : mergeCopy) {
                r = new Rectangle(r.getX(), r.getY(), r.getWidth(), r.getHeight());
                r.setFill(Color.LIGHTSKYBLUE);
                merges.add(r);
            }
            int[] arrCopy = new int[merge.length];
            System.arraycopy(merge, 0, arrCopy, 0, merge.length);

            // animation
            Platform.runLater(() -> {
                mergeStartButton.setDisable(true);
                mergeRandomButton.setDisable(true);
                mergeCustomButton.setDisable(true);
            });
            // something
            merges2 = mergeCopy;
            Platform.runLater(() -> {
                mergeStartButton.setDisable(false);
                mergeStartButton.setText("Restart!");
                mergeCustomButton.setDisable(false);
                mergeRandomButton.setDisable(false);
            });
        }).start();
    }
    public void mergeStart(){
        Runnable task = this::mergeSort;
        Thread bgThread = new Thread(task);
        bgThread.setDaemon(true);
        bgThread.start();
    }
    public void mergeCustom(){
        String text = mergeCustomTF.getText();
        if(!text.matches("\\d+(,\\d+)*"))
            Alerts.warningAlert("Custom input is in the wrong format!");
        else{
            String[] numbers = text.split(",");
            if(numbers.length > 15){
                Alerts.warningAlert("max 15 allow!");
                return;
            }
            int[] newArr = new int[numbers.length];
            double max = 0;
            for (int i = 0; i < numbers.length; i++){
                newArr[i] = Integer.parseInt(numbers[i]);
                if(newArr[i] < 1 || newArr[i] > 50){
                    Alerts.warningAlert("number must be within 1 and 50!");
                    return;
                }
                max = Math.max(max,newArr[i]);
            }
            if(!merges2.isEmpty()) mergePane.getChildren().removeAll(merges2);
            else mergePane.getChildren().removeAll(merges);
            merges.clear();
            merges2.clear();
            merge = new int[newArr.length];
            for(int i=0;i< newArr.length;++i){
                merge[i] = newArr[i];
                double height = newArr[i]/max * maxH;
                Rectangle rect;
                rect = new Rectangle(100+45*i,100+maxH-height,35,height);
                rect.setFill(Color.LIGHTSKYBLUE);
                merges.add(rect);
            }
            mergePane.getChildren().addAll(merges);
        }
    }
    public void mergeRandom(){
        int[] newArr = new int[random.nextInt(6)+10];
        double max = 0;
        for (int i = 0; i < newArr.length; i++){
            newArr[i] = random.nextInt(50) + 1;
            max = Math.max(max,newArr[i]);
        }
        if(!merges2.isEmpty()) mergePane.getChildren().removeAll(merges2);
        else mergePane.getChildren().removeAll(merges);
        merges.clear();
        merges2.clear();
        merge = new int[newArr.length];
        for(int i=0;i< newArr.length;++i){
            merge[i] = newArr[i];
            double height = newArr[i]/max * maxH;
            Rectangle rect;
            rect = new Rectangle(100+45*i,100+maxH-height,35,height);
            rect.setFill(Color.LIGHTSKYBLUE);
            merges.add(rect);
        }
        mergePane.getChildren().addAll(merges);
    }

    public void countingSort(){}
    public void countStart(){
        Runnable task = this::countingSort;
        Thread bgThread = new Thread(task);
        bgThread.setDaemon(true);
        bgThread.start();
    }
    public void countCustom(){
        String text = countCustomTF.getText();
        if(!text.matches("\\d+(,\\d+)*"))
            Alerts.warningAlert("Custom input is in the wrong format!");
        else{
            String[] numbers = text.split(",");
            if(numbers.length > 15){
                Alerts.warningAlert("max 15 allow!");
                return;
            }
            int[] newArr = new int[numbers.length];
            double max = 0;
            for (int i = 0; i < numbers.length; i++){
                newArr[i] = Integer.parseInt(numbers[i]);
                if(newArr[i] < 1 || newArr[i] > 10){
                    Alerts.warningAlert("number must be within 1 and 10!");
                    return;
                }
                max = Math.max(max,newArr[i]);
            }
            if(!counting2.isEmpty()) countingPane.getChildren().removeAll(counting2);
            else countingPane.getChildren().removeAll(counting);
            counting.clear();
            counting2.clear();
            count = new int[newArr.length];
            for(int i=0;i< newArr.length;++i){
                count[i] = newArr[i];
                double height = newArr[i]/max * maxH;
                Rectangle rect;
                rect = new Rectangle(100+45*i,100+maxH-height,35,height);
                rect.setFill(Color.LIGHTSKYBLUE);
                counting.add(rect);
            }
            countingPane.getChildren().addAll(counting);
        }
    }
    public void countRandom(){
        int[] newArr = new int[random.nextInt(6)+10];
        double max = 0;
        for (int i = 0; i < newArr.length; i++){
            newArr[i] = random.nextInt(10) + 1;
            max = Math.max(max,newArr[i]);
        }
        if(!counting2.isEmpty()) countingPane.getChildren().removeAll(counting2);
        else countingPane.getChildren().removeAll(counting);
        counting.clear();
        counting2.clear();
        count = new int[newArr.length];
        for(int i=0;i< newArr.length;++i){
            count[i] = newArr[i];
            double height = newArr[i]/max * maxH;
            Rectangle rect;
            rect = new Rectangle(100+45*i,100+maxH-height,35,height);
            rect.setFill(Color.LIGHTSKYBLUE);
            counting.add(rect);
        }
        countingPane.getChildren().addAll(counting);
    }
}
