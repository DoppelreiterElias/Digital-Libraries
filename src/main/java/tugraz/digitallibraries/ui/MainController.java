package tugraz.digitallibraries.ui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import tugraz.digitallibraries.dataclasses.Author;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tugraz.digitallibraries.App;
import tugraz.digitallibraries.Searcher;
import tugraz.digitallibraries.graph.GraphVisualizer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable
{

    @FXML
    private VBox cit_graph_view_;

    @FXML
    private VBox co_auth_graph_view_;

    @FXML
    private TreeView cit_graph_detail_;

    @FXML
    private TreeView co_auth_graph_detail_;

    @FXML
    private TreeView search_detail_;

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
    private MenuItem about_show_button_;

    @FXML
    private MenuItem quit_btn_;

    @FXML
    private Button cit_graph_detail_open_;

    @FXML
    private Button co_auth_detail_open_;

    @FXML
    private Button search_detail_open_;

    @FXML
    private Button search_button_;

    @FXML
    private Button search_show_selected_;

    @FXML
    private ListView<DetailViewObject> search_results_;

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

    private GraphVisualizer citation_graph_visualizer_;
    private GraphVisualizer co_author_graph_visualizer_;

    private DetailViewListener cit_graph_detail_listener_;
    private DetailViewListener co_auth_detail_listener_;
    private DetailViewListener search_detail_listener_;


    int selected_tab_index_;

    private Searcher searcher_;

    public void initialize(URL location, ResourceBundle resources)
    {
        cit_graph_detail_.setShowRoot(true);
        cit_graph_detail_listener_ = new DetailViewListener(cit_graph_detail_, cit_graph_detail_open_);
        cit_graph_detail_.getSelectionModel().selectedItemProperty().addListener(cit_graph_detail_listener_);

        co_auth_graph_detail_.setShowRoot(true);
        co_auth_detail_listener_ = new DetailViewListener(co_auth_graph_detail_, co_auth_detail_open_);
        co_auth_graph_detail_.getSelectionModel().selectedItemProperty().addListener(co_auth_detail_listener_);

        search_detail_.setShowRoot(true);
        search_detail_listener_= new DetailViewListener(search_detail_, search_detail_open_);
        search_detail_.getSelectionModel().selectedItemProperty().addListener(search_detail_listener_);


        search_by_choice_.setItems(FXCollections.observableArrayList("Author","Paper"));
        search_by_choice_.setValue("Author");
    }


    public void setDetailNode(DetailViewObject obj)
    {
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                TreeItem<DetailViewObject> detail_name = new TreeItem<>(obj);

                switch(selected_tab_index_)
                {
                    case 0:
                        cit_graph_detail_.setRoot(obj.toDetailTreeview());
                        break;
                    case 1:
                        co_auth_graph_detail_.setRoot(obj.toDetailTreeview());
                        break;
                    case 2:
                        search_detail_.setRoot(obj.toDetailTreeview());
                        break;
                }

            }
        });
    }

    @FXML
    public void citGraphDetailButtonPressed(ActionEvent event)
    {
        setDetailNode(cit_graph_detail_listener_.getCurrentSelection());
    }

    @FXML
    public void coAuthGraphDetailButtonPressed(ActionEvent event)
    {
        setDetailNode(co_auth_detail_listener_.getCurrentSelection());
    }

    @FXML
    public void searchDetailButtonPressed(ActionEvent event)
    {
        setDetailNode(search_detail_listener_.getCurrentSelection());
    }

    @FXML
    public void searchButtonPressed(ActionEvent event)
    {
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                search_results_.setItems(searcher_.Search(search_field_.getText(), (String)search_by_choice_.getValue()));
            }
        });
    }

    @FXML
    public void searchShowSelected(ActionEvent event)
    {
        DetailViewObject selected = search_results_.getSelectionModel().getSelectedItem();

        if(selected != null)
        {
            setDetailNode(selected);
        }
    }

    public void setDependencies(GraphVisualizer cit_vis, GraphVisualizer co_auth_vis, Searcher searcher)
    {
        co_author_graph_visualizer_ = co_auth_vis;
        citation_graph_visualizer_ = cit_vis;
        searcher_ = searcher;
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
            co_author_graph_visualizer_.setToPickingMode();
        }
    }

    @FXML
    void radioTransformModeAction(ActionEvent event)
    {
        if(radio_transform_mode_.isSelected())
        {
            citation_graph_visualizer_.setToTransformingMode();
            co_author_graph_visualizer_.setToTransformingMode();
        }
    }

    @FXML
    void aboutShowClicked(ActionEvent event)
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("about.fxml"));
            Scene scene_loaded = new Scene(fxmlLoader.load());
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setResizable(false);
            stage.setScene(scene_loaded);
            stage.setTitle("About " + App.APP_NAME);
            stage.show();

        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    @FXML
    void tabSelectionChanged(Event event)
    {
        Tab source = (Tab)event.getSource();

        if(source == null || source.getTabPane() == null || source.getTabPane().getSelectionModel() == null)
            return;

        selected_tab_index_ = source.getTabPane().getSelectionModel().getSelectedIndex();
    }

    @FXML
    void quitBtnPressed(ActionEvent event)
    {
        Platform.exit();
    }
}
