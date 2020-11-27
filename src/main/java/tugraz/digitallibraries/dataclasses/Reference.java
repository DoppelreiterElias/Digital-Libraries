package tugraz.digitallibraries.dataclasses;

import java.util.List;

// a reference/citation in a paper
public class Reference {

    private String title;
    private List<Author> authors;

    public Reference(String title, List<Author> authors) {
        this.title = title;
        this.authors = authors;
    }


    public Reference() {}


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public List<Author> getAutors() {
        return authors;
    }


    public void setAutors(List<Author> authors) {
        this.authors = authors;
    }
}
