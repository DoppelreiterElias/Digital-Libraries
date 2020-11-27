package tugraz.digitallibraries.graph;


import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.UndirectedSparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import tugraz.digitallibraries.dataclasses.Author;
import tugraz.digitallibraries.dataclasses.MetadataEntry;
import tugraz.digitallibraries.dataclasses.Reference;

import java.util.ArrayList;
import java.util.List;

public class GraphCreator {


    UndirectedSparseMultigraph<Author, EdgePaper> co_author_graph;
    DirectedSparseGraph<Author, EdgeReference> citation_graph;

    public GraphCreator() {

        co_author_graph = new UndirectedSparseMultigraph<>();
        citation_graph = new DirectedSparseGraph<>();
    }

    public GraphCreator(ArrayList<MetadataEntry> list) {
        co_author_graph = new UndirectedSparseMultigraph<>();
        citation_graph = new DirectedSparseGraph<>();

        createCoAuthorGraph(list);
        createCitationGraph(list);
    }

    public UndirectedSparseMultigraph<Author, EdgePaper> getCoAuthorGraph() {
        return co_author_graph;
    }

    public void setCoAuthorGraph(UndirectedSparseMultigraph<Author, EdgePaper> co_author_graph) {
        this.co_author_graph = co_author_graph;
    }

    public DirectedSparseGraph<Author, EdgeReference> getCitationGraph() {
        return citation_graph;
    }

    public void setCitationGraph(DirectedSparseGraph<Author, EdgeReference> citation_graph) {
        this.citation_graph = citation_graph;
    }

    public void createCoAuthorGraph(ArrayList<MetadataEntry> list) {

        for(MetadataEntry paper : list) {

            // create current authors of the paper as vertices
            List<Author> current_authors = paper.getAuthors();
            for(Author author : current_authors) {
                co_author_graph.addVertex(author); // only inserts when not already in the graph
            }

            if(current_authors.size() < 2)
                continue;

            // create edges between all authors of this paper
            EdgePaper current_edge = new EdgePaper(paper);

            for(int i = 0; i < current_authors.size(); i++) {
                for(int j = i + 1; j < current_authors.size(); j++) {
                    co_author_graph.addEdge(current_edge, current_authors.get(i), current_authors.get(j));
                    current_edge = new EdgePaper(current_edge);
                }
            }
            System.out.println("finished paper: " + paper.getFile_path());
        }
        System.out.println("Finished import Co-AuthorGraph");
    }



    public void createCitationGraph(ArrayList<MetadataEntry> list) {

        for(MetadataEntry paper : list) {

            // create vertices - authors
            List<Author> current_authors = paper.getAuthors();
            for(Author author : current_authors) {
                citation_graph.addVertex(author);
            }

            List<Reference> current_references = paper.getReferences();
            for(Reference reference : current_references) {

                // create all authors of referenced paper
                List<Author> authors_of_referenced_paper = reference.getAutors();
                for(Author author : authors_of_referenced_paper) {
                    citation_graph.addVertex(author);
                }

                // create edges from each Paper Author to each Referenced Author
                EdgeReference current_edge = new EdgeReference(reference);
                for(Author paper_author : current_authors) {
                    for(Author ref_author : authors_of_referenced_paper) {
                        citation_graph.addEdge(current_edge, paper_author, ref_author, EdgeType.DIRECTED);
                        current_edge = new EdgeReference(current_edge);
                    }
                }
            }
            System.out.println("finished paper: " + paper.getFile_path());
        }
        System.out.println("Finished import Citation Graph");
    }
}
