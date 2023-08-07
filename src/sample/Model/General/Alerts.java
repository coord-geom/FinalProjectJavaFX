package sample.Model.General;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import java.util.Optional;

public class Alerts {

    private static Alert alert;
    private static Label label;

    public Alerts(){

    }

    public static void errorAlert(String header, String content){
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error!");
        alert.setHeaderText(header);
        label = new Label(content);
        label.setWrapText(true);
        alert.getDialogPane().setContent(label);
        alert.show();
    }

    public static void errorAlert(String header){
        errorAlert(header,"");
    }

    public static void warningAlert(String header, String content){
        alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning!");
        alert.setHeaderText(header);
        label = new Label(content);
        label.setWrapText(true);
        alert.getDialogPane().setContent(label);
        alert.show();
    }

    public static void warningAlert(String header){
        warningAlert(header,"");
    }

    public static boolean confirmAlert(String header, String content){
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(header);
        alert.setContentText(content);
        Optional<ButtonType> opt = alert.showAndWait();
        return opt.get() == ButtonType.OK;
    }

    public static boolean confirmAlert(String header){
        return confirmAlert(header, "");
    }

    public static void successAlert(String header, String content){
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success!");
        alert.setHeaderText(header);
        label = new Label(content);
        label.setWrapText(true);
        alert.getDialogPane().setContent(label);
        alert.show();
    }

    public static void successAlert(String header){ successAlert(header, ""); }

    public static void infoAlert(String header, String content){
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(header);
        label = new Label(content);
        label.setWrapText(true);
        alert.getDialogPane().setContent(label);
        alert.show();
    }

    public static void infoAlert(String header){ successAlert(header, ""); }
}
