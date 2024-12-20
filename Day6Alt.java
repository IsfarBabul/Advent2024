


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Day6Alt {
    public static void main(String[] args) {

        ArrayList<String> fileData = getFileData("src/Day6Input.txt");
        //ArrayList<String> fileData = getFileData("src/exampleText");
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
        ArrayList<ArrayList<String>> unModifiedMap = new ArrayList<>();
        System.out.println("MAP SIZEEEEEEEEEEEEEE: " + map.size());
        for (int j = 0; j < map.size(); j++) {
            unModifiedMap.add(new ArrayList<>(map.get(j)));
        }
        System.out.println("MAP SIZEEEEEEEEEEEEEEz: " + unModifiedMap.size());
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


        //--------------------------------------------------------------------PART 1
        guardMovement(map, guardLocations);

        int count = 0;

        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.getFirst().size(); j++) {
                if (map.get(i).get(j).equals("X")) {
                    count++;
                }
            }
        }

        System.out.println("Your answer to Advent 2024 Day 6 Part 1 is: " + count);
        //--------------------------------------------------------------------PART 1

        //--------------------------------------------------------------------PART 2
        int infiniteLoopCount = 0;

        ArrayList<ArrayList<Integer>> originalGuardLocations = new ArrayList<>();
        for (int j = 0; j < guardLocations.size(); j++) {
            originalGuardLocations.add(new ArrayList<>(guardLocations.get(j)));
        }

        ArrayList<ArrayList<Integer>> infiniteLoopGuardLocations = new ArrayList<>();

        for (int i = 0; i < originalGuardLocations.size(); i++) {    //go through each location the guard was in
            guardLocations = new ArrayList<>();
            //System.out.println("Orig: " + originalGuardLocations);
            ArrayList<ArrayList<String>> newMap = new ArrayList<>();
            //System.out.println("NEW MAP: " + newMap + unModifiedMap.size());
            for (int j = 0; j < unModifiedMap.size(); j++) {
                newMap.add(new ArrayList<>(unModifiedMap.get(j)));
            }
            //System.out.println("NEW MAP AFTER: " + newMap);
            //System.out.println(newMap);
            //System.out.println(originalGuardLocations.get(i).get(0) + ", " + originalGuardLocations.get(i).get(1));
            newMap.get(originalGuardLocations.get(i).get(0)).set(originalGuardLocations.get(i).get(1), "#");
            /*for (int j = 0; j < unModifiedMap.size(); j++) {
                System.out.println(unModifiedMap.get(j));        //print out unmodified map
            }*/
            /*if (guardMovement(newMap, guardLocations)) {
                infiniteLoopCount++;
                boolean loopInsertedAlready = false;
                for (int j = 0; j < infiniteLoopGuardLocations.size(); j++) {
                    if (compareLocations(infiniteLoopGuardLocations.get(j), originalGuardLocations.get(i))) {
                        loopInsertedAlready = true;
                    }
                }
                if (!loopInsertedAlready) {
                    infiniteLoopGuardLocations.add(originalGuardLocations.get(i));
                }
            }*/
        }



        System.out.println("Your answer to Advent 2024 Day 6 Part 2 is: " + infiniteLoopGuardLocations.size());
        //--------------------------------------------------------------------PART 2

        ArrayList<ArrayList<Integer>> guardLocation = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            ArrayList<Integer> loc = new ArrayList<>();
            loc.add(i);
            loc.add(i + 1);
            guardLocation.add(loc);
        }
        ArrayList<Integer> loc = new ArrayList<>();
        loc.add(1);
        loc.add(1 + 1);
        guardLocation.add(loc);
        loc = new ArrayList<>();
        loc.add(2);
        loc.add(2 + 1);
        guardLocation.add(loc);
        System.out.println(guardLocation);
        System.out.println(checkForInfiniteLoop(guardLocation));

        System.out.println(infiniteLoopGuardLocations);


        // you now have an ArrayList of Strings for the map in the file
        // do Advent 2024 day 6!
    }

    //--------------------------------------------------------------------------------------------------------------------MOVES THE GUARD COMPLETELY THROUGH ONE ITERATION OF A MAP
    public static void guardMovement(ArrayList<ArrayList<String>> map, ArrayList<ArrayList<Integer>> guardLocations) {   //returns true if an infinite loop was found and false otherwise
        boolean isGuardPresent = true;
        while (isGuardPresent) {
            move(map, guardLocations);
            /*for (int i = 0; i < map.size(); i++) {
                System.out.println(map.get(i));
            }*/
            //System.out.println(guardLocations);
            //System.out.println();
            ArrayList<Integer> guardLocation = locateGuard(map);
            if (guardLocation.getFirst() == -1) {
                isGuardPresent = false;
            }
        }
    }

    //--------------------------------------------------------------------------------------------------------------------MOVES THE GUARD BASED ON WHERE IT IS FACING
    public static void move(ArrayList<ArrayList<String>> map, ArrayList<ArrayList<Integer>> guardLocations) {
        String guardDirection = identifyGuardDirection(map);
        boolean moveSuccess = false;
        switch (guardDirection) {
            case "^" -> moveSuccess = moveUp(map);
            case ">" -> moveSuccess = moveRight(map);
            case "v" -> moveSuccess = moveDown(map);
            case "<" -> moveSuccess = moveLeft(map);
        }

        //System.out.println("Move Success: " + moveSuccess);

        if (!moveSuccess) {
            //System.out.println(identifyGuardDirection(map));
            changeGuardDirection(map);
            //System.out.println(identifyGuardDirection(map));
        } else {
            if (locateGuard(map).get(1) != -1) {
                guardLocations.add(locateGuard(map));
            }
        }
        //return checkForInfiniteLoop(guardLocations);
    }

    //--------------------------------------------------------------------------------------------------------------------CHECKS TO SEE IF AN INFINITE LOOP HAS FORMED
    public static boolean checkForInfiniteLoop(ArrayList<ArrayList<Integer>> guardLocations) {   //TODO: CHANGE THIS TO COMPARE TO THE CURRENT MOVEMENT
        for (int i = 0; i < guardLocations.size(); i++) {                                                        //searches for a first coordinate location
            for (int j = i + 1; j < guardLocations.size(); j++) {                                                //searches for a second coordinate location after the position for the first coordinate location
                if (compareLocations(guardLocations.get(i), guardLocations.get(j))) {                            //makes sure that both coordinate locations are equal to each other
                    if (j + 1 != guardLocations.size() && compareLocations(guardLocations.get(i + 1), guardLocations.get(j + 1))) {    //checks to see if the subsequent locations are equal to indicate the guard is moving in the same direction which causes an infinite loop
                        return true;               //this line will not run if there is no subsequent location after the second coordinate location so that the program doesn't break
                    }
                }
            }
        }
        return false;
    }

    //--------------------------------------------------------------------------------------------------------------------COMPARES THE VALUES OF TWO LOCATIONS TO SEE IF THEY ARE THE SAME LOCATION
    public static boolean compareLocations(ArrayList<Integer> guardLocation1, ArrayList<Integer> guardLocation2) {
        for (int i = 0; i < guardLocation1.size(); i++) {
            if (Objects.equals(guardLocation1.get(0), guardLocation2.get(0)) && Objects.equals(guardLocation1.get(1), guardLocation2.get(1))) {
                return true;
            }
        }
        return false;
    }

    //--------------------------------------------------------------------------------------------------------------------DETERMINES WHICH WAY THE GUARD IS FACING
    public static String identifyGuardDirection(ArrayList<ArrayList<String>> map) {
        ArrayList<Integer> guardLocation = locateGuard(map);
        if (guardLocation.get(0) != -1) {
            return map.get(guardLocation.get(0)).get(guardLocation.get(1));
        }
        return "";
    }

    //--------------------------------------------------------------------------------------------------------------------CHANGES WHICH WAY THE GUARD FACES
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
        if (guardLocation.get(0) != -1) {
            map.get(guardLocation.get(0)).set(guardLocation.get(1), guard[guardIndex]);
        }
    }

    //--------------------------------------------------------------------------------------------------------------------LOCATES WHERE THE GUARD IS
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

    //--------------------------------------------------------------------------------------------------------------------MOVES THE GUARD UP
    public static boolean moveUp(ArrayList<ArrayList<String>> map) {
        ArrayList<Integer> guardLocation = locateGuard(map);              //finds the guard's current location
        if (guardLocation.get(0) - 1 < 0) {
            //System.out.println("Mount");
            map.get(guardLocation.get(0)).set(guardLocation.get(1), "X");
            return true;
        } else if (map.get(guardLocation.get(0) - 1).get(guardLocation.get(1)).equals(".") || map.get(guardLocation.get(0) - 1).get(guardLocation.get(1)).equals("X")) {      //accesses the element above the guard
            //System.out.println("Elo");
            map.get(guardLocation.get(0) - 1).set(guardLocation.get(1), "^");
            map.get(guardLocation.get(0)).set(guardLocation.get(1), "X");
            return true;
        }
        return false;
    }

    //--------------------------------------------------------------------------------------------------------------------MOVES THE GUARD DOWN
    public static boolean moveDown(ArrayList<ArrayList<String>> map) {
        ArrayList<Integer> guardLocation = locateGuard(map);              //finds the guard's current location
        if (guardLocation.get(0) + 1 >= map.size()) {
            //System.out.println("Mount");
            map.get(guardLocation.get(0)).set(guardLocation.get(1), "X");
            return true;
        } else if (map.get(guardLocation.get(0) + 1).get(guardLocation.get(1)).equals(".") || map.get(guardLocation.get(0) + 1).get(guardLocation.get(1)).equals("X")) {      //accesses the element above the guard
            //System.out.println("Elo");
            map.get(guardLocation.get(0) + 1).set(guardLocation.get(1), "v");
            map.get(guardLocation.get(0)).set(guardLocation.get(1), "X");
            return true;
        }
        return false;
    }

    //--------------------------------------------------------------------------------------------------------------------MOVES THE GUARD LEFT
    public static boolean moveLeft(ArrayList<ArrayList<String>> map) {
        ArrayList<Integer> guardLocation = locateGuard(map);              //finds the guard's current location
        if (guardLocation.get(1) - 1 < 0) {
            //System.out.println("Mount");
            map.get(guardLocation.get(0)).set(guardLocation.get(1), "X");
            return true;
        } else if (map.get(guardLocation.get(0)).get(guardLocation.get(1) - 1).equals(".") || map.get(guardLocation.get(0)).get(guardLocation.get(1) - 1).equals("X")) {      //accesses the element above the guard
            //System.out.println("Elo");
            map.get(guardLocation.get(0)).set(guardLocation.get(1) - 1, "<");
            map.get(guardLocation.get(0)).set(guardLocation.get(1), "X");
            return true;
        }
        return false;
    }

    //--------------------------------------------------------------------------------------------------------------------MOVES THE GUARD RIGHT
    public static boolean moveRight(ArrayList<ArrayList<String>> map) {
        ArrayList<Integer> guardLocation = locateGuard(map);              //finds the guard's current location
        //System.out.println(guardLocation.get(1));
        //System.out.println(map.getFirst().size());
        //System.out.println(guardLocation.get(1) + 1 >= map.getFirst().size());
        //System.out.println(map.get(guardLocation.get(0)).get(guardLocation.get(1) + 1).equals("."));
        if (guardLocation.get(1) + 1 >= map.getFirst().size()) {     //map.getFirst() is arbitrary as any array size would do since this is a regular array
            //System.out.println("Mount");
            map.get(guardLocation.get(0)).set(guardLocation.get(1), "X");
            return true;
        } else if (map.get(guardLocation.get(0)).get(guardLocation.get(1) + 1).equals(".") || map.get(guardLocation.get(0)).get(guardLocation.get(1) + 1).equals("X")) {      //accesses the element above the guard
            //System.out.println("Elo");
            map.get(guardLocation.get(0)).set(guardLocation.get(1) + 1, ">");
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

    //----------------------------------------------------METHOD BASEMENT
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

    public static boolean hypotheticalMap(ArrayList<ArrayList<String>> newMap, ArrayList<ArrayList<Integer>> guardLocations) {
        System.out.println("Hyp MAP");
        for (int i = 0; i < newMap.size(); i++) {
            System.out.println(newMap.get(i));
        }
        return true;
    }

}