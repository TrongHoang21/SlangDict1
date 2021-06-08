package SlangDict;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

//NGUYEN TAC: Code and comment in English, giao dien tieng Viet
public class Main {
    public static void main(String[] args)throws IOException
    {
        //DATA TYPE
        HashMap<String, String> wordList = new HashMap<>();
        ArrayList<String> history = new ArrayList<>();

        //IMPORT DATA
        FileManager.importDataFromFile(history, "history.txt");
        FileManager.importDataFromFile(wordList, "slang.txt");

        //RUNNING PROGRAM
        showMenu(wordList, history);

        //SAVE BACK TO DATABASE IF NO CRASH HAPPEN
        FileManager.saveToDatabase(wordList, "slang.txt");
        FileManager.saveToDatabase(history, "history.txt");
    }

    public static void showMenu(HashMap<String, String> wordList, ArrayList<String> history) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
        char choice;

        //MAIN MENU OF THE PROGRAM
        do {
            System.out.println("MENU: \n1. TIM KIEM THEO SLANG WORD\t2. TIM KIEM THEO MEANING\t3. THEM TU MOI" +
                    "\n4. CHINH SUA 1 TU\t5. XOA 1 TU\t6. RESET DATA\t7.XEM LICH SU TIM KIEM\t8. XEM DANH SACH" +
                    "\n9. RANDOMS AND GAMES\n0. PRESS 0 OR ANY OTHER KEY TO EXIST");

            System.out.println("Ban chon chuc nang nao?: ");

            //Menu-controll variable
            Scanner sc = new Scanner(System.in);
            choice = sc.next().charAt(0);
            String str;

            //Choice handler
            switch (choice) {
                case '1':
                    System.out.println("Chuc nang tim kiem theo slang word");
                    System.out.println("Nhap tu ban muon tim: ");
                    str = br.readLine();

                    if(!str.equals(""))
                        Word.lookUpByWord(wordList, str);
                    else
                        System.out.println("Nhap loi");

                    history.add(str);
                    break;

                case '2':
                    System.out.println("Chuc nang tim kiem theo meaning");
                    System.out.println("Nhap tu ban muon tim: ");
                    str = br.readLine();

                    if(!str.equals(""))
                        Word.lookUpByMeaning(wordList, str);
                    else
                        System.out.println("Nhap loi");

                    history.add(str);
                    break;

                case '3':
                    System.out.println("Chuc nang them 1 tu moi");
                    Word.addWord(wordList);

                    break;

                case '4':
                    System.out.println("Chuc nang chinh sua 1 tu");
                    System.out.println("Nhap tu ban muon chinh sua: ");
                    str = br.readLine();

                    if(!str.equals(""))
                        Word.updateWord(wordList, str);
                    else
                        System.out.println("Nhap loi");

                    break;

                case '5':
                    System.out.println("Chuc nang xoa 1 tu");
                    System.out.println("Nhap tu ban muon xoa: ");
                    str = br.readLine();

                    if(!str.equals(""))
                        Word.removeWord(wordList, str);
                    else
                        System.out.println("Nhap loi");

                    break;

                case '6':
                    System.out.println("Chuc nang reset database ban dau");
                    System.out.println("Are you sure?\n1. YES\t2. NO");
                    str = br.readLine();

                    if(!str.equals("1"))
                        FileManager.importDataFromFile(wordList, "slangOriginal.txt");
                    else
                        System.out.println("The current database remains unchanged");

                    break;

                case '7':
                    System.out.println("Hien thi lich su tim kiem");
                    Word.showHistory(history);

                    break;

                case '8':
                    System.out.println("Hien thi danh sach");
                    Word.showList(wordList);

                    break;

                case '9':
                    System.out.println("Chuc nang RANDOMS AND GAMES");
                    System.out.println("Ban chon chuc nang nao?\n1. Random word\t2. Quiz on word\t3. Quiz on meaning");
                    str = br.readLine();

                    if(str.equals("1"))
                        System.out.println("Today random word is:\n"+ Word.returnOneRandomWord(wordList));
                    else if(str.equals("2"))
                        Word.quizOnWord(wordList);
                    else if(str.equals("3"))
                        Word.quizOnMeaning(wordList);
                    else
                        System.out.println("Nhap loi");

                    break;

                default:
                    choice = '0';
            }
        } while('0' < choice && choice <= '9');

        br.close();

    }
}