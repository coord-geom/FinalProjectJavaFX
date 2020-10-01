package sample;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import sample.Model.Alerts;

import java.io.IOException;


public class Main extends Application {

    public static final String SPLASH_IMAGE = "/ConspicioAlgo.png";
    private Pane splashLayout;
    private ProgressBar loadProgress;
    private Label progressText;
    private final Stage mainStage = new Stage();
    private static final int SPLASH_WIDTH = 400;
    private static final int SPLASH_HEIGHT = 200;

    @Override
    public void init(){
        ImageView splash = new ImageView(new Image(SPLASH_IMAGE));
        splash.setPreserveRatio(true);
        splash.setFitHeight(100);
        loadProgress = new ProgressBar();
        loadProgress.setPrefWidth(SPLASH_WIDTH);
        progressText = new Label("Preparing journey...");
        splashLayout = new VBox();
        splashLayout.getChildren().addAll(splash, loadProgress, progressText);
        progressText.setAlignment(Pos.CENTER);
        splashLayout.setStyle(
                "-fx-padding: 5; " +
                        "-fx-background-color: ivory; " +
                        "-fx-border-width: 5; " +
                        "-fx-border-color: " +
                        "linear-gradient(" +
                        "to bottom, " +
                        "azure, " +
                        "derive(gold, 50%)" +
                        ");"
        );
        splashLayout.setEffect(new DropShadow());
    }

    @Override
    public void start(final Stage initStage) {
        final Task<ObservableList<String>> itemTask = new Task<>() {
            @Override
            protected ObservableList<String> call() throws InterruptedException {
                ObservableList<String> items =
                        FXCollections.observableArrayList();
                ObservableList<String> listItems =
                        FXCollections.observableArrayList(
                                "data...", "files...", "users...", "spaceships...",
                                "algorithms...", "simulations...", "fuel...", "animations...",
                                "languages...", "images...", "packages..."
                        );

                updateMessage("Loading...");
                Thread.sleep(1675);
                for (int i = 0; i < listItems.size(); i++) {
                    Thread.sleep(100);
                    updateProgress(i + 1, listItems.size());
                    String item = listItems.get(i);
                    items.add(item);
                    updateMessage("Loading " + item);
                }
                Thread.sleep(200);

                return listItems;
            }
        };

        showSplash(
                initStage,
                itemTask,
                () -> {
                    try {
                        showLogin(mainStage);
                    } catch (Exception e) {
                        Alerts.errorAlert("Error loading page!");
                        Platform.exit();
                    }
                }
        );
        new Thread(itemTask).start();
    }

    private void showSplash(final Stage initStage, Task<?> task, InitCompletionHandler initCompletionHandler) {
        progressText.textProperty().bind(task.messageProperty());
        loadProgress.progressProperty().bind(task.progressProperty());
        task.stateProperty().addListener((observableValue, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                loadProgress.progressProperty().unbind();
                loadProgress.setProgress(1);
                initStage.toFront();
                FadeTransition fadeSplash = new FadeTransition(Duration.seconds(1.2), splashLayout);
                fadeSplash.setFromValue(1.0);
                fadeSplash.setToValue(0.0);
                fadeSplash.setOnFinished(actionEvent -> initStage.hide());
                fadeSplash.play();

                initCompletionHandler.complete();
            }
        });

        Scene splashScene = new Scene(splashLayout, Color.TRANSPARENT);
        final Rectangle2D bounds = Screen.getPrimary().getBounds();
        initStage.setScene(splashScene);
        initStage.setX(bounds.getMinX() + bounds.getWidth() / 2 - SPLASH_WIDTH / 2);
        initStage.setY(bounds.getMinY() + bounds.getHeight() / 2 - SPLASH_HEIGHT / 2);
        initStage.initStyle(StageStyle.TRANSPARENT);
        initStage.setAlwaysOnTop(true);
        initStage.show();
    }

    public interface InitCompletionHandler {
        void complete();
    }

    public void showLogin(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("View/fxmlFiles/Login.fxml"));
        primaryStage.setTitle("Login in ConspicioAlgo");
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("View/materials.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
