package tugraz.digitallibraries;

public class LevenshteinDistanzTest {

    public static void main(String[] args) {

        String a = "test";
        String b = "temp";

        String[] c = {"Dwayne The Rock Johnson", "house", "Apple", "car"};


        int i = LevenshteinDistanzClass.calculateLevenshteinDistanz(a,b);

        System.out.println("Levenshtein distance: " + i);

        int j[] = LevenshteinDistanzClass.calculateLevenshteinDistanzs(a,c);

        System.out.print("Levenshtein distance: ");

        for (int x = 0; x < j.length; x++)
        {
            System.out.print(j[x] + " ");
        }
    }


}