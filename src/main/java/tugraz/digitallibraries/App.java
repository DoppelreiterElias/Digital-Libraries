package tugraz.digitallibraries;

import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.PickableVertexPaintTransformer;
import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.commons.collections15.Transformer;
import tugraz.digitallibraries.dataclasses.Author;
import tugraz.digitallibraries.graph.EdgeCoAuthorship;
import tugraz.digitallibraries.graph.GraphCreator;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.io.IOException;
import java.util.ArrayList;

import static tugraz.digitallibraries.graph.GraphUtils.MAX_EDGE_WIDTH;
import static tugraz.digitallibraries.graph.GraphUtils.MAX_VERTEX_SIZE;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    // swing node is a javaFX element, we can insert into swingNode java swing elements, and make them in javafx visible
    final SwingNode swing_node_ = new SwingNode();
    GraphCreator graphCreator;

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
        graphCreator = NetworkCreator.getGraphCreator();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run()
            {

                showGraph(graphs.get(0), swing_node_);
            }
        });
    }



    private void showGraph (final Graph g, SwingNode node)
    {

        Layout<Author, EdgeCoAuthorship> layout = new FRLayout<Author, EdgeCoAuthorship>(g);
        layout.setSize(new Dimension(900, 900));

        VisualizationViewer<Author, EdgeCoAuthorship> vv = new VisualizationViewer<Author, EdgeCoAuthorship>(layout);
        vv.setPreferredSize(new Dimension(950, 950));

        DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
        gm.setMode(ModalGraphMouse.Mode.TRANSFORMING); // TODO: change this to pick vertices
        vv.setGraphMouse(gm);
        vv.setBackground(Color.gray);


        Transformer<Author,Paint> vertexColor = new Transformer<Author,Paint>() {
            public Paint transform(Author i) {
                // TODO: make costum vertex color
                return Color.GREEN;
            }
        };

        Transformer<Author,Shape> vertexSize = new Transformer<Author,Shape>(){
            public Shape transform(Author i){
                Ellipse2D circle = new Ellipse2D.Double(-10, -10, 20, 20);

                // VERTEX SIZE
                int max_size_of_graph = graphCreator.getCoAuthorMaxDegree();
                float scaling = ((float)g.inDegree(i) / max_size_of_graph) * MAX_VERTEX_SIZE;

                scaling = Math.max(scaling, 1);
                return AffineTransform.getScaleInstance(scaling, scaling).createTransformedShape(circle);
            }
        };
        vv.getRenderContext().setVertexFillPaintTransformer(vertexColor);
        vv.getRenderContext().setVertexShapeTransformer(vertexSize);


        Transformer<EdgeCoAuthorship, Stroke> edgeStroke = new Transformer<EdgeCoAuthorship, Stroke>() {
            public Stroke transform(EdgeCoAuthorship s) {
                // EDGE SIZE
                int max_size_of_graph = graphCreator.getCoAuthorMaxEdgeWith();
                float scaling = ((float)s.getWeight() / max_size_of_graph) * MAX_EDGE_WIDTH;
                scaling = Math.max(scaling, 1);
                return new BasicStroke(scaling);
            }
        };
        vv.getRenderContext().setEdgeStrokeTransformer(edgeStroke);

        vv.getRenderContext().setVertexFillPaintTransformer(
            new PickableVertexPaintTransformer<Author>(vv.getPickedVertexState(), Color.green,
                Color.yellow));


        // right click menu
        gm.add(new PopupGraphMousePlugin());


        swing_node_.setContent(vv);
    }


}