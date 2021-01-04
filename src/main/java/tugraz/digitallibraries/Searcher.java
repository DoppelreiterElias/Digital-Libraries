package tugraz.digitallibraries;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tugraz.digitallibraries.dataclasses.Author;
import tugraz.digitallibraries.graph.EdgeCoAuthorship;
import tugraz.digitallibraries.graph.GraphCreator;
import tugraz.digitallibraries.ui.DetailViewObject;

import java.util.ArrayList;
import java.util.Collection;

public class Searcher
{
    public ObservableList<DetailViewObject> Search(String keyword, String mode)
    {
        ObservableList<DetailViewObject> test_list = FXCollections.observableArrayList();

        if(mode.equals("Author"))
        {
            ///Todo implement search function - currently hardcoded
            // go through all authors and match them with the levenshtein distance
            Collection<Author> authors = GraphCreator.getInstance().getCitationGraph().getVertices();
            ArrayList<Author> authors_list = new ArrayList<>(authors);
            test_list.add(authors_list.get(0));
            test_list.add(authors_list.get(1));

        }
        else if(mode.equals("Paper"))
        {
            ///Todo implement search function - currently hardcoded, watch out that a paper is only shown once
            // go through all edges and match them with the levenshtein distance
            Collection<EdgeCoAuthorship> edges = GraphCreator.getInstance().getCoAuthorGraph().getEdges();
            ArrayList<EdgeCoAuthorship> edges_list = new ArrayList<>(edges);
            test_list.addAll(edges_list.get(0).getPapers());
            test_list.addAll(edges_list.get(1).getPapers());
        }


        return test_list;
    }
}
