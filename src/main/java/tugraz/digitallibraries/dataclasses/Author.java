package tugraz.digitallibraries.dataclasses;

// Author of a paper
public class Author {

    private String[] forenames;
    private String[] surnames;

    public Author(String[] forenames, String[] surnames)
    {
        this.forenames = forenames;
        this.surnames = surnames;
    }


    public Author(){}

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
}
