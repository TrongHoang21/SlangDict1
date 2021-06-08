package SlangDict;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Word {
    private static final String DISPLAY_SEPERATOR = ":";
    String word, meaning;

    Word(){
        this.word = this.meaning = "";
    }

    //Use Map.containsKey()
    public static void lookUpByWord(HashMap<String, String> wordList, String targetWord){
        if(wordList.containsKey(targetWord)){
            System.out.println("Found: " + targetWord + ": " + wordList.get(targetWord));
            return;
        }
        System.out.println(targetWord + " not found");
    }

    //Use String.contain() and Map.Entry to traverse
    public static void lookUpByMeaning(HashMap<String, String> wordList, String targetWord){
        int flag = 0;
        for (Map.Entry m: wordList.entrySet()) {
            String instance = (String)m.getValue();
            if (instance.contains(targetWord)) {
                System.out.println("Found: " + m.getKey() + ": " + m.getValue());
                flag = 1;
            }
        }

        if(flag == 0) System.out.println(targetWord + " not found");
    }

    public String[] turnRecordToObject(String record, String seperator){
        //split
        String[] arrOfStr = record.split(seperator, 0);

        if(arrOfStr.length < 2)
            return null;
        else if (arrOfStr[0].equals("") || arrOfStr[1].equals(""))
            return null;

        this.word= arrOfStr[0];
        this.meaning = arrOfStr[1];

        return arrOfStr;
    }

    public static String turnObjectToRecord(String word, String meaning, String seperator){
        String result = "";

        result += word;
        result += seperator;
        result += meaning;

        return result;
    }

    public static void addWord(HashMap<String, String> wordList) throws IOException{
        BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in, StandardCharsets.UTF_8));


        Word instance = new Word();
        System.out.print("Input new word: ");
        instance.word = br.readLine();

        System.out.print("Input meaning: ");
        instance.meaning = br.readLine();

        //If duplicate found
        //Op1: overwrite Op2: Replace Op3: exit
        if(wordList.containsKey(instance.word)) {
            System.out.println("Duplicated Found - Press key to choose option");
            System.out.println("1: Overwrite 2: Duplicate 3: Exit");
            String flag;
            flag = br.readLine();

            if (flag.equals("1"))
                wordList.replace(instance.word, instance.meaning);
            else if (flag.equals("2")) {
                //In fact, we add ONE MORE MEANING to the OLD WORD. Because we can't look up for duplicated instance in HashMap datatype
                String newMeaning = wordList.get(instance.word) + "| " + instance.meaning;
                wordList.replace(instance.word, newMeaning);
            }
            else
            {
                System.out.println("Failed to add: " + instance.word);
                return;
            }

        }
        else
            wordList.put(instance.word, instance.meaning);

        System.out.println("Successfully added: " + instance.word);
    }

    public static void removeWord(HashMap<String, String> wordList, String targetWord) throws IOException {

        if(wordList.containsKey(targetWord)) {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(System.in, StandardCharsets.UTF_8));

            System.out.println(targetWord + " will be deleted. Are you sure?");
            System.out.println("1. YES \t 2. NO");
            String choice = br.readLine();

            if(choice.equals("1")){
                wordList.remove(targetWord);
                System.out.println("Successfully removed " + targetWord);
            }
            else
                System.out.println(targetWord + " remains in the database");

            return;
        }


        System.out.println(targetWord + " not found");
    }

    public static void updateWord(HashMap<String, String> wordList, String targetWord) throws IOException {

        if(wordList.containsKey(targetWord)) {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(System.in, StandardCharsets.UTF_8));

            System.out.println(targetWord + " found. Please re-input the word:");

            Word instance = new Word();

            System.out.print("Input new word: ");
            instance.word = br.readLine();

            System.out.print("Input meaning: ");
            instance.meaning = br.readLine();

            wordList.put(instance.word, instance.meaning);
            System.out.println("Successfully updated " + targetWord);
            return;
        }


        System.out.println(targetWord + " not found");
    }

    public static void showList(HashMap<String, String> wordList){
        System.out.println("Database has " + wordList.size() + " words at present");
        for(Map.Entry m : wordList.entrySet()){
            System.out.println(m.getKey() + ": " + m.getValue());
        }
    }

    public static void showHistory(ArrayList<String> list) {
        System.out.println("Lookup History From The Latest:");

        for(int i = list.size() - 1; i >= 0 ; i--){
            System.out.println(list.get(i));
        }
    }

    public static String returnOneRandomWord(HashMap<String, String> wordList) {
        //We traverse through the list in 'randomNum' iterations
        int Max = wordList.size()-1;    //[Min, Max] = [0, size()-1]

        Random ran = new Random(System.currentTimeMillis());
        int randomNum = ran.nextInt(Max + 1);   //minimum + rn.nextInt(maxValue - minvalue + 1)

        int rollNum = 0;

        String res = "";
        for(Map.Entry m : wordList.entrySet()){
            if(rollNum == randomNum) {
                res = (m.getKey() + ":" + m.getValue());
            }

            rollNum++;
        }
        return res;
    }

    public static void quizOnWord(HashMap<String, String> wordList) throws IOException {
        //PREPARE PART
        String quest, right;
        String[] wrong;
        wrong = new String[3];

        String record = returnOneRandomWord(wordList);
        Word instance = new Word();
        String[] arrOfStr = instance.turnRecordToObject(record, DISPLAY_SEPERATOR);
        quest = arrOfStr[0];
        right = arrOfStr[1];

        for(int i = 0; i < wrong.length; i++){

            do{
                record = returnOneRandomWord(wordList);
                arrOfStr = instance.turnRecordToObject(record, DISPLAY_SEPERATOR);
                wrong[i] = arrOfStr[1];
            }while(arrOfStr[1].equals(right));
        }

        Random ran = new Random(System.currentTimeMillis());
        int randDisplay = ran.nextInt(4) + 1;   //[1, 3] minimum + rn.nextInt(maxValue - minvalue + 1)

        //DISPLAY PART
        System.out.println("What is the meaning of " + quest + " ?");

        if(randDisplay == 1){
            System.out.println("1. " + right + " 2. " + wrong[0] + " 3. "+ wrong[1] + " 4. " + wrong[2]);
        }
        else if(randDisplay == 2){
            System.out.println("1. " + wrong[0] + " 2. " + right + " 3. "+ wrong[1] + " 4. " + wrong[2]);
        }
        else if(randDisplay == 3){
            System.out.println("1. " + wrong[1] + " 2. " + wrong[0] + " 3. "+ right + " 4. " + wrong[2]);
        }
        else {
            System.out.println("1. " + wrong[2] + " 2. " + wrong[0] + " 3. "+ wrong[1] + " 4. " + right);
        }

        //VALIDATE PART
        BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in, StandardCharsets.UTF_8));
        String choice;

        do{
            System.out.print("Your choice (other key to exit): ");
            choice = br.readLine();

            if (Integer.parseInt(choice) == randDisplay) {
                System.out.println("true!!");
                return;
            } else {
                System.out.println("false!!");
            }
        } while(!choice.equals("") && (choice.equals("1") || choice.equals("2") || choice.equals("3") || choice.equals("4") ));

    }

    public static void quizOnMeaning(HashMap<String, String> wordList) throws IOException {
        //PREPARE PART
        String quest, right;
        String[] wrong;
        wrong = new String[3];

        String record = returnOneRandomWord(wordList);
        Word instance = new Word();
        String[] arrOfStr = instance.turnRecordToObject(record, DISPLAY_SEPERATOR);
        quest = arrOfStr[1];
        right = arrOfStr[0];

        for(int i = 0; i < wrong.length; i++){

            do{
                record = returnOneRandomWord(wordList);
                arrOfStr = instance.turnRecordToObject(record, DISPLAY_SEPERATOR);
                wrong[i] = arrOfStr[0];
            }while(arrOfStr[0].equals(right));
        }

        Random ran = new Random(System.currentTimeMillis());
        int randDisplay = ran.nextInt(4) + 1;   //[1, 3] minimum + rn.nextInt(maxValue - minvalue + 1)

        //DISPLAY PART
        System.out.println("What is the word for " + quest + " ?");

        if(randDisplay == 1){
            System.out.println("1. " + right + " 2. " + wrong[0] + " 3. "+ wrong[1] + " 4. " + wrong[2]);
        }
        else if(randDisplay == 2){
            System.out.println("1. " + wrong[0] + " 2. " + right + " 3. "+ wrong[1] + " 4. " + wrong[2]);
        }
        else if(randDisplay == 3){
            System.out.println("1. " + wrong[1] + " 2. " + wrong[0] + " 3. "+ right + " 4. " + wrong[2]);
        }
        else {
            System.out.println("1. " + wrong[2] + " 2. " + wrong[0] + " 3. "+ wrong[1] + " 4. " + right);
        }

        //VALIDATE PART
        BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in, StandardCharsets.UTF_8));
        String choice;

        do{
            System.out.print("Your choice (other key to exit): ");
            choice = br.readLine();

            if (Integer.parseInt(choice) == randDisplay) {
                System.out.println("true!!");
                return;
            } else {
                System.out.println("false!!");
            }
        } while(!choice.equals("") && (choice.equals("1") || choice.equals("2") || choice.equals("3") || choice.equals("4") ));

    }
}
