package sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Model.*;
import sample.Model.Sorting.BubbleSorter;
import sample.Model.Sorting.CountingSorter;
import sample.Model.Sorting.InsertionSorter;
import sample.Model.Sorting.MergeSorter;

import java.io.IOException;
import java.net.URL;
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

    private final Random random = new Random();

    private BubbleSorter bs;
    private InsertionSorter is;
    private MergeSorter ms;
    private CountingSorter cs;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        int[] bubs = new int[15];
        int[] insert = new int[15];
        int[] merge = new int[15];
        int[] count = new int[15];
        for(int i=0;i<15;++i){
            bubs[i] = insert[i] = merge[i] = random.nextInt(50) + 1;
            count[i] = random.nextInt(10)+1;
        }
        bs = new BubbleSorter(bubs,bubblePane,bubbleSlider,bubbleStartButton,bubbleRandomButton,bubbleCustomButton);
        is = new InsertionSorter(insert,insertionPane,insertSlider,insertStartButton,insertRandomButton,insertCustomButton);
        ms = new MergeSorter(merge,mergePane,mergeSlider,mergeStartButton,mergeRandomButton,mergeCustomButton);
        cs = new CountingSorter(count,countingPane,countSlider,countStartButton,countRandomButton,countCustomButton);
    }
    public void returnBack(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/sample/View/fxmlFiles/algoPage.fxml"));
            Stage stage = (Stage) tabPane.getScene().getWindow();
            stage.setTitle("Algorithms");
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/sample/View/materials.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void bubbleStart(){
        bs.start();
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
            for (int i = 0; i < numbers.length; i++){
                newArr[i] = Integer.parseInt(numbers[i]);
                if(newArr[i] < 1 || newArr[i] > 50){
                    Alerts.warningAlert("number must be within 1 and 50!");
                    return;
                }
            }
            bs.updateRectangles(newArr);
        }

    }
    public void bubbleRandom(){
        int[] newArr = new int[15];
        for (int i = 0; i < newArr.length; i++)
            newArr[i] = random.nextInt(50) + 1;
        bs.updateRectangles(newArr);
    }

    public void insertStart(){
        is.start();
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
            for (int i = 0; i < numbers.length; i++){
                newArr[i] = Integer.parseInt(numbers[i]);
                if(newArr[i] < 1 || newArr[i] > 50){
                    Alerts.warningAlert("number must be within 1 and 50!");
                    return;
                }
            }
            is.updateRectangles(newArr);
        }
    }
    public void insertRandom(){
        int[] newArr = new int[15];
        for (int i = 0; i < newArr.length; i++)
            newArr[i] = random.nextInt(50) + 1;
        is.updateRectangles(newArr);
    }

    public void mergeStart(){
        ms.start();
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
            for (int i = 0; i < numbers.length; i++){
                newArr[i] = Integer.parseInt(numbers[i]);
                if(newArr[i] < 1 || newArr[i] > 50){
                    Alerts.warningAlert("number must be within 1 and 50!");
                    return;
                }
            }
            ms.updateRectangles(newArr);
        }
    }
    public void mergeRandom(){
        int[] newArr = new int[random.nextInt(6)+10];
        double max = 0;
        for (int i = 0; i < newArr.length; i++){
            newArr[i] = random.nextInt(50) + 1;
            max = Math.max(max,newArr[i]);
        }
        ms.updateRectangles(newArr);
    }

    public void countStart(){
        cs.start();
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
            for (int i = 0; i < numbers.length; i++){
                newArr[i] = Integer.parseInt(numbers[i]);
                if(newArr[i] < 1 || newArr[i] > 10){
                    Alerts.warningAlert("number must be within 1 and 10!");
                    return;
                }
            }
            cs.updateRectangles(newArr);
        }
    }
    public void countRandom(){
        int[] newArr = new int[15];
        for (int i = 0; i < newArr.length; i++)
            newArr[i] = random.nextInt(10) + 1;
        cs.updateRectangles(newArr);
    }

    public void loadInfo(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/sample/View/fxmlFiles/sortInfo.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Sorting Info");
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/sample/View/materials.css").toExternalForm());
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    public void loadCode(){
        try{
            Parent root = FXMLLoader.load(SortControl.class.getResource("/sample/View/fxmlFiles/sortCode.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Sorting Code Examples");
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/sample/View/materials.css").toExternalForm());
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
