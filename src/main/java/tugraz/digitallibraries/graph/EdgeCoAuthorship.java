package tugraz.digitallibraries.graph;

import javafx.util.Pair;
import tugraz.digitallibraries.dataclasses.Author;
import tugraz.digitallibraries.dataclasses.MetadataEntry;

import java.util.ArrayList;



public class EdgeCoAuthorship extends AbstractEdge {

    public static int counter = 0;

    private int id;
    private ArrayList<MetadataEntry> papers = new ArrayList<>();
    Pair<Author, Author> authors;


    public EdgeCoAuthorship(MetadataEntry paper) {
        initEdge();
        addPaperToEdge(paper);
    }

    public EdgeCoAuthorship() {
        initEdge();
    }

    private void initEdge() {
        this.id = counter++;
    }

    private int getId() {
        return this.id;
    }

    public void addPaperToEdge(MetadataEntry new_paper) {
        boolean already_exist = false;
        for(MetadataEntry cur : papers) {
            if(cur.getPaper_title().equals(new_paper.getPaper_title()))
                already_exist = true;
        }
        if(!already_exist)
            papers.add(new_paper);
    }

    public int getWeight() {
        return this.papers.size();
    }

    public ArrayList<MetadataEntry> getPapers() {
        return papers;
    }


    @Override
    public String toString() {
        return new String(authors.getKey().getSurname() + "<->" + authors.getValue().getSurname());
    }

}
