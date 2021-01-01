package tugraz.digitallibraries;

import java.util.Arrays;

public class LevenshteinDistanzClass {


    static public int calculateLevenshteinDistanz(String target, String source)
    {
        if (target.isEmpty()) {
            return source.length();
        }

        if (source.isEmpty()) {
            return target.length();
        }

        int array[][] = new int[target.length() + 1][source.length() + 1];


        for (int i = 0; i <= target.length(); i++)
        {
            for (int j = 0; j <= source.length(); j++)
            {
                if (i == 0)
                {
                    array[i][j]= j;
                }
                else if (j == 0)
                {
                    array[i][j] = i;
                }
                else {

                    int a = array[i - 1][j - 1];

                    int b = array[i - 1][j] + 1;
                    int c = array[i][j - 1] + 1;

                    if (target.charAt(i - 1) != source.charAt(j - 1))
                    {
                         a++;
                    }

                    array[i][j] = Math.min(Math.min(a , b), c);
                }
            }
        }

        return array[target.length()][source.length()];
    }

}
