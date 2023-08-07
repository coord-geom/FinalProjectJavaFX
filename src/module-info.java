module CS6132Assignment {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.zxing;
    requires com.google.zxing.javase;
    requires java.desktop;

    opens sample;
    opens sample.Controller;
    opens sample.Model.General;
    opens sample.View;
}