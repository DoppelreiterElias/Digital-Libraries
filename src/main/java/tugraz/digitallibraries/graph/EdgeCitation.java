package tugraz.digitallibraries.graph;

import javafx.util.Pair;
import tugraz.digitallibraries.dataclasses.Author;
import tugraz.digitallibraries.dataclasses.Reference;

import java.util.ArrayList;

public class EdgeCitation {

    public static int counter = 0;
    private int id;
    private ArrayList<Reference> references = new ArrayList<>();
    Pair<Author, Author> authors; // don't know if really necessary, currently null

    public EdgeCitation(Reference ref) {
        this.references.add(ref);
        initEdge();
    }

    private void initEdge() {
        this.id = counter++;
    }

    public int getId() {
        return this.id;
    }

    public int getWeight() {
        return this.references.size();
    }

    public void addReferenceToEdge(Reference new_ref) {
        references.add(new_ref);
    }




    // shallow copy because we want that edges refers to the same object
    //    public EdgeCitation(EdgeCitation edge) {
    //        this.reference = edge.reference;
    //    }

    // shallow copy because we want that edges refers to the same object
    //    public EdgeCitation(EdgeCitation edge, Author a1, Author a2) {
    //        this.reference = edge.reference;
    //        this.authors = new Pair<>(a1, a2);
    //    }
}
