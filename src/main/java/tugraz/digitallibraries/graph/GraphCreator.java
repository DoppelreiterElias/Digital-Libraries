package tugraz.digitallibraries.graph;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import tugraz.digitallibraries.dataclasses.Author;
import tugraz.digitallibraries.dataclasses.MetadataEntry;
import tugraz.digitallibraries.dataclasses.Reference;

import java.util.ArrayList;
import java.util.List;

public class GraphCreator {

    private ArrayList<MetadataEntry> metadata;
    UndirectedSparseGraph<Author, EdgeCoAuthorship> coAuthorGraph;
    DirectedSparseGraph<Author, EdgeCitation> citationGraph;

    public GraphCreator(ArrayList<MetadataEntry> list) {
        coAuthorGraph = new UndirectedSparseGraph<>();
        citationGraph = new DirectedSparseGraph<>();
        this.metadata = list;
    }

    public UndirectedSparseGraph<Author, EdgeCoAuthorship> getCoAuthorGraph() {
        return coAuthorGraph;
    }

    public void setCoAuthorGraph(UndirectedSparseGraph<Author, EdgeCoAuthorship> co_author_graph) {
        this.coAuthorGraph = co_author_graph;
    }

    public DirectedSparseGraph<Author, EdgeCitation> getCitationGraph() {
        return citationGraph;
    }

    public void setCitationGraph(DirectedSparseGraph<Author, EdgeCitation> citation_graph) {
        this.citationGraph = citation_graph;
    }

    public void createCoAuthorGraph() {

        for(MetadataEntry paper : metadata) {

            // create current authors of the paper as vertices
            List<Author> current_authors = paper.getAuthors();
            for(Author author : current_authors) {
                coAuthorGraph.addVertex(author); // only inserts when not already in the graph
            }

            if(current_authors.size() < 2)
                continue;

            // create edges between authors. If a edge already exists between 2 authors the paper will be added to the paperList
            for(int i = 0; i < current_authors.size(); i++) {
                for(int j = i + 1; j < current_authors.size(); j++) {
                    EdgeCoAuthorship existing_edge = coAuthorGraph.findEdge(current_authors.get(i),current_authors.get(j));
                    if(existing_edge != null) // edge between authors already exists
                    {
//                        System.out.println("adding paper to existing edge");
                        existing_edge.addPaperToEdge(paper);
                    }
                    else {
                        EdgeCoAuthorship current_edge = new EdgeCoAuthorship(paper);
                        boolean success = coAuthorGraph.addEdge(current_edge, current_authors.get(i), current_authors.get(j), EdgeType.UNDIRECTED);
                        assert !success;
                    }
                }
            }
            System.out.println("finished paper: " + paper.getFile_path());
        }
        System.out.println("Finished import Co-AuthorGraph");
    }



    public void createCitationGraph() {

        for(MetadataEntry paper : metadata) {

            // create vertices - authors
            List<Author> current_authors = paper.getAuthors();
            for(Author author : current_authors) {
                citationGraph.addVertex(author);
            }

            List<Reference> current_references = paper.getReferences();
            for(Reference reference : current_references) {

                // create all authors of referenced paper
                List<Author> authors_of_referenced_paper = reference.getAutors();
                for(Author author : authors_of_referenced_paper) {
                    citationGraph.addVertex(author);
                }

                // create edges from each Paper Author to each Referenced Author
                for(Author paper_author : current_authors) {
                    for(Author ref_author : authors_of_referenced_paper) {
                        EdgeCitation existing_edge = citationGraph.findEdge(paper_author, ref_author);
                        if(existing_edge != null) { // already exists an edge between these two
//                            System.out.println("adding reference allready exists");
                            existing_edge.addReferenceToEdge(reference);
                        }
                        else {
                            EdgeCitation current_edge = new EdgeCitation(reference);
                            boolean success = citationGraph.addEdge(current_edge, paper_author, ref_author, EdgeType.DIRECTED);
                            assert !success;
                        }
                    }
                }
            }
            System.out.println("finished paper: " + paper.getFile_path());
        }
        System.out.println("Finished import Citation Graph");
    }
}
