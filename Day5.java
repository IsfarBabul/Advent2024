import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day5 {
    public static void main(String[] args) {

        ArrayList<String> fileData = getFileData("src/Day5Input.txt");
        System.out.println(fileData);

        ArrayList<String> rules = new ArrayList<>();
        ArrayList<String> updates = new ArrayList<>();

        for (String fileDatum : fileData) {
            if (fileDatum.contains("|")) {
                rules.add(fileDatum);
            } else {
                updates.add(fileDatum);
            }
        }

        System.out.println("Rules: " + rules);
        System.out.println();
        System.out.println("Updates: " + updates);


        // you now have an ArrayList of Strings of the rules and updates in the file
        // do Advent 2024 day 5!
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