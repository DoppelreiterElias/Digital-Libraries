package tugraz.digitallibraries;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MetadataHandler {

    private List<MetadataEntry> Metadata;

    public MetadataHandler() {

        Metadata = new ArrayList<MetadataEntry>();
    }


    public MetadataEntry AddMetadataEntry(String filepath) {
        return createMetadataEntry(filepath);
    }


    public MetadataEntry AddMultipleMetadataEntry(String[] filepath) {

        MetadataEntry entry = null;

        for (int i = 0; i < filepath.length; i++)
        {
            entry = createMetadataEntry(filepath[i]);
        }
        return entry;
    }


    private MetadataEntry createMetadataEntry(String filepath)
    {
        Document doc;

        try {

            File inputFile = new File(filepath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }


        MetadataEntry entry = new MetadataEntry();

        entry.setFile_path(filepath);

        FencodingDesc_ref(doc, entry);
        Fpaper_title(doc, entry);
        Fpublication_date(doc, entry);
        Fauthor(doc, entry);
        Fidno(doc, entry);
        Fkeywords(doc, entry);
        Fabstract(doc, entry);
        FbiblStruct(doc, entry);

        Metadata.add(entry);

        return entry;
    }


    private void FencodingDesc_ref(Document doc, MetadataEntry entry) {

        try {
            NodeList nList = doc.getElementsByTagName("encodingDesc");
            Node nNode = nList.item(0);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;

               // System.out.println("encodingDesc_ref: " + eElement.getElementsByTagName("ref").item(0).getTextContent());

                entry.setDesc_ref(eElement.getElementsByTagName("ref").item(0).getTextContent());
            }
        }
        catch (Exception e){
                entry.setDesc_ref(null);
        }
    }


    private void Fpaper_title(Document doc, MetadataEntry entry) {

        try {
            NodeList nList = doc.getElementsByTagName("fileDesc");
            Node nNode = nList.item(0);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;

                //System.out.println("paper_title: " + eElement.getElementsByTagName("title").item(0).getTextContent());

                entry.setPaper_title(eElement.getElementsByTagName("title").item(0).getTextContent());
            }
        }catch (Exception e){
            entry.setPaper_title(null);
        }
    }


    private void Fpublication_date(Document doc, MetadataEntry entry) {

        try {
            NodeList nList = doc.getElementsByTagName("publicationStmt");
            Node nNode = nList.item(0);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;

                //System.out.println("publication_date: " + eElement.getElementsByTagName("date").item(0).getTextContent());

                entry.setPublication_date(eElement.getElementsByTagName("date").item(0).getTextContent());
            }
        }catch (Exception e){
            entry.setPublication_date(null);
        }
    }


    private void Fauthor(Document doc, MetadataEntry entry) {

        try {
            NodeList nList = doc.getElementsByTagName("sourceDesc");
            Node nNode = nList.item(0);

            Element eElement = (Element) nNode;
            nList = eElement.getElementsByTagName("author");


            List<Autor> autors = new ArrayList<Autor>();

            for (int temp = 0; temp < nList.getLength(); temp++) {
                nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    eElement = (Element) nNode;

                    Autor autor = new Autor();

                    String[] first_name = new String[eElement.getElementsByTagName("forename").getLength()];
                    for (int i = 0; i < eElement.getElementsByTagName("forename").getLength(); i++) {
                        first_name[i] = eElement.getElementsByTagName("forename").item(i).getTextContent();
                    }

                    /*
                    System.out.print("First Names: ");
                    for (int i = 0; i < first_name.length; i++) {
                       System.out.print(first_name[i] + " ");
                    }
                   System.out.print("\n");
                    */

                    String[] last_name = new String[eElement.getElementsByTagName("surname").getLength()];
                    for (int i = 0; i < eElement.getElementsByTagName("surname").getLength(); i++) {
                        last_name[i] = eElement.getElementsByTagName("surname").item(i).getTextContent();
                    }

                    /*
                    System.out.print("surname Name: ");
                    for (int i = 0; i < last_name.length; i++) {
                        System.out.print(last_name[i] + " ");
                    }
                    System.out.print("\n");
                    */

                    autor.setForenames(first_name);
                    autor.setSurnames(last_name);

                    autors.add(autor);
                }
            }

            entry.setAutors(autors);

        } catch (Exception e) {
            entry.setAutors(null);
        }
    }


    private void Fidno(Document doc, MetadataEntry entry) {

        try {
            NodeList nList = doc.getElementsByTagName("idno");
            Node nNode = nList.item(0);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;

                //System.out.println("idno: " + eElement.getFirstChild().getTextContent());

                entry.setIdno(eElement.getFirstChild().getTextContent());
            }
        } catch (Exception e) {
            entry.setIdno(null);
        }
    }


    private void Fkeywords(Document doc, MetadataEntry entry) {

        try {
            NodeList nList = doc.getElementsByTagName("keywords");

            Node nNode = nList.item(0);
            Element eElement = (Element) nNode;

            nList = eElement.getElementsByTagName("term");

            String[] Keywords = new String[nList.getLength()];

            for (int temp = 0; temp < nList.getLength(); temp++) {
                nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    eElement = (Element) nNode;

                    Keywords[temp] = eElement.getTextContent();
                }
            }

            /*
            System.out.println("Keywords:");
            for (int i = 0; i < Keywords.length; i++) {
                System.out.println(Keywords[i]);
            }
            */

            entry.setKeywords(Keywords);
        }catch (Exception e) {
            entry.setKeywords(null);
        }

    }


    private void Fabstract(Document doc, MetadataEntry entry) {

        try {
            NodeList nList = doc.getElementsByTagName("abstract");
            Node nNode = nList.item(0);

            Element eElement = (Element) nNode;
            nList = eElement.getElementsByTagName("p");
            nNode = nList.item(0);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                eElement = (Element) nNode;

                //System.out.println("Abstract: " + eElement.getFirstChild().getTextContent());

                entry.setPaper_abstract(eElement.getFirstChild().getTextContent());
            }
        }catch (Exception e) {
            entry.setPaper_abstract(null);
        }
    }


    private void FbiblStruct(Document doc, MetadataEntry entry) {

        try {
            NodeList nList = doc.getElementsByTagName("biblStruct");

            List<Reference> references = new ArrayList<Reference>();

            for (int temp = 1; temp < nList.getLength(); temp++) {

                Reference reference = new Reference();


                // Titel of Reference
                Node nNode = nList.item(temp);
                Element eElement = (Element) nNode;

                NodeList nlTitel = eElement.getElementsByTagName("title");
                Node nTitel = nlTitel.item(0);
                Element eTitel = (Element) nTitel;
                //System.out.println("Titel: " + eTitel.getTextContent());

                reference.setTitle(eTitel.getTextContent());

                // Authors of Reference
                NodeList nlAuthor = eElement.getElementsByTagName("author");

                List<Autor> autors = new ArrayList<Autor>();

                for (int iauthor = 0; iauthor < nlAuthor.getLength(); iauthor++) {
                    Node nAuthor = nlAuthor.item(iauthor);
                    Element eAuthor = (Element) nAuthor;

                    Autor autor = new Autor();

                    String[] first_name = new String[eAuthor.getElementsByTagName("forename").getLength()];
                    for (int i = 0; i < eAuthor.getElementsByTagName("forename").getLength(); i++) {
                        first_name[i] = eAuthor.getElementsByTagName("forename").item(i).getTextContent();
                    }

                    /*
                    System.out.print("First Names: ");
                    for (int i = 0; i < first_name.length; i++) {
                        System.out.print(first_name[i] + " ");
                    }
                    System.out.print("\n");
                     */

                    String[] last_name = new String[eAuthor.getElementsByTagName("surname").getLength()];
                    for (int i = 0; i < eAuthor.getElementsByTagName("surname").getLength(); i++) {
                        last_name[i] = eAuthor.getElementsByTagName("surname").item(i).getTextContent();
                    }

                    /*
                    System.out.print("surname Name: ");
                    for (int i = 0; i < last_name.length; i++) {
                        System.out.print(last_name[i] + " ");
                    }
                    System.out.print("\n");
                    */

                    autor.setForenames(first_name);
                    autor.setSurnames(last_name);

                    autors.add(autor);
                }

                reference.setAutors(autors);

                references.add(reference);
            }

            entry.setReferences(references);
        } catch (Exception e) {
            entry.setReferences(null);
        }
    }
}