package tugraz.digitallibraries;

public class LevenshteinDistanecTest {

    public static void main(String[] args) {

        String a = "test";
        String b = "temp";

        String[] c = {"Dwayne The Rock Johnson", "house", "Apple", "car"};


        int i = LevenshteinDistancsClass.calculateLevenshteinDistance(a,b);

        System.out.println("Levenshtein distance: " + i);

        int j[] = LevenshteinDistancsClass.calculateLevenshteinDistances(a,c);

        System.out.print("Levenshtein distance: ");

        for (int x = 0; x < j.length; x++)
        {
            System.out.print(j[x] + " ");
        }
    }


}