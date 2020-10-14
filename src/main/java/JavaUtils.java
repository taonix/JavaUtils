import java.io.*;
import java.util.*;

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

    /**
     * @param nameFile :String
     * @return {@code true} if the file of th directory {@code nomFichier} exist
     */
    public static boolean exist(String nameFile){
        File f = new File(nameFile);
        return f.exists() && !f.isDirectory();
    }

    /**
     * Create file to directory with name indicated in {@code nomFichier}<br>
     * @param nameFile :String
     */
    public static void createFile(String nameFile) {
        File f = new File(nameFile);
        try {
            String[] temp = nameFile.split("/");
            String strTemp = "";
            for(int i = 0; i < temp.length - 1; i++) {
                strTemp = strTemp + temp[i] + "/";
            }
            new File(strTemp).mkdir();
            f.createNewFile();
        } catch (IOException e) {
            System.out.println("Erreur: Impossible de créer le fichier!!");
        }
    }

    /**
     * Create direcoty or path with idicated name in {@code nomDosier}<br>
     * @param directoryName :String
     */
    public static void createDirectory(String directoryName) {
        new File(directoryName + "/").mkdir();
    }

    /**
     * Delete file if the path and name is {@code fileName}.
     * @param fileName :String
     */
    public static void deleteFile(String fileName) {
        File f = new File(fileName);
        if(!f.delete()) {
            System.out.println("Erreur: Impossible de supprimer le fichier!!");
        }
    }

    /**
     * Rename {@code fileName} to {@code newFileName}
     * @param newFileName :String
     * @param fileName :String
     */
    public static void renameFile(String fileName, String newFileName) {
        File f = new File(fileName);
        f.renameTo(new File(newFileName));
    }

    /**
     * write th text {@code text} at end of the file {@code fileName} and return at line.
     * @param text :String
     * @param fileName :String
     */
    public static void write(String text, String fileName) {
        if(!new File(fileName).exists()) {
            createFile(fileName);
        }
        try {
            FileWriter fw = new FileWriter(fileName, true);
            BufferedWriter input = new BufferedWriter(fw);
            input.write(text + "\n");
            input.flush();
            input.close();
            fw.close();
        }
        catch(IOException ioe) {
            System.out.println("Error: " + ioe);
        }
    }


    /**
     * @param numLine :int
     * @param fileName :String
     * @return the line {@code numLine} of file {@code fileName}
     */
    public static String read(int numLine, String fileName) {
        if(numLine != 0) {
            try {
                File f = new File(fileName);
                Scanner fichier = new Scanner(f);
                String texte;
                try {
                    for(int i = 0; i < numLine-1; i++) {
                        fichier.nextLine();
                    }
                    texte = fichier.nextLine();
                    fichier.close();
                    return texte;
                }
                catch(NoSuchElementException e) {
                    fichier.close();
                    return "Error: Line not found!!";
                }
            }
            catch(FileNotFoundException exception) {
                return "Error: File not found!!";
            }
        }
        else {
            return "Error: Line 0 dose not exist!!";
        }
    }

    /**
     * Delete the line number {@code numLine} of the file {@code numFichier}.
     * @param numLine :String
     * @param fileName :String
     */
    public static void deleteLine(int numLine, String fileName) {
        if(numLine != 0) {
            //On copie toutes les lignes sauf celle que l'on souhaite supprimer dans un fichier ...Bis
            int i = 1;
            createFile(fileName+"Bis");
            while(read(i, fileName) != "Error: Line not found!!") {
                String lue = read(i, fileName);
                if(i != numLine) {
                    write(lue, fileName+"Bis");
                }
                i++;
            }

            //On suprimme le fichier hôte.
            deleteFile(fileName);

            //On renomme ...Bis avec le même nom que le fichier original
            renameFile(fileName+"Bis", fileName);

        }
        else {
            System.out.println("Error: Line 0 not found!!");
        }
    }

    /**
     * Delete the line container the id {@code id} of file {@code fileName}
     * @param fileName :String
     * @param id :String
     */
    public static void supprimerLigneById(String fileName, String id) {
        deleteLine(numLigneFromId(fileName, id), fileName);
    }


    /**
     * @param Line :String
     * @param idColumn :int
     * @return The part of {@code Line} corresponding to {@code idColumn}
     */
    public static String getLine(String Line, int idColumn) {
        int cpt = 0;
        while(cpt != idColumn) {
            while(Line.charAt(0) != ':') {
                Line = Line.substring(1);
            }
            Line = Line.substring(1);
            cpt++;
        }
        String result = "";
        try {
            while(Line.charAt(0) != ':') {
                result = result + Line.charAt(0);
                Line = Line.substring(1);
            }
            return result;
        }
        catch(StringIndexOutOfBoundsException e) {
            return "Error: Column not found!!";
        }
    }

    /**
     * @param Line :String
     * @return The number of information on {@code Line} corresponding to {@code id}.
     */
    private static int nbInfos(String Line) {
        int cpt = 0;
        while(getLine(Line, cpt) != "Error: Column not found!!") {
            cpt++;
        }
        return cpt;
    }

    private static int numLigneFromId(String nomFichier, String id) {
        int cpt = 1;
        while(cpt <= nbLigne(nomFichier) && !id.equals(getLine(read(cpt, nomFichier), 0))) {
            cpt++;
        }
        if(cpt <= nbLigne(nomFichier)) {
            return cpt;
        }else {
            return -1;
        }
    }

    /**
     * @param id :String
     * @return Un array.
     */
    public static String[] getInfosFromId(String nomFichier, String id) {
        int numLigne = numLigneFromId(nomFichier, id);
        String ligne = read(numLigne, nomFichier);
        String[] result = new String[nbInfos(ligne)];
        for(int i = 0; i<result.length; i++) {
            result[i] = getLine(ligne, i);
        }
        return result;
    }

    /**
     * @param fileName :String
     * @param id :String
     * @return {@code true} ssi le fichier {@code fileName} contient une ligne correspondant à {@code id}.
     */
    public static boolean haveId(String fileName, String id) {
        int i = 1;
        while(i <= nbLigne(fileName) && !getLine(read(i, fileName), 0).equals(id)) {
            i++;
        }
        return i <= nbLigne(fileName);
    }

    /**
     * @param nomFichier :String
     * @return The nuber of the line of the file {@code nomFichier}.
     */
    public static int nbLigne(String nomFichier) {
        int cpt = 0;
        while(read(cpt, nomFichier) != "Erreur: Line not found!!"){
            cpt++;
        }
        cpt--;
        return cpt;
    }


}
