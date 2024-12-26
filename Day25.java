import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Day25 {
    public static void main(String[] args) {

        ArrayList<String> fileData = getFileData("src/Day25Input.txt");
        //ArrayList<String> fileData = getFileData("src/Day25ExampleText");
        System.out.println(fileData);
        // you now have an ArrayList of locks and keys in the file
        // do Advent 2024 day 25!

        ArrayList<ArrayList<ArrayList<String>>> locksAndKeys = new ArrayList<>();    //make the 3D arrayList of locks and keys
        constructArrayOfLocksAndKeys(fileData, locksAndKeys);

        //printAllLocksAndKeys(locksAndKeys);    //prints out all the locks and keys in array form

        ArrayList<ArrayList<ArrayList<String>>> locks = new ArrayList<>();
        ArrayList<ArrayList<ArrayList<String>>> keys = new ArrayList<>();
        separateIntoLocksOrKeys(locksAndKeys, locks, keys);

        System.out.println("Locks: ");
        printAllLocksAndKeys(locks);         //prints out all the locks in array form

        System.out.println("Keys: ");       //prints out all the keys in array form
        printAllLocksAndKeys(keys);


        ArrayList<ArrayList<Integer>> lockHeights = new ArrayList<>();
        produceLockHeights(locks, lockHeights);
        ArrayList<ArrayList<Integer>> keyHeights = new ArrayList<>();
        produceKeyHeights(keys, keyHeights);

        System.out.println("Lock Heights: ");
        printAllLockOrKeyHeights(lockHeights);         //prints out all the lock heights in array form

        System.out.println("Key Heights: ");       //prints out all the key heights in array form
        printAllLockOrKeyHeights(keyHeights);


        /* THE asList() TEST

        ArrayList<String> margin = new ArrayList<>(Arrays.asList("#", "#", "#", "#", "#"));

        ArrayList<String> margin2 = new ArrayList<>();
        margin2.add("#");
        margin2.add("#");
        margin2.add("#");
        margin2.add("#");
        margin2.add("#");

        System.out.println(compareArrayListContentsOfStrings(margin, margin2));
        System.out.println(margin + " " + margin2);*/

        System.out.println("The answer to Advent 2024 Day 25 Part 1 is: " + countHowManyFit(lockHeights, keyHeights));
        System.out.println("The answer to Advent 2024 Day 25 Part 2 is: ");
    }

    public static ArrayList<String> breakUpStrings(String line) {
        ArrayList<String> brokenLine = new ArrayList<>();
        for (int i = 0; i < line.length(); i++) {
            brokenLine.add(line.substring(i, i + 1));
        }
        return brokenLine;
    }

    public static void constructArrayOfLocksAndKeys(ArrayList<String> fileData, ArrayList<ArrayList<ArrayList<String>>> locksAndKeys) {
        for (int i = 0; i < fileData.size(); i += 7) {
            ArrayList<ArrayList<String>> lockOrKey = new ArrayList<>();
            for (int j = i; j < i + 7; j++) {
                lockOrKey.add(breakUpStrings(fileData.get(j)));
            }
            locksAndKeys.add(lockOrKey);
        }
    }

    public static void separateIntoLocksOrKeys(ArrayList<ArrayList<ArrayList<String>>> locksAndKeys, ArrayList<ArrayList<ArrayList<String>>> locks, ArrayList<ArrayList<ArrayList<String>>> keys) {
        ArrayList<String> margin = new ArrayList<>(Arrays.asList("#", "#", "#", "#", "#"));
        for (int i = 0; i < locksAndKeys.size(); i++) {
            if (compareArrayListContentsOfStrings(locksAndKeys.get(i).getFirst(), margin)) {    //Is it a lock?
                locks.add(locksAndKeys.get(i));
            } else if (compareArrayListContentsOfStrings(locksAndKeys.get(i).getLast(), margin)) {   //Is it a key?
                keys.add(locksAndKeys.get(i));
            } else {                                 //Is it neither? Hopefully, not!
                System.out.println("This is neither a lock nor a key. Hopefully, this message never gets printed to the console!");
            }
        }
    }

    public static ArrayList<Integer> identifyKeyHeight(ArrayList<ArrayList<String>> key) {
        ArrayList<Integer> keyHeight = new ArrayList<>();
        for (int i = 0; i < key.getFirst().size(); i++) {
            int count = 0;
            for (int j = 0; j < key.size() - 1; j++) {
                if (key.get(j).get(i).equals("#")) {
                    count++;
                }
            }
            keyHeight.add(count);
        }
        return keyHeight;
    }

    public static void produceKeyHeights(ArrayList<ArrayList<ArrayList<String>>> keys, ArrayList<ArrayList<Integer>> keyHeights) {
        for (int i = 0; i < keys.size(); i++) {
            keyHeights.add(identifyKeyHeight(keys.get(i)));
        }
    }

    public static ArrayList<Integer> identifyLockHeight(ArrayList<ArrayList<String>> lock) {
        ArrayList<Integer> lockHeight = new ArrayList<>();
        for (int i = 0; i < lock.getFirst().size(); i++) {
            int count = 0;
            for (int j = 1; j < lock.size(); j++) {
                if (lock.get(j).get(i).equals("#")) {
                    count++;
                }
            }
            lockHeight.add(count);
        }
        return lockHeight;
    }

    public static void produceLockHeights(ArrayList<ArrayList<ArrayList<String>>> locks, ArrayList<ArrayList<Integer>> lockHeights) {
        for (int i = 0; i < locks.size(); i++) {
            lockHeights.add(identifyLockHeight(locks.get(i)));
        }
    }

    public static int countHowManyFit(ArrayList<ArrayList<Integer>> lockHeights, ArrayList<ArrayList<Integer>> keyHeights) {
        int count = 0;
        for (int i = 0; i < lockHeights.size(); i++) {
            for (int j = 0; j < keyHeights.size(); j++) {
                if (checkForFit(lockHeights.get(i), keyHeights.get(j))) {
                    count++;
                }
            }
        }
        return count;
    }

    public static boolean checkForFit(ArrayList<Integer> lockHeight, ArrayList<Integer> keyHeight) {
        boolean fitCheck = true;
        for (int i = 0; i < lockHeight.size(); i++) {
            if (lockHeight.get(i) + keyHeight.get(i) > 5) {
                fitCheck = false;
                break;
            }
        }
        return fitCheck;
    }

    /*
        1. Look at every 7 lines
        2. Store them into a 2D array
        3. Store the 2D array into a 3D array
     */


    //UTILITY METHODS
    public static void printLockOrKey(ArrayList<ArrayList<String>> lockOrKey) {
        for (int i = 0; i < lockOrKey.size(); i++) {
            System.out.println(lockOrKey.get(i));
        }
    }

    public static void printAllLocksAndKeys(ArrayList<ArrayList<ArrayList<String>>> locksAndKeys) {
        for (int i = 0; i < locksAndKeys.size(); i++) {
            printLockOrKey(locksAndKeys.get(i));
            System.out.println();
        }
    }

    public static void printAllLockOrKeyHeights(ArrayList<ArrayList<Integer>> lockOrKeyHeights) {
        for (int i = 0; i < lockOrKeyHeights.size(); i++) {
            System.out.println(lockOrKeyHeights.get(i));
            System.out.println();
        }
    }

    public static boolean compareArrayListContentsOfStrings(ArrayList<String> arrayList1, ArrayList<String> arrayList2) {
        if (arrayList1.size() != arrayList2.size()) {
            return false;
        }
        for (int i = 0; i < arrayList1.size(); i++) {
            if (!arrayList1.get(i).equals(arrayList2.get(i))) {
                return false;
            }
        }
        return true;
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