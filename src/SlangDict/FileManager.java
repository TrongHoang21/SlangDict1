package SlangDict;
import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class FileManager {
    private static final String SEPERATOR = "`";

    public static void importDataFromFile(HashMap<String, String> wordList, String fileName) {
        //display text
        System.out.println("Importing data from " + fileName + " ...");

        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(fileName));

            while (true) {
                String str = br.readLine();
                if (str == null)
                    break;
                //Converting
                Word std = new Word();
                std.turnRecordToObject(str, SEPERATOR);
                wordList.put(std.word, std.meaning);
            }

            //successfully
            System.out.println("Successfully imported from " + fileName);
            br.close();

        } catch (IOException exc) {
            System.out.println("Error opening file: " + exc);
            System.exit(1);
        }
    }

    public static void saveToDatabase(HashMap<String, String> wordList, String fileName) {
        FileWriter fw;

        try {
            fw = new FileWriter(fileName);   //no append, rewrite


            for (Map.Entry m : wordList.entrySet()) {
                String str = Word.turnObjectToRecord((String) m.getKey(), (String) m.getValue(), SEPERATOR);
                fw.write(str);
                fw.write("\n");
            }

            fw.close();
            System.out.println("Successfully exported to " + fileName);

        } catch (IOException exc) {
            System.out.println("Error opening file");
            System.exit(1);
        }
    }

    public static void saveToDatabase(ArrayList<String> list, String fileName) {
        FileWriter fw;

        try {
            fw = new FileWriter(fileName);   //no append, rewrite


            for(int i = 0; i < list.size(); i++) {
                fw.write(list.get(i));
                fw.write("\n");
            }

            fw.close();
            System.out.println("Successfully exported to " + fileName);

        } catch (IOException exc) {
            System.out.println("Error opening file");
            System.exit(1);
        }
    }


    public static void importDataFromFile(ArrayList<String> WordList, String fileName) {
        //display text
        System.out.println("Importing data from " + fileName + " ...");

        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(fileName));


        while (true) {
            String str = br.readLine();
            if (str == null)
                break;

            WordList.add(str);
        }

        //successfully
        System.out.println("Successfully imported from " + fileName);
        br.close();

        } catch (IOException exc) {
            System.out.println("Error opening file: " + exc);
            System.exit(1);
        }

    }
}