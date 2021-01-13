package tugraz.digitallibraries;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tugraz.digitallibraries.dataclasses.Author;
import tugraz.digitallibraries.dataclasses.AuthorType;
import tugraz.digitallibraries.graph.EdgeCoAuthorship;
import tugraz.digitallibraries.graph.GraphCreator;
import tugraz.digitallibraries.ui.DetailViewObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Searcher
{
    public ObservableList<DetailViewObject> Search(String keyword, String mode)
    {
        ObservableList<DetailViewObject> test_list = FXCollections.observableArrayList();

        if(mode.equals("Author"))
        {
            ///Todo implement search function - currently hardcoded
            // go through all authors and match them with the levenshtein distance

            // Citation Graph contains all Authors and ReferenceAuthors - would we print both?
            Collection<Author> authors = GraphCreator.getInstance().getCitationGraph().getVertices();
            ArrayList<Author> authors_list = new ArrayList<>(authors);

            String authors_names[] = new String[authors_list.size()];
            int count = 0;

            for(Author a : authors_list) {

                authors_names[count] = a.getFullname();
                count++;
            }

            String similiar_name[] = LevenshteinDistancsClass.getTopTenLevenshteinDistances(keyword, authors_names, 15);

            for(Author a : authors_list) {

                for (int x = 0; x < similiar_name.length; x++)
                {
                    if (a.getFullname() == similiar_name[x])
                    {
                        test_list.add(a);
                    }
                }
            }


        }
        else if(mode.equals("Paper"))
        {
            ///Todo implement search function - currently hardcoded, watch out that a paper is only shown once
            // go through all edges and match them with the levenshtein distance
            Collection<EdgeCoAuthorship> edges = GraphCreator.getInstance().getCoAuthorGraph().getEdges();
            ArrayList<EdgeCoAuthorship> edges_list = new ArrayList<>(edges);

            ArrayList<String> paper_names_list = new ArrayList<>();


            for (int i = 0; i < edges_list.size(); i++)
            {
                String new_paper = edges_list.get(i).getPapers().toString();

                boolean already_in_array = false;

                for(String s : paper_names_list) {

                    if (s.equals(new_paper))
                    {
                        already_in_array = true;
                    }
                }

                if (!already_in_array)
                {
                    paper_names_list.add(new_paper);
                }
            }

            String paper_names[] = new String[paper_names_list.size()];

            int x = 0;
            for (String s : paper_names_list)
            {
                paper_names[x] = s;
                System.out.println(paper_names[x]);

                x++;
            }

            String similiar_papers[] = LevenshteinDistancsClass.getTopTenLevenshteinDistances(keyword, paper_names, 15);

            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("Papers: " + similiar_papers.length);

            for (int i = 0; i < similiar_papers.length; i++)
            {
                System.out.println(similiar_papers[i]);

                // TODO ADD to test_list!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

            }

            test_list.addAll(edges_list.get(0).getPapers());
            test_list.addAll(edges_list.get(1).getPapers());
        }


        return test_list;
    }
}
