import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day4 {
    public static void main(String[] args) {

        ArrayList<String> fileData = getFileData("src/Day4Input.txt");
        System.out.println(fileData);

        ArrayList<ArrayList<String>> fileData2D = new ArrayList<ArrayList<String>>();

        for (int i = 0; i < fileData.size(); i++) {
            fileData2D.add(convertWordToStrings(fileData.get(i)));
        }

        System.out.println(fileData2D);



        // you now have an ArrayList of Strings for each number in the file
        // do Advent 2024 day 4!
    }


    //checks 'Xmas' horizontally
    public static int checkHorizontally() {
        return 0;
    }

    //checks 'Xmas' vertically

    //checks 'Xmas' diagonally right(\)

    //checks 'Xmas' diagonally left(/)

    //converts words into strings
    public static ArrayList<String> convertWordToStrings(String word) {
        ArrayList<String> strings = new ArrayList<String>();
        for (int i = 0; i < word.length(); i++) {
            strings.add(word.substring(i, i + 1));
        }
        return strings;
    }

    //string reverser



    public static ArrayList<String> getFileData(String fileName) {
        ArrayList<String> fileData = new ArrayList<String>();
        try {
            File f = new File(fileName);
            Scanner s = new Scanner(f);
            while (s.hasNextLine()) {
                String line = s.nextLine();
                if (!line.equals(""))
                    fileData.add(line);
            }
            return fileData;
        }
        catch (FileNotFoundException e) {
            return fileData;
        }
    }
}