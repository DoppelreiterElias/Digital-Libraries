package tugraz.digitallibraries.ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;


public class DetailViewListener implements ChangeListener<TreeItem<DetailViewObject>>
{
    private TreeView cit_graph_detail_;

    private DetailViewObject cur_selection_ = null;

    private Button open_button_;

    DetailViewListener(TreeView cit_graph, Button open_button)
    {
        super();
        cit_graph_detail_ = cit_graph;
        open_button_ = open_button;
    }


    @Override
    public void changed(ObservableValue<? extends TreeItem<DetailViewObject>> observable,
                        TreeItem<DetailViewObject> old_value, TreeItem<DetailViewObject> new_value)
    {

        cur_selection_ = null;
        open_button_.setDisable(true);

        if(new_value == null)
            return;

        System.out.println("Can be opened: " + new_value.getValue().canBeOpened());

        ///is String if not paper
        if(new_value.getValue().canBeOpened())
        {
            cur_selection_ = new_value.getValue();
            System.out.println("Selected " + new_value.getValue());

            open_button_.setDisable(false);
        }
        System.out.println(new_value.getValue().toString());

        // newValue represents the selected itemTree
    }

    public DetailViewObject getCurrentSelection()
    {
        return cur_selection_;
    }
}
