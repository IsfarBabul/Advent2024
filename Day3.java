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
        String currentMatch = "";
        boolean enabled = true;

        for (int i = 0; i < fileData.size(); i++) {
            for (int j = 0; j < fileData.get(i).length(); j++) {
                Pattern pattern = Pattern.compile("mul\\([0-9]{1,3},[0-9]{1,3}\\)|do\\(\\)|don't\\(\\)");
                Matcher matcher = pattern.matcher(fileData.get(i).substring(j));
                boolean matchFound = matcher.find();
                if (matchFound && !currentMatch.equals(matcher.group())) {
                    if (matcher.group().equals("do()")) {
                        enabled = true;
                    } else if (matcher.group().equals("don't()")) {
                        enabled = false;
                    } else if (enabled) {
                        System.out.println(matcher.group() + " = " + mul(matcher.group()));
                        total += mul(matcher.group());
                        j += matcher.group().length();
                        currentMatch = matcher.group();
                    }
                }
            }
        }

        System.out.println("The answer to Advent 2024 Day 3 Part 2 is " + total);

        // you now have an ArrayList of Strings for each number in the file
        // do Advent 2024 day 3!
    }

    //feed it a string like "mul(456,128)" min 8 max 12

    public static int mul(String expression) {
        int front = expression.indexOf("(");
        int middle = expression.indexOf(",");
        int back = expression.indexOf(")");
        int firstNum = Integer.parseInt(expression.substring(front + 1, middle));
        int secondNum = Integer.parseInt(expression.substring(middle + 1, back));
        return firstNum * secondNum;
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