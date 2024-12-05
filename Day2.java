import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day2 {
    public static void main(String[] args) {

        ArrayList<String> fileData = getFileData("src/adummylikeu");
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

        int isUnsafe = 0;
        int count = 0;

        for (int i = 0; i < num2dData.size(); i++) {
            ArrayList<Integer> targetLevel = num2dData.get(i);
            boolean isIncreasing = targetLevel.get(0) < targetLevel.get(1);
            if (problemDetector(targetLevel, isIncreasing)) {
                isUnsafe++;
            }
        }

        int isSafe = num2dData.size() - isUnsafe;

        System.out.println(num2dData);

        System.out.println("Advent2024 Day2 Part1 Answer: " + isSafe);


        //PART 2

        isUnsafe = 0;

        for (int i = 0; i < num2dData.size(); i++) {
            ArrayList<Integer> targetLevel = num2dData.get(i);
            boolean isIncreasing = targetLevel.get(0) < targetLevel.get(1);
            if (problemDetector(targetLevel, isIncreasing)) {
                boolean safeDetected = false;
                for (int j = 0; j < targetLevel.size(); j++) {
                    ArrayList<Integer> newTargetLevel = new ArrayList<>(targetLevel);
                    newTargetLevel.remove(j);
                    System.out.println(newTargetLevel);
                    if (!problemDetector(newTargetLevel, isIncreasing)) {
                        safeDetected = true;
                    }
                }
                if (!safeDetected) {
                    isUnsafe++;
                }
            }
        }

        /*
        decreasing or increasing
        adjacent numbers are equal
        adjacent numbers distance of 3(assuming first condition is met)



         */



        isSafe = num2dData.size() - isUnsafe;

        System.out.println("Advent2024 Day2 Part2 Answer: " + isSafe);

        // you now have an ArrayList of Strings for each level of numbers in the file
        // do Advent 2024 day 2!
    }

    public static boolean problemDetector(ArrayList<Integer> targetLevel, boolean isIncreasing) {
        for (int j = 0; j < targetLevel.size() - 1; j++) {
            int distance = targetLevel.get(j + 1) - targetLevel.get(j);
            if (Math.abs(distance) > 3) {
                return true;
            } else if (isIncreasing && distance <= 0) {
                return true;
            } else if (!isIncreasing && distance >= 0) {
                return true;
            }
        }
        return false;
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