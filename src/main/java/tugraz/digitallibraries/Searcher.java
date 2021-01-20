package tugraz.digitallibraries;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tugraz.digitallibraries.dataclasses.Author;
import tugraz.digitallibraries.dataclasses.MetadataEntry;
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
            Collection<Author> authors = GraphCreator.getInstance().getCitationGraph().getVertices();
            ArrayList<Author> authors_list = new ArrayList<>(authors);

            String authors_names[] = new String[authors_list.size()];
            int count = 0;

            for(Author a : authors_list) {

                authors_names[count] = a.getSurname();
                count++;
            }

            String similiar_name[] = LevenshteinDistancsClass.getTopTenLevenshteinDistances(keyword, authors_names, 15);


            for (int x = 0; x < similiar_name.length; x++)
            {
                for(Author a : authors_list) {
                    if (a.getSurname() == similiar_name[x])
                    {
                        test_list.add(a);
                    }
                }

            }

        }
        else if(mode.equals("Paper")) {

            Collection<EdgeCoAuthorship> edges = GraphCreator.getInstance().getCoAuthorGraph().getEdges();
            ArrayList<EdgeCoAuthorship> edges_list = new ArrayList<>(edges);

            test_list = LevenshteinDistancsClass.paperTitelLevenshteinDistances(keyword, edges_list, 20);

        }

        return test_list;
    }
}
