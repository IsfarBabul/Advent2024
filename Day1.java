import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
public class Day1 {
    public static void main(String[] args) {
        ArrayList<String> fileData = getFileData("src/Day1Input.txt");
        System.out.println(fileData);
        ArrayList<Integer> leftList = new ArrayList<>();
        ArrayList<Integer> rightList = new ArrayList<>();
        for (int i = 0; i < fileData.size(); i++) {
            leftList.add(Integer.valueOf(fileData.get(i).split(" {3}")[0]));
            rightList.add(Integer.valueOf(fileData.get(i).split(" {3}")[1]));
        }
        leftList.sort(null);
        rightList.sort(null);
        System.out.println(leftList);
        System.out.println(rightList);
        int totalDistance = 0;
        for (int i = 0; i < fileData.size(); i++) {
            totalDistance += Math.abs(leftList.get(i) - rightList.get(i));
        }
        int similarityScore = 0;
        for (int i = 0; i < leftList.size(); i++) {
            int count = 0;
            for (int j = 0; j < rightList.size(); j++) {
                if (Objects.equals(leftList.get(i), rightList.get(j))) {
                    count++;
                }
            }
            similarityScore += leftList.get(i) * count;
        }
        System.out.println(totalDistance);
        System.out.println(similarityScore);
        // you now have an ArrayList of Strings for each pair of numbers in the file
        // do Advent 2024 day 1!
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