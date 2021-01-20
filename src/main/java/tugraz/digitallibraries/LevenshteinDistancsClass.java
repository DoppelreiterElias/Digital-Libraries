package tugraz.digitallibraries;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tugraz.digitallibraries.graph.EdgeCoAuthorship;
import tugraz.digitallibraries.ui.DetailViewObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LevenshteinDistancsClass {


    static public ObservableList<DetailViewObject> paperTitelLevenshteinDistances(String target, ArrayList<EdgeCoAuthorship> edges_list, int number_of_hits)
    {

        ObservableList<DetailViewObject> return_list = FXCollections.observableArrayList();

        List<String> text_list = new ArrayList<String>();


        target = "[" + target + "]";


        for (int i = 0; i < edges_list.size(); i++)
        {
            String new_paper = edges_list.get(i).getPapers().toString();

            boolean already_in_array = false;


           for (int j = 0; j < i ; j++)
           {
               if (new_paper.equals(edges_list.get(j).getPapers().toString()))
               {
                   already_in_array = true;
               }
           }

            if (!already_in_array)
            {
                text_list.add(edges_list.get(i).getPapers().toString());
            }
        }



        //List to Array
        String unsorted_texts[] = new String[text_list.size()];
        String sorted_texts[] = new String[text_list.size()];

        unsorted_texts = text_list.toArray(unsorted_texts);


        int sorted_ratings[] = new int[text_list.size()];

        int rating[] = new int[text_list.size()];

        for (int i = 0; i < unsorted_texts.length; i++)
        {
            rating[i] = calculateLevenshteinDistance(target,unsorted_texts[i]);
        }


        int max = 0;

        for (int i = 0; i < rating.length;i++)
        {
            if (max < rating[i])
                max = rating[i];
        }

        int temporary_holder[] = new int[max+1];


        for (int i = 0; i < rating.length; i++)
        {
            temporary_holder[rating[i]] += 1;
        }


        for (int i = 1; i < temporary_holder.length; i++)
        {
            temporary_holder[i] = temporary_holder[i] + temporary_holder[i-1];
        }


        for (int i = 0; i < rating.length;i++)
        {
            sorted_ratings[temporary_holder[rating[i]]-1] = rating[i];
            sorted_texts[temporary_holder[rating[i]]-1] = unsorted_texts[i];
            temporary_holder[rating[i]] -= 1;
        }



        if (number_of_hits >= unsorted_texts.length)
        {
            for (int i = 0; i < sorted_texts.length; i++)
            {
                for (int j = 0; j < edges_list.size(); j++)
                {
                    if (sorted_texts[i].equals(edges_list.get(j).getPapers().toString()))
                    {
                        return_list.addAll(edges_list.get(j).getPapers());
                        break;
                    }
                }
            }

        } else if (number_of_hits < 0)
        {
            return_list = null;
        }
        else
        {
            for (int i = 0; i < number_of_hits; i++)
            {
                for (int j = 0; j < edges_list.size(); j++)
                {
                    if (sorted_texts[i].equals(edges_list.get(j).getPapers().toString()))
                    {
                        return_list.addAll(edges_list.get(j).getPapers());
                        break;
                    }
                }
            }
        }

        return return_list;
    }


    //based on pseudo code: https://de.wikipedia.org/wiki/Countingsort
    //and https://studyflix.de/informatik/counting-sort-1407
    static public String[] getTopTenLevenshteinDistances(String target, String source[],  int number_of_hits)
    {
        //For test purposes
        /*
        //Test Begin

        String source[] = {"drei", "eins", "neun", "vier", "drei","acht","neun","fünf"};

        int rating[] = {3, 1, 9, 4, 3, 8, 9, 5};

        //Test Ende
        */



        String sorted_texts[] = new String[source.length];
        int sorted_ratings[] = new int[source.length];

        int rating[] = new int[source.length];


        for (int i = 0; i < source.length; i++)
        {
            rating[i] = calculateLevenshteinDistance(target,source[i]);
        }


        int max = 0;

        for (int i = 0; i < rating.length;i++)
        {
            if (max < rating[i])
                max = rating[i];
        }

        int temporary_holder[] = new int[max+1];


        for (int i = 0; i < rating.length; i++)
        {
            temporary_holder[rating[i]] += 1;
        }


        for (int i = 1; i < temporary_holder.length; i++)
        {
            temporary_holder[i] = temporary_holder[i] + temporary_holder[i-1];
        }


        for (int i = 0; i < rating.length;i++)
        {
            sorted_ratings[temporary_holder[rating[i]]-1] = rating[i];
            sorted_texts[temporary_holder[rating[i]]-1] = source[i];
            temporary_holder[rating[i]] -= 1;
        }


        if (number_of_hits >= source.length)
        {
            return sorted_texts;

        } else if (number_of_hits < 0)
        {
            return new String[0];
        }
        else
        {
            String texts[] = new String[number_of_hits];
            for (int i = 0; i < number_of_hits; i++)
            {
                texts[i] = sorted_texts[i];
            }

            return texts;
        }

    }


    static public int[] calculateLevenshteinDistances(String target, String source[])
    {
        int array[] = new int[source.length];


        for (int i = 0; i < source.length; i++)
        {
            array[i] = calculateLevenshteinDistance(target,source[i]);
        }

        return array;
    }

    //based on pseudo code: https://de.wikipedia.org/wiki/Levenshtein-Distanz
    static public int calculateLevenshteinDistance(String target, String source)
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
