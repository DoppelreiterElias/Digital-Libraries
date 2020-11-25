package tugraz.digitallibraries;

import java.util.List;

public class Reference {

    private String title;
    private List<Autor> autors;

    public Reference(String title, List<Autor> autors) {
        this.title = title;
        this.autors = autors;
    }


    public Reference() {}


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public List<Autor> getAutors() {
        return autors;
    }


    public void setAutors(List<Autor> autors) {
        this.autors = autors;
    }
}
