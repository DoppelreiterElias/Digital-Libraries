package tugraz.digitallibraries.dataclasses;

import javafx.scene.control.TreeItem;
import tugraz.digitallibraries.ui.DetailViewObject;
import tugraz.digitallibraries.ui.DetailViewStringNode;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import java.util.ArrayList;


// ONE PAPER
public class MetadataEntry extends DetailViewObject
{

    private String file_path;
    private String pdf_path;

    private String desc_ref;
    private String paper_title;
    private String publication_date;

    private List<Author> authors = new ArrayList<Author>();
    private String idno;
    private String[] keywords = new String[]{};
    private String paper_abstract;

    private List<Reference> references;

    public MetadataEntry(String file_path, String pdf_path, String desc_ref, String paper_title, String publication_date, List<Author> authors, String idno, String[] keywords, String paper_abstract, List<Reference> references){
        super(paper_title, true);

        this.file_path = file_path;
        this.pdf_path = pdf_path;
        this.desc_ref = desc_ref;
        this.paper_title = paper_title;
        this.publication_date = publication_date;
        this.authors = authors;
        this.idno = idno;
        this.keywords = keywords;
        this.paper_abstract = paper_abstract;
        this.references = references;
    }


    public MetadataEntry(){super("", true);
    }


    public String getDesc_ref() {
        return desc_ref;
    }


    public void setDesc_ref(String desc_ref) {
        this.desc_ref = desc_ref;
    }


    public String getPaper_title() {
        return paper_title;
    }


    public void setPaper_title(String paper_title) {
        this.paper_title = paper_title;
        this.name_ = paper_title;
    }


    public String getPublication_date() {
        return publication_date;
    }


    public void setPublication_date(String publication_date) {
        this.publication_date = publication_date;
    }


    public String getIdno() {
        return idno;
    }


    public void setIdno(String idno) {
        this.idno = idno;
    }


    public String[] getKeywords() {
        return keywords;
    }


    public void setKeywords(String[] keywords) {
        this.keywords = keywords;
    }


    public String getPaper_abstract() {
        return paper_abstract;
    }


    public void setPaper_abstract(String paper_abstract) {
        this.paper_abstract = paper_abstract;
    }


    public List<Author> getAuthors() {
        return authors;
    }


    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }


    public List<Reference> getReferences() {
        return references;
    }


    public void setReferences(List<Reference> references) {
        this.references = references;
    }


    public String getFile_path() {
        return file_path;
    }


    public void setFile_path(String file_name) {
        this.file_path = file_name;
    }

    public String getPdf_path() { return pdf_path;}

    public void SetPdf_path(String pdf_name) {
        this.pdf_path = pdf_name;
    }

    @Override
    public String toString()
    {
        return this.paper_title;
    }

    @Override
    public DetailViewStringNode toDetailTreeview()
    {
        DetailViewStringNode root = new DetailViewStringNode(paper_title);
        root.setExpanded(true);

        DetailViewStringNode authors_root = new DetailViewStringNode("Authors");
        for(Author cur_author : authors)
            authors_root.getChildren().add(new TreeItem<>(cur_author));

        DetailViewStringNode keywords_root = new DetailViewStringNode("Keywords");
        for(String act_keyword : keywords)
            keywords_root.getChildren().add(new DetailViewStringNode(act_keyword));


        DetailViewStringNode publication_date_root = new DetailViewStringNode("Publication Date");
        publication_date_root.getChildren().add(new DetailViewStringNode(publication_date));

        root.getChildren().addAll(authors_root, keywords_root, publication_date_root);

        return root;
    }

    public void openPdfInBrowser() {
        // TODO: implementation failing. on linux/ubuntu does not work

        String os = System.getProperty("os.name");
        try {
            if (os.contains("Linux")) {
                // TODO implement
                System.out.println("Browse URL currently not implemented for linux");
            } else {
                if (Desktop.isDesktopSupported())
                {
                    Desktop.getDesktop().browse(new URI(pdf_path));
                } else {
                    System.out.println("Browse URL xdg-open not supported!");
                }
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
