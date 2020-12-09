package tugraz.digitallibraries;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tugraz.digitallibraries.dataclasses.Author;
import tugraz.digitallibraries.graph.EdgePaper;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

import javafx.embed.swing.SwingNode;
/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    // swing node is a javaFX element, we can insert into swingNode java swing elements, and make them in javafx visible
    final SwingNode swing_node_ = new SwingNode();

    @Override
    public void start(Stage stage) throws IOException {


//        createAndSetSwingContent(swingNode);

        // TODO: https://stackoverrun.com/de/q/11457654

        scene = new Scene(loadFXML("main_view"), 640, 480);
        stage.setScene(scene);
        stage.show();

        AnchorPane anchor = (AnchorPane)scene.lookup("#graphAnchor");
        anchor.getChildren().add(swing_node_);

        createAndSetSwingContent();
    }

    private Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        Parent scene_loaded = fxmlLoader.load();
        return scene_loaded;
    }

    public static void main(String[] args) {


        launch();
    }



    // function to show how to insert swingNode

    private void createAndSetSwingContent()
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run()
            {
                Graph<Author, EdgePaper> g  = createGraph();

                showGraph(g, swing_node_);
            }
        });
    }

    private Graph<Author, EdgePaper> createGraph() {

//        Graph<String, Number> g = TestGraphs.createTestGraph(true);

        ArrayList<String> files = ListAllFilesFromFolder("Document and Metadata Collection");
        MetadataHandler m = new MetadataHandler(files);
        Graph g = m.getGraphCreator().getCoAuthorGraph();

        Graph<Author, EdgePaper> testingGraph = new SparseGraph<>();
        Author a = new Author();
        Author b = new Author();
        testingGraph.addVertex(a);
        testingGraph.addVertex(b);
        testingGraph.addEdge(new EdgePaper(),a,b);

        return g;
    }


    private JFrame showGraph (Graph g, SwingNode node)
    {
        CircleLayout layout = new CircleLayout(g);


        layout.setSize(new

                Dimension(900, 900));
        //        BasicVisualizationServer<Integer,String> vv = new BasicVisualizationServer<Integer,String>(layout);

        VisualizationViewer<Integer, String> vv = new VisualizationViewer<Integer, String>(layout);
        vv.setPreferredSize(new

                Dimension(950, 950));

        DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
        gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);
        vv.setGraphMouse(gm);

        JFrame frame = new JFrame("Simple Graph View");
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        swing_node_.setContent(vv);

        //        frame.getContentPane().add(vv);
        //        frame.pack();
        //        frame.setVisible(true);
        return frame;
    }

    public static ArrayList<String> ListAllFilesFromFolder(String foldername){
        File folder = new File(foldername);
        ArrayList<String> files = new ArrayList<>();
        try
        {
            for(File filename : folder.listFiles()) {
                if(filename.isDirectory()) {
                    files.addAll(ListAllFilesFromFolder(foldername + "/" + filename.getName()));
                }
                else {
                    files.add(foldername + "/" + filename.getName());
                }
            }
        } catch(NullPointerException e)
        {
            e.printStackTrace();
        }
        return files;
    }



}