
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Day4 {
    public static void main(String[] args) {

        ArrayList<String> fileData = getFileData("src/Day4Input.txt");
        //ArrayList<String> fileData = getFileData("src/exampletext");
        //ArrayList<String> fileData = getFileData("src/dummyText");
        //System.out.println(fileData);

        ArrayList<ArrayList<String>> fileData2D = new ArrayList<ArrayList<String>>();

        for (int i = 0; i < fileData.size(); i++) {
            fileData2D.add(convertWordToStrings(fileData.get(i)));
        }

        //System.out.println(fileData2D);

        System.out.println(convertWordToStrings("XMAS"));

        int[] unsimplifiedAnswer = {checkHorizontally(convertWordToStrings("XMAS"), fileData2D), checkVertically(convertWordToStrings("XMAS"), fileData2D), checkDiagonallyRight(convertWordToStrings("XMAS"), fileData2D), checkDiagonallyLeft(convertWordToStrings("XMAS"), fileData2D), checkHorizontally(convertWordToStrings("SAMX"), fileData2D), checkVertically(convertWordToStrings("SAMX"), fileData2D), checkDiagonallyRight(convertWordToStrings("SAMX"), fileData2D), checkDiagonallyLeft(convertWordToStrings("SAMX"), fileData2D)};
        System.out.println(Arrays.toString(unsimplifiedAnswer));
        int simplifiedAnswer = 0;
        for (int i = 0; i < unsimplifiedAnswer.length; i++) {
            simplifiedAnswer += unsimplifiedAnswer[i];
        }
        System.out.println("Your answer to Advent 2024 Day 4 Part 1 is: " + simplifiedAnswer);
        System.out.println("Your answer to Advent 2024 Day 4 Part 2 is: " + checkXMas(fileData2D));

        // you now have an ArrayList of Strings for each number in the file
        // do Advent 2024 day 4!
    }

    public static int checkXMas(ArrayList<ArrayList<String>> wordSearch) {
        int count = 0;
        ArrayList<String> arrayOfWord = convertWordToStrings("MAS");                                   //       M S        M M     S S         S M
        ArrayList<String> reverseOfArrayOfWord = convertWordToStrings(reverse("MAS"));           //        A          A       A           A
        for (int i = 0; i <= wordSearch.size() - arrayOfWord.size(); i++) {                            //       M S         S S     M M         S M
            for (int j = 0; j <= wordSearch.get(i).size() - arrayOfWord.size(); j++) {
                boolean issue = false;
                for (int k = 0; k < arrayOfWord.size(); k++) {
                    if (!arrayOfWord.get(k).equals(wordSearch.get(i + k).get(j + k))) {
                        issue = true;
                        break;
                    }
                }
                if (issue) {
                    issue = false;
                    for (int k = 0; k < arrayOfWord.size(); k++) {
                        if (!reverseOfArrayOfWord.get(k).equals(wordSearch.get(i + k).get(j + k))) {
                            issue = true;
                            break;
                        }
                    }
                }
                if (!issue) {
                    for (int k = 0; k < arrayOfWord.size(); k++) {
                        if (!arrayOfWord.get(k).equals(wordSearch.get(i + 2 - k).get(j + k))) {
                            issue = true;
                            break;
                        }
                    }
                    if (issue) {
                        issue = false;
                        for (int k = 0; k < arrayOfWord.size(); k++) {
                            if (!reverseOfArrayOfWord.get(k).equals(wordSearch.get(i + 2 - k).get(j + k))) {
                                issue = true;
                                break;
                            }
                        }
                    }
                }
                if (!issue) {
                    count++;
                }
            }
            
        }
        return count;
    }


    //checks 'Xmas' horizontally
    public static int checkHorizontally(ArrayList<String> arrayOfWord, ArrayList<ArrayList<String>> wordSearch) {
        int count = 0;
        for (int i = 0; i < wordSearch.size(); i++) { //go through each line of our 2d array of strings accounting for bounds issues
            for (int j = 0; j <= wordSearch.get(i).size() - arrayOfWord.size(); j++) { //go through each character in the line
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
        int count = 0;
        for (int i = 0; i < wordSearch.getFirst().size(); i++) { //go through each column of our 2d array of strings accounting for bounds issues
            for (int j = 0; j <= wordSearch.size() - arrayOfWord.size(); j++) { //go through each character in the column
                boolean issue = false;  //checks for issues
                for (int k = 0; k < arrayOfWord.size(); k++) { //checks the character selected and the next 3 characters toward the bottom
                    if (!arrayOfWord.get(k).equals(wordSearch.get(j + k).get(i))) {
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

    //checks 'Xmas' diagonally right(\)
    public static int checkDiagonallyRight(ArrayList<String> arrayOfWord, ArrayList<ArrayList<String>> wordSearch) {
        int count = 0;
        for (int i = 0; i <= wordSearch.size() - arrayOfWord.size(); i++) { //go through each line of our 2d array of strings accounting for bounds issues
            for (int j = 0; j <= wordSearch.get(i).size() - arrayOfWord.size(); j++) { //go through each character in the line
                boolean issue = false;  //checks for issues
                for (int k = 0; k < arrayOfWord.size(); k++) { //checks the character selected and the next 3 characters to the right
                    if (!arrayOfWord.get(k).equals(wordSearch.get(i + k).get(j + k))) {
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

    //checks 'Xmas' diagonally left(/)
    public static int checkDiagonallyLeft(ArrayList<String> arrayOfWord, ArrayList<ArrayList<String>> wordSearch) {
        int count = 0;
        for (int i = arrayOfWord.size() - 1; i < wordSearch.size(); i++) { //go through each line of our 2d array of strings accounting for bounds issues
            for (int j = 0; j <= wordSearch.get(i).size() - arrayOfWord.size(); j++) { //go through each character in the line
                boolean issue = false;  //checks for issues
                for (int k = 0; k < arrayOfWord.size(); k++) { //checks the character selected and the next 3 characters to the right
                    if (!arrayOfWord.get(k).equals(wordSearch.get(i - k).get(j + k))) {
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
