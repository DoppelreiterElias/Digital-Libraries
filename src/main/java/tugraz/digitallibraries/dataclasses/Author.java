package tugraz.digitallibraries.dataclasses;


// Author of a paper
public class Author {

    private static int counter = 0;
    private int id;
    private String[] forenames;
    private String[] surnames;
    AuthorType authorType;


    public Author(String[] forenames, String[] surnames)
    {
        this.id = counter++;
        this.forenames = forenames;
        this.surnames = surnames;
    }

    public Author(String[] forenames, String[] surnames, AuthorType type) {
        this.id = counter++;
        this.forenames = forenames;
        this.surnames = surnames;
        this.authorType = type;
    }


    public Author(){
        this.id = counter++;
    }

    public Author(AuthorType type) {

        this.id = counter++;
        this.authorType = type;
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
}
