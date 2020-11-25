module tugraz.digitallibraries {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.xml;

    opens tugraz.digitallibraries to javafx.fxml;
    exports tugraz.digitallibraries;
}