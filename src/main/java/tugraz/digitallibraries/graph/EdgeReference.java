package tugraz.digitallibraries.graph;

import javafx.util.Pair;
import tugraz.digitallibraries.dataclasses.Author;
import tugraz.digitallibraries.dataclasses.Reference;

public class EdgeReference {

    private Reference reference;
    Pair<Author, Author> authors; // don't know if really necessary, currently null

    public EdgeReference(Reference ref) {
        this.reference = ref;
    }

    // shallow copy because we want that edges refers to the same object
    public EdgeReference(EdgeReference edge, Author a1, Author a2) {
        this.reference = edge.reference;
        this.authors = new Pair<>(a1, a2);
    }

    // shallow copy because we want that edges refers to the same object
    public EdgeReference(EdgeReference edge) {
        this.reference = edge.reference;
    }

    public void setReference(Reference ref) {
        this.reference = ref;
    }

    public Reference getReference() {
        return this.reference;
    }

}
