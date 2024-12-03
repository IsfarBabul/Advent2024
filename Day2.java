import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day2 {
    public static void main(String[] args) {

        ArrayList<String> fileData = getFileData("src/Day2Input.txt");
        System.out.println(fileData);

        ArrayList<ArrayList<Integer>> num2dData = new ArrayList<>();

        for (String fileDatum : fileData) {
            ArrayList<Integer> numData = new ArrayList<>();
            String[] splitString = fileDatum.split(" ");
            for (String s : splitString) {
                numData.add(Integer.valueOf(s));
            }
            num2dData.add(numData);
        }

        // you now have an ArrayList of Strings for each level of numbers in the file
        // do Advent 2024 day 2!
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