package tugraz.digitallibraries;

import edu.uci.ics.jung.graph.UndirectedSparseMultigraph;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();

    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {

        UndirectedSparseMultigraph<Integer, String> g = new UndirectedSparseMultigraph<>();

        g.addVertex(1);
        g.addVertex(2);
        g.addEdge("helo", 1,2);
        System.out.println("starting");


        launch();
    }



}