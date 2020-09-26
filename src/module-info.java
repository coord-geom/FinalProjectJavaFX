module FinalProjectJavaFX {
    requires javafx.controls;
    requires javafx.fxml;
    opens sample;
    opens sample.Controller;
    opens sample.Model;
    opens sample.View;
}