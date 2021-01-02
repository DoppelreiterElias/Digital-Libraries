package tugraz.digitallibraries;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tugraz.digitallibraries.dataclasses.Author;
import tugraz.digitallibraries.graph.EdgeCitation;
import tugraz.digitallibraries.graph.EdgeCoAuthorship;
import tugraz.digitallibraries.graph.GraphCreator;
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

    // swing node is a javaFX element, we can insert into swingNode java swing elements, and make them in javafx visible
    final SwingNode cit_graph_node_ = new SwingNode();
    final SwingNode co_auth_graph_node_ = new SwingNode();

    GraphCreator graphCreator;
    GraphVisualizer graphVisualizer;
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
        main_controller_.setDependencies(graphVisualizer, graphVisualizer, searcher_);
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
        VBox cit_graph_vbox = (VBox)scene.lookup("#cit_graph_view_");
        cit_graph_vbox.getChildren().add(cit_graph_node_);

        VBox co_auth_graph_vbox = (VBox)scene.lookup("#co_auth_graph_view_");
        co_auth_graph_vbox.getChildren().add(cit_graph_node_);


        NetworkCreator.createNetwork("Document and Metadata Collection");
        ArrayList<Graph>  graphs  = NetworkCreator.createGraphs();
        graphCreator = NetworkCreator.getGraphCreator();
        graphVisualizer = new GraphVisualizer(graphCreator);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run()
            {
                showCoAuthorGraph(graphs.get(0));
//                showCitationGraph(graphs.get(0));
            }
        });
    }



    private void showCoAuthorGraph(final Graph g)
    {
        VisualizationViewer<Author, EdgeCoAuthorship> vv = graphVisualizer.createCoAuthorVisualizer(g, main_controller_);
        cit_graph_node_.setContent(vv);
    }

    private void showCitationGraph(final Graph g)
    {
        // todo make citation graph similar to coauthor graph
        VisualizationViewer<Author, EdgeCitation> vv = graphVisualizer.createCitationVisualizer(g);
        co_auth_graph_node_.setContent(vv);
    }


}