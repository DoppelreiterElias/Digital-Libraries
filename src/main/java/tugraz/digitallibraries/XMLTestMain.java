package tugraz.digitallibraries;


import edu.uci.ics.jung.graph.UndirectedSparseGraph;

import java.io.File;
import java.util.ArrayList;

public class XMLTestMain {


    public static void main(String[] args){

        if(args.length != 1) {
            System.out.println("ERROR WRONG PARAMETER COUNT");
            System.out.println("Just enter 'Document and Metadata Collection' as path to program argument (in Intellj + " +
                "configure Working Dir) or give the absolute path as argument - we scan the given directory for all files");
            return;
        }



        ArrayList<String> files = ListAllFilesFromFolder(args[0]);
        MetadataHandler handler = new MetadataHandler(files);


//        String file1 = "C:\\Users\\elias\\Desktop\\Document and Metadata Collection\\Vast-2014\\1624_20tvcg12-dang-2346572.pdf.tei.xml";
//        String file2 = "C:\\\\Users\\\\elias\\\\Desktop\\\\Document and Metadata Collection\\\\Vast-2009\\\\09_vast_andrienk.pdf.tei.xml";

//        String[] files = {
//                "C:\\Users\\elias\\Desktop\\Document and Metadata Collection\\Vast-2006\\06_vast_bethel.pdf.tei.xml",
//                "C:\\Users\\elias\\Desktop\\Document and Metadata Collection\\Vast-2007\\07_vast_andrienko.pdf.tei.xml",
//                "C:\\Users\\elias\\Desktop\\Document and Metadata Collection\\Vast-2008\\08_vast_andrienk.pdf.tei.xml",
//                "C:\\Users\\elias\\Desktop\\Document and Metadata Collection\\Vast-2009\\09_vast_andrienk.pdf.tei.xml",
//                "C:\\Users\\elias\\Desktop\\Document and Metadata Collection\\Vast-2010\\10_vast_choo.pdf.tei.xml",
//                "C:\\Users\\elias\\Desktop\\Document and Metadata Collection\\Vast-2012\\12_vast_cottam.pdf.tei.xml",
//                "C:\\Users\\elias\\Desktop\\Document and Metadata Collection\\Vast-2013\\13_vast_ferreira.pdf.tei.xml"};

//        handler.AddMetadataEntry(path);
//        handler.AddMetadataEntry(file2);
//

//        handler.AddMultipleMetadataEntry(files);



        System.out.println("Ende");

    }


    public static ArrayList<String> ListAllFilesFromFolder(String foldername){
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
        return files;
    }


    public static void simpleGraphTest()
    {
        UndirectedSparseGraph<Integer, String> g = new UndirectedSparseGraph<>();

        g.addVertex(0);
        g.addVertex(1);
        g.addVertex(2);

        g.addEdge("0 to 1", 0, 1);
        g.addEdge("0 to 2", 0, 2);
        System.out.println(g);
    }


}
