package tugraz.digitallibraries.ui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import tugraz.digitallibraries.dataclasses.Author;
import tugraz.digitallibraries.graph.GraphVisualizer;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable
{

    @FXML
    private VBox cit_graph_view_;

    @FXML
    private TreeView cit_graph_detail_;

    @FXML
    private ChoiceBox search_by_choice_;

    @FXML
    private TextField search_field_;

    @FXML
    private CheckMenuItem check_show_names_;

    @FXML
    private RadioMenuItem radio_picking_mode_;

    @FXML
    private RadioMenuItem radio_transform_mode_;

    @FXML
    private Font x1;

    @FXML
    private Color x2;

    @FXML
    private Font x11;

    @FXML
    private Color x21;

    @FXML
    private Font x111;

    @FXML
    private Color x211;

    @FXML
    private Font x22;

    @FXML
    private Color x23;

    GraphVisualizer citation_graph_visualizer_;
    GraphVisualizer co_author_graph_visualizer_;


    public void initialize(URL location, ResourceBundle resources)
    {
        cit_graph_detail_.setShowRoot(true);
        cit_graph_detail_.getSelectionModel().selectedItemProperty().addListener(new PaperContextMenuListener(cit_graph_detail_));

        search_by_choice_.setItems(FXCollections.observableArrayList("Author","Paper"));
        search_by_choice_.setValue("Author");

    }

    public void setCitGraphDetail(Author auth)
    {
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                cit_graph_detail_.setRoot(auth.toDetailTree());
            }
        });
    }

    public void setDependencies(GraphVisualizer cit_vis, GraphVisualizer co_auth_vis)
    {
        co_author_graph_visualizer_ = co_auth_vis;
        citation_graph_visualizer_ = cit_vis;
    }

    @FXML
    void showNamesAction(ActionEvent event)
    {
        if(check_show_names_.isSelected())
        {
            System.out.println("Showing Edge Labels");

            co_author_graph_visualizer_.showEdgeLabels();
            co_author_graph_visualizer_.showVertexLabels();
            citation_graph_visualizer_.showEdgeLabels();
            citation_graph_visualizer_.showVertexLabels();
        }
        else
        {
            System.out.println("Hiding Edge Labels");

            co_author_graph_visualizer_.hideEdgeLabels();
            co_author_graph_visualizer_.hideVertexLabels();
            citation_graph_visualizer_.hideEdgeLabels();
            citation_graph_visualizer_.hideVertexLabels();

        }
    }

    @FXML
    void radioPickingModeAction(ActionEvent event)
    {
        if(radio_picking_mode_.isSelected())
        {
            citation_graph_visualizer_.setToPickingMode();
        }
    }

    @FXML
    void radioTransformModeAction(ActionEvent event)
    {
        if(radio_transform_mode_.isSelected())
        {
            citation_graph_visualizer_.setToTransformingMode();
        }
    }
}
