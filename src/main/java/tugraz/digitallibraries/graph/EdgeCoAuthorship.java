package tugraz.digitallibraries.graph;

import javafx.util.Pair;
import tugraz.digitallibraries.dataclasses.Author;
import tugraz.digitallibraries.dataclasses.MetadataEntry;

import java.util.ArrayList;



public class EdgeCoAuthorship {

    public static int counter = 0;

    private int id;
    private ArrayList<MetadataEntry> papers = new ArrayList<>();
    Pair<Author, Author> authors; // don't know if really necessary because Edge already has the pair of authors as attribute


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
        this.papers.add(new_paper);
    }

    public int getWeight() {
        return this.papers.size();
    }

    public ArrayList<MetadataEntry> getPapers() {
        return papers;
    }


}
