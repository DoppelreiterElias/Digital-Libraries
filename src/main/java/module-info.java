module tugraz.digitallibraries {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.xml;
    requires jung.graph.impl; //conflicts with jung.api
    requires jung.visualization;
    requires jung.algorithms;
    ///requires jung.api;
    requires jung.io;

    opens tugraz.digitallibraries to javafx.fxml;
    exports tugraz.digitallibraries;
}