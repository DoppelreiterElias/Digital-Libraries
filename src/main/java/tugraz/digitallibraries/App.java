package tugraz.digitallibraries;

import edu.uci.ics.jung.graph.Graph;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tugraz.digitallibraries.graph.GraphUtils;
import tugraz.digitallibraries.graph.GraphVisualizer;
import tugraz.digitallibraries.ui.MainController;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;



    GraphVisualizer graph_visualizer_;
    MainController main_controller_;
    Searcher searcher_ = new Searcher();

    public static final String APP_NAME = new String("Libraliz0r");

    @Override
    public void start(Stage stage) throws IOException {

        scene = new Scene(loadFXML("main_view"), 1000, 666);
        stage.setScene(scene);
        stage.setTitle(APP_NAME);
        stage.show();

        createAndSetSwingContent();

        ///TODO maybe better initialization
        main_controller_.setDependencies(graph_visualizer_, graph_visualizer_, searcher_);
    }

    private Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        Parent scene_loaded = fxmlLoader.load();
        main_controller_ = fxmlLoader.getController();

        return scene_loaded;
    }

    public static void main(String[] args) {

        launch();
    }


    // function to show how to insert swingNode

    private void createAndSetSwingContent()
    {
        graph_visualizer_ = new GraphVisualizer();

        VBox cit_graph_vbox = (VBox)scene.lookup("#cit_graph_view_");
        cit_graph_vbox.getChildren().add(graph_visualizer_.getCitGraphNode());

        VBox co_auth_graph_vbox = (VBox)scene.lookup("#co_auth_graph_view_");
        co_auth_graph_vbox.getChildren().add(graph_visualizer_.getCoAuthGraphNode());


        NetworkCreator.createNetwork("Document and Metadata Collection");
        ArrayList<Graph>  graphs  = NetworkCreator.createGraphs();


        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run()
            {
                main_controller_.showCoAuthorGraph(graphs.get(GraphUtils.GraphType.COAUTHOR_GRAPH.ordinal()));
                main_controller_.showCitationGraph(graphs.get(GraphUtils.GraphType.CITATION_GRAPH.ordinal()));
            }
        });
    }






}