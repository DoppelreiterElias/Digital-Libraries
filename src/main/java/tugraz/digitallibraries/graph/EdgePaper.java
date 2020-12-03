package tugraz.digitallibraries.graph;

import javafx.util.Pair;
import tugraz.digitallibraries.dataclasses.Author;
import tugraz.digitallibraries.dataclasses.MetadataEntry;

public class EdgePaper {

    private MetadataEntry paper;
    Pair<Author, Author> authors; // don't know if really necessary because Edge already has the pair of authors as attribute



    public EdgePaper(MetadataEntry paper) {
        this.paper = paper;
    }

    public EdgePaper() {
        this.paper = null;
    }

    // shallow copy because we want that edges refers to the same object
    public EdgePaper(EdgePaper edge, Author a1, Author a2) {
        this.paper = edge.paper;
        this.authors = new Pair<>(a1, a2);
    }

    // shallow copy because we want that edges refers to the same object
    public EdgePaper(EdgePaper edge) {
        this.paper = edge.paper;
    }

    public void setPaper(MetadataEntry paper) {
        this.paper = paper;
    }

    public MetadataEntry getPaper() {
        return this.paper;
    }

}
