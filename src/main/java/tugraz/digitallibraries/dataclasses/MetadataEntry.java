package tugraz.digitallibraries.dataclasses;

import java.util.List;


// ONE PAPER
public class MetadataEntry {

    private String file_path;

    private String desc_ref;
    private String paper_title;
    private String publication_date;

    private List<Author> authors;

    private String idno;
    private String[] keywords;
    private String paper_abstract;

    private List<Reference> references;

    public MetadataEntry(String file_path, String desc_ref, String paper_title, String publication_date, List<Author> authors, String idno, String[] keywords, String paper_abstract, List<Reference> references){
        this.file_path = file_path;
        this.desc_ref = desc_ref;
        this.paper_title = paper_title;
        this.publication_date = publication_date;
        this.authors = authors;
        this.idno = idno;
        this.keywords = keywords;
        this.paper_abstract = paper_abstract;
        this.references = references;
    }


    public MetadataEntry(){}


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
}
