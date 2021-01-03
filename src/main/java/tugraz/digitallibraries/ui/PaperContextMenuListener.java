package tugraz.digitallibraries.ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.robot.Robot;
import tugraz.digitallibraries.dataclasses.MetadataEntry;


public class PaperContextMenuListener implements ChangeListener<TreeItem<MetadataEntry>>
{
    PaperContextMenuListener(TreeView cit_graph)
    {
        super();
        cit_graph_detail_ = cit_graph;
    }
    TreeView cit_graph_detail_;

    MetadataEntry cur_paper_ = null;

    ContextMenu cur_context_menu_ = null;

    @Override
    public void changed(ObservableValue<? extends TreeItem<MetadataEntry>> observable,
                        TreeItem<MetadataEntry> old_value, TreeItem<MetadataEntry> new_value)
    {
        //close old context menu if exists
        if(cur_context_menu_ != null)
            cur_context_menu_.hide();

        if(new_value == null)
            return;

        ///is String if not paper
        if(new_value.getValue() instanceof MetadataEntry)
        {
            cur_paper_ = new_value.getValue();

            // Create ContextMenu
            cur_context_menu_ = new ContextMenu();

            MenuItem item1 = new MenuItem("Open Paper");
            item1.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent event)
                {
                    ///Todo actually open paper
                    System.out.println("Opening " + cur_paper_.getPaper_title());
                    cur_paper_.openPdfInBrowser();

                }
            });

            cur_context_menu_.getItems().add(item1);

            Robot robot = new Robot();

            cur_context_menu_.show(cit_graph_detail_,robot.getMouseX(), robot.getMouseY() );
        }
        //System.out.println(newValue.getClass());

        // newValue represents the selected itemTree
    }
}
