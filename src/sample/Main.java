package sample;

import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import sample.Model.General.Alerts;
import sample.Model.Sorting.BubbleSorter;


import java.io.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;


public class Main extends Application {

    public static final String SPLASH_IMAGE = "/ConspicioAlgo.png";
    private VBox splash;
    private final Stage mainStage = new Stage();
    private static final int SPLASH_WIDTH = 400;
    public static Stage stage;

    private ArrayList<Rectangle> rectangles;
    private final int[] array = new int[]{3,44,38,5,47,15};

    @Override
    public void init(){
        AnchorPane pane = new AnchorPane();
        pane.setPrefSize(400,200);
        Slider slider = new Slider();
        slider.setMin(0); slider.setMax(1); slider.setValue(0.5);
        BubbleSorter bs = new BubbleSorter(array, pane, slider, new Button(), new Button(), new Button());
        slider.setValue(0.8);
        rectangles = bs.getRectangles();
        for(Rectangle r: rectangles){
            r.setY(r.getY()-100);
            r.setX(r.getX()-28);
        }
        ImageView img = new ImageView(new Image(SPLASH_IMAGE));
        img.setPreserveRatio(true);
        img.setFitWidth(SPLASH_WIDTH);
        ProgressBar pbar = new ProgressBar();
        pbar.setPrefWidth(SPLASH_WIDTH);
        splash = new VBox();
        splash.getChildren().addAll(img, pane, pbar);
        splash.setAlignment(Pos.BASELINE_CENTER);
    }

    @Override
    public void start(Stage stage){
        Task<Boolean> task = new Task<>() {
            @Override
            protected Boolean call(){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Alerts.errorAlert("interruptedException caught","i don't know how it got here");
                }
                double speed = 10; // lower number is faster
                ArrayList<Rectangle> copy = new ArrayList<>(rectangles);
                rectangles = new ArrayList<>();
                for (Rectangle r : copy) {
                    r = new Rectangle(r.getX(), r.getY(), r.getWidth(), r.getHeight());
                    r.setFill(Color.LIGHTSKYBLUE);
                    rectangles.add(r);
                }
                int[] arrayCopy = new int[array.length];
                System.arraycopy(array, 0, arrayCopy, 0, array.length);
                for (int i = 0; i < arrayCopy.length - 1; ++i) {
                    for (int j = 0; j < arrayCopy.length - i - 1; ++j) {
                        Rectangle r1 = copy.get(j), r2 = copy.get(j + 1);
                        Platform.runLater(() -> new ParallelTransition(
                                new FillTransition(Duration.millis(speed), r1, Color.LIGHTSKYBLUE, Color.RED),
                                new FillTransition(Duration.millis(speed), r2, Color.LIGHTSKYBLUE, Color.RED)
                        ).play());
                        try {
                            Thread.sleep((long) speed/2);
                        } catch (InterruptedException e) {
                            Alerts.errorAlert("interruptedException caught","i don't know how it got here");
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
                                Thread.sleep((long) speed/2);
                            } catch (InterruptedException e) {
                                Alerts.errorAlert("interruptedException caught","i don't know how it got here");
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
                            Thread.sleep((long) speed/2);
                        } catch (InterruptedException e) {
                            Alerts.errorAlert("interruptedException caught","i don't know how it got here");
                        }
                    }
                    int finalI = i;
                    Platform.runLater(() -> new FillTransition(
                            Duration.millis(speed), copy.get(arrayCopy.length - 1 - finalI), Color.LIGHTSKYBLUE, Color.ORANGE
                    ).play());
                    try {
                        Thread.sleep((long) speed/2);
                    } catch (InterruptedException e) {
                        Alerts.errorAlert("interruptedException caught","i don't know how it got here");
                    }
                }
                Platform.runLater(() -> new FillTransition(
                        Duration.millis(speed), copy.get(0), Color.LIGHTSKYBLUE, Color.ORANGE
                ).play());
                try {
                    Thread.sleep((long) speed*2);
                } catch (InterruptedException e) {
                    Alerts.errorAlert("interruptedException caught","i don't know how it got here");
                }
                return null;
            }
        };
        showSplash(stage,task,() -> {
            try { //showLogin(mainStage);
                showStart(mainStage); }
            catch (Exception e) {
                Alerts.errorAlert("Error loading page!");
                Platform.exit();
            }
        });
        new Thread(task).start();
    }

    private void showSplash(final Stage initStage, Task<Boolean> task, InitCompletionHandler ich) {
        task.stateProperty().addListener(((observableValue, state, t1) -> {
            if(t1 == Worker.State.SUCCEEDED){
                initStage.toFront();
                FadeTransition fadeSplash = new FadeTransition(Duration.seconds(1.2), splash);
                fadeSplash.setFromValue(1.0);
                fadeSplash.setToValue(0.0);
                fadeSplash.setOnFinished(actionEvent -> initStage.hide());
                fadeSplash.play();

                ich.complete();
            }
        }));
        Scene splashScene = new Scene(splash, Color.TRANSPARENT);
        initStage.setScene(splashScene);
        initStage.initStyle(StageStyle.TRANSPARENT);
        //initStage.setAlwaysOnTop(true);
        initStage.show();
    }

    public interface InitCompletionHandler { void complete(); }

    public void showStart(Stage primaryStage) throws Exception{
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/sample/View/fxmlFiles/graphSPA.fxml"));
        stage.setTitle("Single Source Shortest Path Algorithms");
        primaryStage.setTitle("Single Source Shortest Path Algorithms");
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/sample/View/materials.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    public static void main(String[] args) {
        locale = Locale.getDefault();
        launch(args);
    }

    public static Locale locale;

    public static ResourceBundle getMessages(){
        return ResourceBundle.getBundle("sample/View/Internationalization/MessagesBundle",locale);
    }
}
