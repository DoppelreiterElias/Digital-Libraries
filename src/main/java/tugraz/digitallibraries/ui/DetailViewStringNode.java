package tugraz.digitallibraries.ui;

import javafx.scene.control.TreeItem;

public class DetailViewStringNode extends TreeItem<DetailViewObject>
{
    public DetailViewStringNode(String name)
    {
        super(new DetailViewObject(name, false));
    }

}
