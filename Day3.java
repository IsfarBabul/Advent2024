import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {
    public static void main(String[] args) {

        ArrayList<String> fileData = getFileData("src/Day3Input.txt");
        System.out.println(fileData);

        int total = 0;

        System.out.println(fileData.get(0).substring(0, 7));

        for (int i = 0; i < fileData.size(); i++) {
            for (int j = 0; j < fileData.get(i).length(); j++) {
                if (fileData.get(i).startsWith("mul(", j)) {
                    System.out.println(fileData.get(i).substring(j, j + 4));
                    //Pattern.compile("mul");
                }
            }
        }
        // you now have an ArrayList of Strings for each number in the file
        // do Advent 2024 day 3!

        //PATTERN TEST
        String literal = "I have a 5 on both AP Computer Science Principles and AP Computer Science A";
        Pattern pattern = Pattern.compile("a");
        Matcher matcher = pattern.matcher(literal);
        System.out.println(matcher.group());

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
