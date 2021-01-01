package tugraz.digitallibraries;

public class LevenshteinDistanzTest {

    public static void main(String[] args) {

        String a = "test";
        String b = "temp";


        int i = LevenshteinDistanzClass.calculateLevenshteinDistanz(a,b);

        System.out.println("Levenshtein distance: " + i);
    }


}