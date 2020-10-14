import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class JavaUtils {

    public static void main(String[] args) {

    }

    /**
     *
     * EN : Generating a random int.
     * FR : Génère un int aléatoire
     *
     * @param Min
     * @param Max
     *
     * @return int between Min and Max
     */
    public static int CustomRandom(int Min, int Max) {
        return Min + (int)(Math.random() * ((Max - Min) + 1));
    }

    /**
     *
     * EN : Change chars to String
     * FR : Transforme les chars en String
     *
     * @param Chars
     * @return String
     */
    public static String CharsToString(char[] Chars) {
        StringBuilder result = new StringBuilder();
        for (char aChar : Chars) {
            result.append(aChar);
        }
        return result.toString();
    }

    /**
     *
     * EN : Sorts an array in alphabetical order
     * FR : Trie un tableau par ordre alphabétique
     *
     * @param ListOfString
     * @return sortedListOfString
     */
    public static String[] ABCsort(String[] ListOfString) {
        List List = new ArrayList();
        String[] result = new String[ListOfString.length];

        List.addAll(Arrays.asList(ListOfString));
        Collections.sort(List);

        for (int i = 0; i < List.size(); i++) {
            result[i] = List.get(i).toString();
        }

        return result;
    }

}
