module com.example.lab3ui {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;

    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires org.json;

    opens com.example.lab3ui to javafx.fxml;
    exports com.example.lab3ui;
}