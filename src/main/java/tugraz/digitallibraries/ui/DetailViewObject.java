package tugraz.digitallibraries.ui;

import tugraz.digitallibraries.ui.DetailViewStringNode;

public class DetailViewObject
{
    protected String name_;
    private final boolean can_be_opened_;

    public DetailViewObject(String name, boolean can_be_opened)
    {
        can_be_opened_ = can_be_opened;
        name_ = name;
    }

    public boolean canBeOpened() { return can_be_opened_; }

    public void setName(String name) { name_ = name;}

    @Override
    public String toString()
    {
        return name_;
    }

    public DetailViewStringNode toDetailTreeview()
    {
        ///stub for generic case
        return new DetailViewStringNode(name_);
    }
}
