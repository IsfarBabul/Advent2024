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

        System.out.println(convertWordToStrings("XMAS"));
        System.out.println(checkHorizontally(convertWordToStrings("XMAS"), fileData2D));


        // you now have an ArrayList of Strings for each number in the file
        // do Advent 2024 day 4!
    }


    //checks 'Xmas' horizontally
    public static int checkHorizontally(ArrayList<String> arrayOfWord, ArrayList<ArrayList<String>> wordSearch) {
        int count = 0;
        for (int i = 0; i < wordSearch.size(); i++) { //go through each line of our 2d array of strings accounting for bounds issues
            for (int j = 0; j < wordSearch.get(i).size() - arrayOfWord.size(); j++) { //go through each character in the line
                boolean issue = false;  //checks for issues
                for (int k = 0; k < arrayOfWord.size(); k++) { //checks the character selected and the next 3 characters to the right
                    if (!arrayOfWord.get(k).equals(wordSearch.get(i).get(j + k))) {
                        issue = true;
                        break;
                    }
                }
                if (!issue) {
                    count++;
                }
            }
        }
        return count;
    }

    //checks 'Xmas' vertically
    public static int checkVertically(ArrayList<String> arrayOfWord, ArrayList<ArrayList<String>> wordSearch) {

        return 0;
    }

    //checks 'Xmas' diagonally right(\)
    public static int checkDiagonallyRight(ArrayList<String> arrayOfWord, ArrayList<ArrayList<String>> wordSearch) {

        return 0;
    }

    //checks 'Xmas' diagonally left(/)
    public static int checkDiagonallyLeft(ArrayList<String> arrayOfWord, ArrayList<ArrayList<String>> wordSearch) {

        return 0;
    }

    //converts words into strings
    public static ArrayList<String> convertWordToStrings(String word) {
        ArrayList<String> strings = new ArrayList<String>();
        for (int i = 0; i < word.length(); i++) {
            strings.add(word.substring(i, i + 1));
        }
        return strings;
    }

    //string reverser
    public static String reverse(String word) {
        String reversedString = "";
        for (int i = word.length() - 1; i >= 0 ; i--) {
            reversedString += word.substring(i, i + 1);
        }
        return reversedString;
    }



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