package tugraz.digitallibraries.graph;

import edu.uci.ics.jung.algorithms.filters.Filter;
import edu.uci.ics.jung.algorithms.filters.KNeighborhoodFilter;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import javafx.util.Pair;
import tugraz.digitallibraries.dataclasses.Author;
import tugraz.digitallibraries.dataclasses.MetadataEntry;
import tugraz.digitallibraries.dataclasses.Reference;

import java.util.ArrayList;
import java.util.List;



public class GraphCreator {

    private static GraphCreator instance;
    private ArrayList<MetadataEntry> metadata;
    UndirectedSparseGraph<Author, EdgeCoAuthorship> coAuthorGraph;
    private int coAuthorMaxDegree = 0;
    private int coAuthorMaxEdgeWith = 1;

    DirectedSparseGraph<Author, EdgeCitation> citationGraph;
    private int citationMaxDegree = 0;
    private int citationMaxEdgeWith = 1;

    public static GraphCreator getInstance() {
        if(GraphCreator.instance == null) {
            GraphCreator.instance = new GraphCreator();
            GraphCreator.instance.coAuthorGraph = new UndirectedSparseGraph<>();
            GraphCreator.instance.citationGraph = new DirectedSparseGraph<>();
        }

        return GraphCreator.instance;
    }

    private GraphCreator() {   }

    public void initializeList(ArrayList<MetadataEntry> list) {
        this.metadata = list;
    }



    public int getCoAuthorMaxDegree() {
        return this.coAuthorMaxDegree;
    }

    public int getCitationMaxDegree() {
        return this.citationMaxDegree;
    }

    public int getCoAuthorMaxEdgeWith() {
        return this.coAuthorMaxEdgeWith;
    }

    public int getCitationMaxEdgeWith() {
        return this.citationMaxEdgeWith;
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
                //if(current_authors.get(i).getForenames().length == 0 && current_authors.get(i).getSurnames().length == 0)
                  //  System.out.println("no name here");
                for(int j = i + 1; j < current_authors.size(); j++) {
                    EdgeCoAuthorship existing_edge = coAuthorGraph.findEdge(current_authors.get(i),current_authors.get(j));
                    if(existing_edge != null) // edge between authors already exists
                    {
//                        System.out.println("adding paper to existing edge");
                        existing_edge.addPaperToEdge(paper);
                        if(coAuthorMaxEdgeWith < existing_edge.getWeight())
                            coAuthorMaxEdgeWith = existing_edge.getWeight();
                        assert existing_edge.authors.getKey() == current_authors.get(i) || existing_edge.authors.getValue() == current_authors.get(i);
                        assert existing_edge.authors.getKey() == current_authors.get(j) || existing_edge.authors.getValue() == current_authors.get(j);
                    }
                    else {
                        EdgeCoAuthorship current_edge = new EdgeCoAuthorship(paper);
                        current_edge.authors = new Pair<>(current_authors.get(i),current_authors.get(j));
                        boolean success = coAuthorGraph.addEdge(current_edge, current_authors.get(i), current_authors.get(j), EdgeType.UNDIRECTED);
//                        assert !success;
                        int current_degree_i = coAuthorGraph.inDegree(current_authors.get(i));
                        int current_degree_j = coAuthorGraph.inDegree(current_authors.get(i));
                        if(coAuthorMaxDegree < current_degree_i)
                            coAuthorMaxDegree = current_degree_i;
                        if(coAuthorMaxDegree < current_degree_j)
                            coAuthorMaxDegree = current_degree_j;
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
//                            System.out.println("adding reference already exists");
                            existing_edge.addReferenceToEdge(reference);
                            if(citationMaxEdgeWith < existing_edge.getWeight())
                                citationMaxEdgeWith = existing_edge.getWeight();
                        }
                        else {
                            EdgeCitation current_edge = new EdgeCitation(reference);
                            boolean success = citationGraph.addEdge(current_edge, paper_author, ref_author, EdgeType.DIRECTED);
//                            assert !success;
                            int current_degree_i = citationGraph.inDegree(paper_author);
                            int current_degree_j = citationGraph.inDegree(ref_author);
                            if(citationMaxDegree < current_degree_i)
                                citationMaxDegree = current_degree_i;
                            if(citationMaxDegree < current_degree_j)
                                citationMaxDegree = current_degree_j;
                        }
                    }
                }
            }
            System.out.println("finished paper: " + paper.getFile_path());
        }
        System.out.println("Finished import Citation Graph");
    }

    public Graph createSubgraph(GraphUtils.GraphType type, Author vertex) {

        if(type == GraphUtils.GraphType.COAUTHOR_GRAPH) {
            Filter<Author, EdgeCoAuthorship> filter = new KNeighborhoodFilter<>(vertex, 1, KNeighborhoodFilter.EdgeType.IN_OUT);
            return filter.transform(coAuthorGraph);
        }
        else
        {
            Filter<Author, EdgeCitation> filter = new KNeighborhoodFilter<>(vertex, 1, KNeighborhoodFilter.EdgeType.IN_OUT);
            return filter.transform(citationGraph);
        }
    }

    public EdgeCoAuthorship getEdgeFromPaper(MetadataEntry paper_to_look) {

        for(EdgeCoAuthorship edge : coAuthorGraph.getEdges()) {

            for(MetadataEntry paper : edge.getPapers()) {
                if(paper_to_look == paper)
                    return edge;
            }
        }
        return null;
    }
}
