package tugraz.digitallibraries;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tugraz.digitallibraries.dataclasses.Author;
import tugraz.digitallibraries.dataclasses.MetadataEntry;
import tugraz.digitallibraries.ui.DetailViewObject;

import static javafx.collections.FXCollections.observableArrayList;

public class Searcher
{
    public ObservableList<DetailViewObject> Search(String keyword, String mode)
    {
        ObservableList<DetailViewObject> test_list = FXCollections.observableArrayList();

        if(mode.equals("Author"))
        {
            ///Todo implement
            Author test_author = new Author(new String[]{"Test"}, new String[]{"Author"});
            test_list.add(test_author);

        }
        else if(mode.equals("Paper"))
        {
            ///Todo implement

            MetadataEntry test_paper = new MetadataEntry();
            test_paper.setPaper_title("TestPaper");
            test_list.add(test_paper);
        }


        return test_list;
    }
}
