package tugraz.digitallibraries.graph;

public class GraphUtils {

    public static final int MAX_EDGE_WIDTH = 8;
    public static final int MAX_VERTEX_SIZE = 3;

    //--------------------------------------------------------------------------------------------------
    // only to import a smaller dataset
    public static final boolean USE_SMALL_DATASET = false;
    public static int NR_PAPERS = 10; // nr paper to import - only if USE_SMALL_DATASET = true
    //--------------------------------------------------------------------------------------------------

    public enum GraphType {COAUTHOR_GRAPH, CITATION_GRAPH};
}
