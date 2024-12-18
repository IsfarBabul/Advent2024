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
        System.out.println(locateGuard(map));
        for (int i = 0; i < 7; i++) {
            System.out.println(moveUp(map));
        }
        for (int i = 0; i < map.size(); i++) {
            System.out.println(map.get(i));
        }


        // you now have an ArrayList of Strings for the map in the file
        // do Advent 2024 day 6!
    }


    //returns x-coordinate going down and y-coordinate going right
    public static ArrayList<Integer> locateGuard(ArrayList<ArrayList<String>> map) {
        String[] guard = {"^", ">", "v", "<"};
        int x = -1;
        int y = -1;
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(i).size(); j++) {
                for (int k = 0; k < guard.length; k++) {
                    if (map.get(i).get(j).equals(guard[k])) {
                        x = i;
                        y = j;
                    }
                }
            }
        }
        ArrayList<Integer> coordinates = new ArrayList<>();
        coordinates.add(x);
        coordinates.add(y);
        return coordinates;
    }

    public static boolean moveUp(ArrayList<ArrayList<String>> map) {
        ArrayList<Integer> guardLocation = locateGuard(map);              //finds the guard's current location
        if (map.get(guardLocation.get(0) - 1).get(1).equals(".")) {      //accesses the element above the guard
            map.get(guardLocation.get(0) - 1).set(guardLocation.get(1), "^");
            map.get(guardLocation.get(0)).set(guardLocation.get(1), "X");
            return true;
        } else if (guardLocation.get(0) - 1 < 0) {
            map.get(guardLocation.get(0)).set(guardLocation.get(1), "X");
            return true;
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