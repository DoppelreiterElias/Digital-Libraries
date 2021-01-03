package tugraz.digitallibraries.dataclasses;

import javafx.scene.control.TreeItem;
import tugraz.digitallibraries.ui.DetailViewObject;
import tugraz.digitallibraries.ui.DetailViewStringNode;

import java.util.List;

// a reference/citation in a paper
public class Reference extends DetailViewObject {

    private String title;
    private List<Author> authors;

    public Reference(String title, List<Author> authors) {
        super(title, true);
        this.title = title;
        this.authors = authors;
    }


    public Reference() { super("title", true);}


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
        super.name_ = title;
    }


    public List<Author> getAutors() {
        return authors;
    }


    public void setAutors(List<Author> authors) {
        this.authors = authors;
    }

    @Override
    public DetailViewStringNode toDetailTreeview()
    {
        DetailViewStringNode root = new DetailViewStringNode(title);
        root.setExpanded(true);

        DetailViewStringNode authors_root = new DetailViewStringNode("Authors");
        for(Author cur_author : authors)
            authors_root.getChildren().add(new TreeItem<>(cur_author));

        return root;
    }

}
