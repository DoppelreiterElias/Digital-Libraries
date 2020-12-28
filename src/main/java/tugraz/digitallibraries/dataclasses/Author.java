package tugraz.digitallibraries.dataclasses;


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
}
