package tugraz.digitallibraries;

public class XMLTestMein {

    public static void main(String[] args){

        MetadataHandler handler = new MetadataHandler();

        String file1 = "C:\\Users\\elias\\Desktop\\Document and Metadata Collection\\Vast-2014\\1624_20tvcg12-dang-2346572.pdf.tei.xml";
        String file2 = "C:\\\\Users\\\\elias\\\\Desktop\\\\Document and Metadata Collection\\\\Vast-2009\\\\09_vast_andrienk.pdf.tei.xml";

        String[] files = {
                "C:\\Users\\elias\\Desktop\\Document and Metadata Collection\\Vast-2006\\06_vast_bethel.pdf.tei.xml",
                "C:\\Users\\elias\\Desktop\\Document and Metadata Collection\\Vast-2007\\07_vast_andrienko.pdf.tei.xml",
                "C:\\Users\\elias\\Desktop\\Document and Metadata Collection\\Vast-2008\\08_vast_andrienk.pdf.tei.xml",
                "C:\\Users\\elias\\Desktop\\Document and Metadata Collection\\Vast-2009\\09_vast_andrienk.pdf.tei.xml",
                "C:\\Users\\elias\\Desktop\\Document and Metadata Collection\\Vast-2010\\10_vast_choo.pdf.tei.xml",
                "C:\\Users\\elias\\Desktop\\Document and Metadata Collection\\Vast-2012\\12_vast_cottam.pdf.tei.xml",
                "C:\\Users\\elias\\Desktop\\Document and Metadata Collection\\Vast-2013\\13_vast_ferreira.pdf.tei.xml"};





        handler.AddMetadataEntry(file1);
        handler.AddMetadataEntry(file2);

        handler.AddMultipleMetadataEntry(files);

        System.out.println("Ende");

    }

}
