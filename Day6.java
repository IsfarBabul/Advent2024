import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day6 {
    public static void main(String[] args) {

        //ArrayList<String> fileData = getFileData("src/Day6Input.txt");
        ArrayList<String> fileData = getFileData("src/exampleText");
        System.out.println(fileData);

        ArrayList<ArrayList<String>> map = new ArrayList<>();
        for (int i = 0; i < fileData.size(); i++) {
            ArrayList<String> mapLine = new ArrayList<>();
            for (int j = 0; j < fileData.get(i).length(); j++) {
                mapLine.add(fileData.get(i).substring(j, j + 1));
            }
            map.add(mapLine);
        }

        for (int i = 0; i < map.size(); i++) {
            System.out.println(map.get(i));
        }


        // you now have an ArrayList of Strings for the map in the file
        // do Advent 2024 day 6!
    }

    public static ArrayList<Integer> locateGuard() {
        int x = 0;
        int y = 0;
        ArrayList<Integer> coordinates = new ArrayList<>();
        coordinates.add(x);
        coordinates.add(y);
        return coordinates;
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