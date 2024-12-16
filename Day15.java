import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day15 {
    public static void main(String[] args) {

        ArrayList<String> fileData = getFileData("src/Day15Input.txt");
        System.out.println(fileData);

        //------------------------------------------------------------------PARSED DATA
        ArrayList<String> warehouse = new ArrayList<>();
        ArrayList<String> listOfMovement = new ArrayList<>();

        for (String fileDatum : fileData) {
            if (fileDatum.contains("#")) {
                warehouse.add(fileDatum);
            } else {
                listOfMovement.add(fileDatum);
            }
        }

        System.out.println("Warehouse: " + warehouse);
        System.out.println();
        System.out.println("Instructions: " + listOfMovement);
        //------------------------------------------------------------------PARSED DATA

        //------------------------------------------------------------------CONVERTED VARIABLES
        ArrayList<ArrayList<String>> warehouseMaze = new ArrayList<>();
        String instructions = "";

        for (String s : warehouse) {
            warehouseMaze.add(convertWordToStrings(s));
        }

        for (String movement : listOfMovement) {
            instructions += movement;
        }

        //non-array warehouse
        for (String s : warehouse) {
            System.out.println(s);
        }
        System.out.println();
        //array warehouse
        for (ArrayList<String> strings : warehouseMaze) {
            System.out.println(strings);
        }
        System.out.println();
        //string of instructions for robot's list of movement
        System.out.println(instructions);
        //------------------------------------------------------------------CONVERTED VARIABLES


        // you now have an ArrayList of Strings of the warehouse and list of movement in the file
        // do Advent 2024 day 15!
    }

    //completes a set of instructions given in the parameters
    public static void robotMove(String instructions, ArrayList<ArrayList<String>> warehouseMaze) {
        for (int i = 0; i < instructions.length(); i++) {
            if (instructions.charAt(i) == '<') {

            } else if (instructions.charAt(i) == '>') {

            } else if (instructions.charAt(i) == '^') {

            } else if (instructions.charAt(i) == 'v') {

            } else {
                System.out.println("Invalid Input!");
            }
        }
    }

    public static void moveLeft() {

    }

    public static void moveRight() {

    }

    public static void moveUp() {

    }

    public static void moveDown() {

    }

    public static ArrayList<String> convertWordToStrings(String word) {
        ArrayList<String> strings = new ArrayList<String>();
        for (int i = 0; i < word.length(); i++) {
            strings.add(word.substring(i, i + 1));
        }
        return strings;
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