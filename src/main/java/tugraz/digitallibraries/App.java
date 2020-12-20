package tugraz.digitallibraries;

import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.w3c.dom.events.EventListener;
import tugraz.digitallibraries.ui.CustomGraphMousePlugin;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    // swing node is a javaFX element, we can insert into swingNode java swing elements, and make them in javafx visible
    final SwingNode swing_node_ = new SwingNode();

    @Override
    public void start(Stage stage) throws IOException {



        // TODO: graph weighting of edges
        // TODO: check if better matching if middlename ignoring
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
        NetworkCreator.createNetwork("Document and Metadata Collection");
        ArrayList<Graph>  graphs  = NetworkCreator.createGraphs();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run()
            {

                showGraph(graphs.get(0), swing_node_);
            }
        });
    }



    private JFrame showGraph (Graph g, SwingNode node)
    {

//        ISOMLayout layout = new ISOMLayout(g);
        FRLayout layout = new FRLayout(g);

        layout.setSize(new

                Dimension(900, 900));
        //        BasicVisualizationServer<Integer,String> vv = new BasicVisualizationServer<Integer,String>(layout);

        VisualizationViewer<Integer, String> vv = new VisualizationViewer<Integer, String>(layout);
        vv.setPreferredSize(new

                Dimension(950, 950));

        //DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
        //gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);
        //vv.setGraphMouse(gm);

        JFrame frame = new JFrame("Simple Graph View");
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        vv.removeMouseListener(vv.getGraphMouse());

        swing_node_.setContent(vv);

        CustomGraphMousePlugin ev_handler = new CustomGraphMousePlugin(vv);

        swing_node_.addEventHandler(MouseEvent.MOUSE_PRESSED , ev_handler);
        swing_node_.addEventHandler(MouseEvent.MOUSE_RELEASED , ev_handler);
        swing_node_.addEventHandler(MouseEvent.MOUSE_DRAGGED , ev_handler);
        //        frame.getContentPane().add(vv);
        //        frame.pack();
        //        frame.setVisible(true);
        return frame;
    }






}