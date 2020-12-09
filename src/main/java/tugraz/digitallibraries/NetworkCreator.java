package tugraz.digitallibraries;

import edu.uci.ics.jung.graph.Graph;
import tugraz.digitallibraries.graph.GraphCreator;

import java.io.File;
import java.util.ArrayList;


// static class
public class NetworkCreator {

    private static GraphCreator graphCreator;
    private static MetadataHandler metadataHandler;

    private NetworkCreator() { }

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

        boolean USE_SMALL_DATASET = false;

        if(USE_SMALL_DATASET) {
            ArrayList<String> tmp_list = new ArrayList<>();
            tmp_list.add(files.get(1));
            return tmp_list;
        }
        return files;
    }
}
