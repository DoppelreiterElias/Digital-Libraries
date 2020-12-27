package tugraz.digitallibraries;

import edu.uci.ics.jung.graph.Graph;
import tugraz.digitallibraries.graph.GraphCreator;

import java.io.File;
import java.util.ArrayList;

import static tugraz.digitallibraries.graph.GraphUtils.*;

// static class
public class NetworkCreator {

    private static GraphCreator graphCreator;
    private static MetadataHandler metadataHandler;




    private NetworkCreator() { } // private because a static class

    public static void createNetwork(String subfoldername) {
        ArrayList<String> files = ListAllFilesFromFolder(subfoldername);
        metadataHandler = new MetadataHandler(files);
    }

    public static ArrayList<Graph> createGraphs() {

        ArrayList<Graph> graphs = new ArrayList<>();
        graphCreator = new GraphCreator(metadataHandler.getMetadata());

        // create the graphs
        graphCreator.createCoAuthorGraph();
        graphCreator.createCitationGraph();

        // add the graphs
        graphs.add(graphCreator.getCoAuthorGraph());
        graphs.add(graphCreator.getCitationGraph());

        return graphs;
    }

    public static GraphCreator getGraphCreator() {
        return graphCreator;
    }


    private static ArrayList<String> ListAllFilesFromFolder(String foldername){
        File folder = new File(foldername);
        ArrayList<String> files = new ArrayList<>();
        try
        {
            for(File filename : folder.listFiles()) {
                if(filename.isDirectory()) {
                    files.addAll(ListAllFilesFromFolder(foldername + "/" + filename.getName()));
                }
                else {
                    files.add(foldername + "/" + filename.getName());
                }
            }
        } catch(NullPointerException e)
        {
            e.printStackTrace();
        }


        if(USE_SMALL_DATASET && files.size() >= NR_PAPERS) {
            ArrayList<String> tmp_list = new ArrayList<>();
            for(int i = 0; i < NR_PAPERS; i++) {
                tmp_list.add(files.get(i));
            }

            return tmp_list;
        }
        return files;
    }
}
