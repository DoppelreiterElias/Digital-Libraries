package tugraz.digitallibraries.dataclasses;


import javafx.scene.control.TreeItem;
import tugraz.digitallibraries.graph.EdgeCitation;
import tugraz.digitallibraries.ui.DetailViewObject;
import tugraz.digitallibraries.ui.DetailViewStringNode;
import tugraz.digitallibraries.graph.EdgeCoAuthorship;
import tugraz.digitallibraries.graph.GraphCreator;

import java.util.ArrayList;
import java.util.Collection;

// Author of a paper
public class Author extends DetailViewObject
{

    private static int counter = 0;
    private int id;
    private String[] forenames;
    private String[] surnames;
    private String fullname;
    AuthorType authorType;

    public Author(String[] forenames, String[] surnames)
    {
        super(String.join(" ", forenames) + String.join(" ", surnames), true);
        this.id = counter++;
        this.forenames = forenames;
        this.surnames = surnames;
        createFullName();
    }

    public Author(String[] forenames, String[] surnames, AuthorType type) {
        super(String.join(" ", forenames) + String.join(" ", surnames), true);
        this.id = counter++;
        this.forenames = forenames;
        this.surnames = surnames;
        this.authorType = type;
        createFullName();
    }


    public Author(){
        super("", true);
        this.id = counter++;
    }

    public Author(AuthorType type) {
        super("", true);
        this.id = counter++;
        this.authorType = type;
        createFullName();
    }

    public String[] getForenames() {
        return forenames;
    }


    public void setForenames(String[] forenames) {
        this.forenames = forenames;
        createFullName();
        name_ = fullname;
    }


    public String[] getSurnames() {
        return surnames;
    }


    public void setSurnames(String[] surnames) {
        this.surnames = surnames;
        createFullName();
        name_ = fullname;
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

    @Override
    public DetailViewStringNode toDetailTreeview()
    {
        DetailViewStringNode root = new DetailViewStringNode(getFullname());
        root.setExpanded(true);

        DetailViewStringNode fornames = new DetailViewStringNode("Fornames");
        fornames.getChildren().add(new DetailViewStringNode(String.join(" ", this.forenames)));

        DetailViewStringNode surnames = new DetailViewStringNode("Surnames");
        surnames.getChildren().add(new DetailViewStringNode(String.join(" ", this.surnames)));

        DetailViewStringNode papers_root = new DetailViewStringNode("Papers of Author");

        for(MetadataEntry cur_paper : getPapersOfAuthor())
        {
            papers_root.getChildren().add(new TreeItem<>(cur_paper));
        }

        DetailViewStringNode co_authors_root = new DetailViewStringNode("Co-Authors");

        for(Author cur_co_author : getCoAuthors())
        {
            co_authors_root.getChildren().add(new TreeItem<>(cur_co_author));
        }


        DetailViewStringNode sources_root = new DetailViewStringNode("Sources of Author");

        for(Reference cur_paper : getSources())
        {
            sources_root.getChildren().add(new TreeItem<>(cur_paper));
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

    public ArrayList<Reference> getSources()
    {
        Collection<EdgeCitation> edges = GraphCreator.getInstance().getCitationGraph().getOutEdges(this);
        ArrayList<Reference> cited_papers = new ArrayList<>();
        for(EdgeCitation edge : edges)
            cited_papers.addAll(edge.getReferences());
        return cited_papers;
    }

}
