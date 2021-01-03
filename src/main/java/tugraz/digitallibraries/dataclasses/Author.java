package tugraz.digitallibraries.dataclasses;


import javafx.scene.control.TreeItem;
import tugraz.digitallibraries.graph.EdgeCoAuthorship;
import tugraz.digitallibraries.graph.GraphCreator;

import java.util.ArrayList;
import java.util.Collection;

// Author of a paper
public class Author {

    private static int counter = 0;
    private int id;
    private String[] forenames;
    private String[] surnames;
    private String fullname;
    AuthorType authorType;


    public Author(String[] forenames, String[] surnames)
    {
        this.id = counter++;
        this.forenames = forenames;
        this.surnames = surnames;
        createFullName();
    }

    public Author(String[] forenames, String[] surnames, AuthorType type) {
        this.id = counter++;
        this.forenames = forenames;
        this.surnames = surnames;
        this.authorType = type;
        createFullName();
    }


    public Author(){
        this.id = counter++;
    }

    public Author(AuthorType type) {
        this.id = counter++;
        this.authorType = type;
        createFullName();
    }

    public String[] getForenames() {
        return forenames;
    }


    public void setForenames(String[] forenames) {
        this.forenames = forenames;
    }


    public String[] getSurnames() {
        return surnames;
    }


    public void setSurnames(String[] surnames) {
        this.surnames = surnames;
    }

    public void setAuthorType(AuthorType type) {
        this.authorType = type;
    }

    public AuthorType getAuthorType() {
        return this.authorType;
    }

    public int getId() {return this.id; }

    public String getFullname() {
        return this.fullname;
    }

    public String getSurname() {
        return String.join(" ", this.surnames);
    }

    public void createFullName() {
        if(forenames == null || surnames == null)
        {
            this.fullname = new String("");
            return;
        }
        String fornames = String.join(" ", this.forenames);
        String lastnames = String.join(" ", this.surnames);

        this.fullname = new String(String.join(",", fornames, lastnames));
    }

    @Override
    public String toString() {
        return fullname;
    }

    public TreeItem<String> toDetailTree()
    {
        TreeItem<String> root = new TreeItem<>(getFullname());
        root.setExpanded(true);

        TreeItem<String> fornames = new TreeItem<>("Fornames");
        fornames.getChildren().add(new TreeItem<String>(String.join(" ", this.forenames)));

        TreeItem<String> surnames = new TreeItem<>("Surnames");
        surnames.getChildren().add(new TreeItem<String>(String.join(" ", this.surnames)));

        TreeItem papers_root = new TreeItem<String>("Papers of Author");

        for(MetadataEntry cur_paper : getPapersOfAuthor())
        {
            TreeItem new_paper = new TreeItem<MetadataEntry>(cur_paper);
//            new_paper.addEventHandler(ContextMenuEvent.CONTEXT_MENU_REQUESTED, new PaperContextMenuListener());
            papers_root.getChildren().add(new_paper);
        }

        ///Testcode for Paper handler
//        MetadataEntry test_paper = new MetadataEntry();
//        test_paper.setPaper_title("TestPaper");
//        TreeItem new_paper = new TreeItem<MetadataEntry>(test_paper);
//        papers_root.getChildren().add(new_paper);
        ////

        TreeItem co_authors_root = new TreeItem("Co-Authors");

        for(Author cur_co_author : getCoAuthors())
        {
            co_authors_root.getChildren().add(new TreeItem(cur_co_author));
        }

        TreeItem sources_root = new TreeItem("Sources of Author");

        for(MetadataEntry cur_paper : getSources())
        {
            sources_root.getChildren().add(new TreeItem<MetadataEntry>(cur_paper));
            ///Todo Register Event Handler for Paper
        }


        root.getChildren().addAll(fornames, surnames, papers_root, co_authors_root, sources_root);

        return root;
    }

    public ArrayList<MetadataEntry> getPapersOfAuthor()
    {
        Collection<EdgeCoAuthorship> edges = GraphCreator.getInstance().getCoAuthorGraph().getOutEdges(this);
        ArrayList<MetadataEntry> papers = new ArrayList<>();
        for (EdgeCoAuthorship edge : edges) {
            papers.addAll(edge.getPapers());
        }
        return papers;
    }

    public ArrayList<Author> getCoAuthors()
    {
        Collection<Author> neighbours = GraphCreator.getInstance().getCoAuthorGraph().getNeighbors(this);
        return new ArrayList<>(neighbours);
    }

    public ArrayList<MetadataEntry> getSources()
    {
        ///Todo implement
        return new ArrayList<>();
    }

}
