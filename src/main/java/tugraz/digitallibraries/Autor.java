package tugraz.digitallibraries;

public class Autor {

    private String[] forenames;
    private String[] surnames;

    public Autor(String[] forenames, String[] surnames)
    {
        this.forenames = forenames;
        this.surnames = surnames;
    }


    public Autor(){}

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
