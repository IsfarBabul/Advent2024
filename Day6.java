
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
        ArrayList<ArrayList<Integer>> guardLocations = new ArrayList<>();
        for (int i = 0; i < fileData.size(); i++) {
            ArrayList<String> mapLine = new ArrayList<>();
            for (int j = 0; j < fileData.get(i).length(); j++) {
                mapLine.add(fileData.get(i).substring(j, j + 1));
            }
            map.add(mapLine);
        }

        ArrayList<Integer> guardOriginalLocation = locateGuard(map);
        //System.out.println("Orig: " + guardOriginalLocation);

        /*for (int i = 0; i < map.size(); i++) {
            System.out.println(map.get(i));
        }
        System.out.println(locateGuard(map));
        for (int i = 0; i < 8; i++) {
            System.out.println(moveUp(map));
        }
        for (int i = 0; i < map.size(); i++) {
            System.out.println(map.get(i));
        }*/


        boolean isGuardPresent = true;
        //--------------------------------------------------------------------PART 1
        while (isGuardPresent) {
            move(map, guardLocations);
            for (int i = 0; i < map.size(); i++) {
                System.out.println(map.get(i));
            }
            System.out.println(guardLocations);
            System.out.println();
            ArrayList<Integer> guardLocation = locateGuard(map);
            if (guardLocation.getFirst() == -1) {
                isGuardPresent = false;
            }
        }

        int count = 0;

        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.getFirst().size(); j++) {
                if (map.get(i).get(j).equals("X")) {
                    count++;
                }
            }
        }
        //--------------------------------------------------------------------PART 1

        //--------------------------------------------------------------------PART 2
        int infiniteLoopCount = 0;

        for (int i = 0; i < guardLocations.size(); i++) {
            ArrayList<ArrayList<String>> newMap = new ArrayList<>(map);
            newMap.get(guardLocations.get(i).get(0)).set(guardLocations.get(i).get(1), "^");
            if(hypotheticalMap(newMap, guardLocations)) {
                infiniteLoopCount++;
            }
        }




        //--------------------------------------------------------------------PART 2

        System.out.println("Your answer to Advent 2024 Day 6 Part 1 is: " + count);
        System.out.println("Your answer to Advent 2024 Day 6 Part 2 is: " + infiniteLoopCount);


        // you now have an ArrayList of Strings for the map in the file
        // do Advent 2024 day 6!
    }

    public static boolean hypotheticalMap(ArrayList<ArrayList<String>> newMap, ArrayList<ArrayList<Integer>> guardLocations) {
        System.out.println("Hyp MAP");
        for (int i = 0; i < newMap.size(); i++) {
            System.out.println(newMap.get(i));
        }
        return true;
    }

    public static void move(ArrayList<ArrayList<String>> map, ArrayList<ArrayList<Integer>> guardLocations) {
        String guardDirection = identifyGuardDirection(map);
        boolean moveSuccess = false;
        switch (guardDirection) {
            case "^" -> moveSuccess = moveUp(map);
            case ">" -> moveSuccess = moveRight(map);
            case "v" -> moveSuccess = moveDown(map);
            case "<" -> moveSuccess = moveLeft(map);
        }

        System.out.println("Move Success: " + moveSuccess);

        if (!moveSuccess) {
            //System.out.println(identifyGuardDirection(map));
            changeGuardDirection(map);
            //System.out.println(identifyGuardDirection(map));
        } else {
            guardLocations.add(locateGuard(map));
        }
    }

    public static boolean laserSight(ArrayList<ArrayList<String>> map) {
        String guardDirection = identifyGuardDirection(map);
        boolean laserSightSuccess = false;
        switch (guardDirection) {
            case "^" -> laserSightSuccess = laserSightRight(map);
            case ">" -> laserSightSuccess = laserSightDown(map);
            case "v" -> laserSightSuccess = laserSightLeft(map);
            case "<" -> laserSightSuccess = laserSightUp(map);
        }
        return laserSightSuccess;
    }

    public static String identifyGuardDirection(ArrayList<ArrayList<String>> map) {
        ArrayList<Integer> guardLocation = locateGuard(map);
        if (guardLocation.get(0) != -1) {
            return map.get(guardLocation.get(0)).get(guardLocation.get(1));
        }
        return "";
    }

    public static void changeGuardDirection(ArrayList<ArrayList<String>> map) {
        String[] guard = {"^", ">", "v", "<"};
        int guardIndex = 0;
        for (int i = 0; i < guard.length; i++) {
            if (guard[i].equals(identifyGuardDirection(map))) {
                guardIndex = i;
            }
        }
        guardIndex++;
        if (guardIndex == guard.length) {
            guardIndex = 0;
        }
        ArrayList<Integer> guardLocation = locateGuard(map);
        map.get(guardLocation.get(0)).set(guardLocation.get(1), guard[guardIndex]);
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
        if (guardLocation.get(0) - 1 < 0) {
            System.out.println("Mount");
            map.get(guardLocation.get(0)).set(guardLocation.get(1), "X");
            return true;
        } else if (map.get(guardLocation.get(0) - 1).get(guardLocation.get(1)).equals(".") || map.get(guardLocation.get(0) - 1).get(guardLocation.get(1)).equals("X")) {      //accesses the element above the guard
            System.out.println("Elo");
            map.get(guardLocation.get(0) - 1).set(guardLocation.get(1), "^");
            map.get(guardLocation.get(0)).set(guardLocation.get(1), "X");
            return true;
        }
        return false;
    }

    public static boolean laserSightUp(ArrayList<ArrayList<String>> map) {
        ArrayList<Integer> guardLocation = locateGuard(map);
        boolean laserSightDetected = false;
        for (int i = guardLocation.get(0); i > 1; i--) {
            if (map.get(i).get(guardLocation.get(1)).equals("X") && map.get(i - 1).get(guardLocation.get(1)).equals("X")) {
                laserSightDetected = true;
                break;
            }
        }
        return laserSightDetected;
    }

    public static boolean moveDown(ArrayList<ArrayList<String>> map) {
        ArrayList<Integer> guardLocation = locateGuard(map);              //finds the guard's current location
        if (guardLocation.get(0) + 1 >= map.size()) {
            System.out.println("Mount");
            map.get(guardLocation.get(0)).set(guardLocation.get(1), "X");
            return true;
        } else if (map.get(guardLocation.get(0) + 1).get(guardLocation.get(1)).equals(".") || map.get(guardLocation.get(0) + 1).get(guardLocation.get(1)).equals("X")) {      //accesses the element above the guard
            System.out.println("Elo");
            map.get(guardLocation.get(0) + 1).set(guardLocation.get(1), "v");
            map.get(guardLocation.get(0)).set(guardLocation.get(1), "X");
            return true;
        }
        return false;
    }

    public static boolean laserSightDown(ArrayList<ArrayList<String>> map) {
        ArrayList<Integer> guardLocation = locateGuard(map);
        boolean laserSightDetected = false;
        for (int i = guardLocation.get(0); i < map.size() - 2; i++) {
            if (map.get(i).get(guardLocation.get(1)).equals("X") && map.get(i + 1).get(guardLocation.get(1)).equals("X")) {       //two X in a row means the guard has been there before but if those two are not connected to more X or a # then it means the path ends there; don't check if there's only 1 X in a row
                laserSightDetected = true;
                break;
            }
        }
        return laserSightDetected;
    }

    public static boolean moveLeft(ArrayList<ArrayList<String>> map) {
        ArrayList<Integer> guardLocation = locateGuard(map);              //finds the guard's current location
        if (guardLocation.get(1) != 0) {
            System.out.println(map.get(guardLocation.get(0)).get(guardLocation.get(1) - 1));
        }
        if (guardLocation.get(1) - 1 < 0) {
            System.out.println("Mount");
            map.get(guardLocation.get(0)).set(guardLocation.get(1), "X");
            return true;
        } else if (map.get(guardLocation.get(0)).get(guardLocation.get(1) - 1).equals(".") || map.get(guardLocation.get(0)).get(guardLocation.get(1) - 1).equals("X")) {      //accesses the element above the guard
            System.out.println("Elo");
            map.get(guardLocation.get(0)).set(guardLocation.get(1) - 1, "<");
            map.get(guardLocation.get(0)).set(guardLocation.get(1), "X");
            return true;
        }
        return false;
    }

    public static boolean laserSightLeft(ArrayList<ArrayList<String>> map) {
        ArrayList<Integer> guardLocation = locateGuard(map);
        boolean laserSightDetected = false;
        for (int i = guardLocation.get(1); i > 1; i--) {
            if (map.get(guardLocation.get(0)).get(i).equals("X") && map.get(guardLocation.get(0)).get(i - 1).equals("X")) {
                laserSightDetected = true;
                break;
            }
        }
        return laserSightDetected;
    }

    public static boolean moveRight(ArrayList<ArrayList<String>> map) {
        ArrayList<Integer> guardLocation = locateGuard(map);              //finds the guard's current location
        System.out.println(guardLocation.get(1));
        System.out.println(map.getFirst().size());
        System.out.println(guardLocation.get(1) + 1 >= map.getFirst().size());
        //System.out.println(map.get(guardLocation.get(0)).get(guardLocation.get(1) + 1).equals("."));
        if (guardLocation.get(1) + 1 >= map.getFirst().size()) {     //map.getFirst() is arbitrary as any array size would do since this is a regular array
            System.out.println("Mount");
            map.get(guardLocation.get(0)).set(guardLocation.get(1), "X");
            return true;
        } else if (map.get(guardLocation.get(0)).get(guardLocation.get(1) + 1).equals(".") || map.get(guardLocation.get(0)).get(guardLocation.get(1) + 1).equals("X")) {      //accesses the element above the guard
            System.out.println("Elo");
            map.get(guardLocation.get(0)).set(guardLocation.get(1) + 1, ">");
            map.get(guardLocation.get(0)).set(guardLocation.get(1), "X");
            return true;
        }
        return false;
    }

    public static boolean laserSightRight(ArrayList<ArrayList<String>> map) {
        ArrayList<Integer> guardLocation = locateGuard(map);
        boolean laserSightDetected = false;
        for (int i = guardLocation.get(1); i < map.getFirst().size() - 2; i++) {
            if (map.get(guardLocation.get(0)).get(i).equals("X") && map.get(guardLocation.get(0)).get(i + 1).equals("X")) {
                laserSightDetected = true;
                break;
            }
        }
        return laserSightDetected;
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
