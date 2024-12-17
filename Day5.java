import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day5 {
    public static void main(String[] args) {

        ArrayList<String> fileData = getFileData("src/Day5Input.txt");
        //ArrayList<String> fileData = getFileData("src/exampleText");
        System.out.println(fileData);

        ArrayList<String> rules = new ArrayList<>();
        ArrayList<String> updates = new ArrayList<>();
        ArrayList<ArrayList<Integer>> numUpdates = new ArrayList<>();

        for (String fileDatum : fileData) {
            if (fileDatum.contains("|")) {
                rules.add(fileDatum);
            } else {
                updates.add(fileDatum);
            }
        }

        for (int i = 0; i < updates.size(); i++) {
            ArrayList<Integer> update = new ArrayList<>();
            String[] splitUpdate = updates.get(i).split(",");
            for (int j = 0; j < splitUpdate.length; j++) {
                update.add(Integer.parseInt(splitUpdate[j]));
            }
            numUpdates.add(update);
        }

        System.out.println("Rules: " + rules);
        System.out.println();
        System.out.println("Updates: " + numUpdates);


        int answerForValids = 0;
        int answerForInvalids = 0;

        for (int i = 0; i < numUpdates.size(); i++) {
            if (checkUpdateValidity(rules, numUpdates.get(i))) {
                answerForValids += returnMiddleNum(numUpdates.get(i));
            }
        }

        /*for (int i = 0; i < numUpdates.size(); i++) {
            ArrayList<Integer> validateNumUpdate = numUpdates.get(i);
            while (!checkUpdateValidity(rules, numUpdates.get(i))) {

            }
            if (!checkUpdateValidity(rules, numUpdates.get(i))) {
                answerForValids += returnMiddleNum(numUpdates.get(i));
            }
        }*/



        String s = " 75|49";
        System.out.println(s.indexOf(String.valueOf(75)));

        System.out.println("Your answer to Advent 2024 Day 5 Part 1 is: " + answerForValids);
        System.out.println("Your answer to Advent 2024 Day 5 Part 2 is: " + answerForInvalids);
        // you now have an ArrayList of Strings of the rules and updates in the file
        // do Advent 2024 day 5!
        System.out.println(parseRules(75, rules));
    }

    //parse out rules for specific number
    //run only those rules through checkUpdateValidity
    public static ArrayList<String> parseRules(int num, ArrayList<String> rules) {
        ArrayList<String> specificRules = new ArrayList<>();
        for (int i = 0; i < rules.size(); i++) {
            if (rules.get(i).contains(String.valueOf(num))) {
                specificRules.add(rules.get(i));
            }
        }
        return specificRules;
    }

    public static Boolean checkUpdateValidity(ArrayList<String> rules, ArrayList<Integer> numUpdate) {
        boolean isInvalid = false;
        for (int i = 0; i < numUpdate.size(); i++) {  //looks at each number in one line of numUpdates
            int chosenNumber = numUpdate.get(i);      //number indexed with "i" is the chosen number **********
            for (int j = 0; j < rules.size(); j++) {  //looks at each rule in the rules arrayList; searches for ones with the number indexed with i; identifies the number's index relative to the "|";
                String rule = rules.get(j);           //focuses on the rule in question
                int barIndex = rule.indexOf("|");     //finds the index of the bar
                int chosenNumberIndex = rule.indexOf(String.valueOf(chosenNumber));      //index of the chosen number **********
                System.out.println("j's index: " + j + " " + rule.indexOf(String.valueOf(chosenNumber)) + " " + chosenNumber + " in " + rule);
                if (chosenNumberIndex != -1) {
                    int otherNumberIndex = 0;              //other number index initialized **********
                    if (chosenNumberIndex == 0) {
                        otherNumberIndex = barIndex + 1;     //updates the index of the other number if necessary
                    }
                    int otherNumber = Integer.parseInt(rule.substring(otherNumberIndex, otherNumberIndex + 2)); //other number **********
                    if (numUpdate.contains(otherNumber)) {            //need to check if our numUpdate line has the other number in the rule and if not then we ignore the rule entirely
                        //System.out.println("Index Battle: " + chosenNumberIndex + " " + otherNumberIndex);
                        if (chosenNumberIndex < otherNumberIndex && numUpdate.indexOf(chosenNumber) > numUpdate.indexOf(otherNumber)) {
                            isInvalid = true;
                        } else if (chosenNumberIndex > otherNumberIndex && numUpdate.indexOf(chosenNumber) < numUpdate.indexOf(otherNumber)) {
                            isInvalid = true;
                        }
                    }
                    //System.out.println(rule + " " + !isInvalid + " Chosen Number: " + chosenNumber);
                }
            }
            //System.out.println(chosenNumber + " " + !isInvalid);
        }
        //System.out.println(" wh" + !isInvalid);
        return !isInvalid;
    }
    //returns true if valid and false if not

    public static int returnMiddleNum(ArrayList<Integer> numUpdate) {    //assuming a valid numUpdate is supplied, returns the middle number in that numUpdate
        int middleNum = numUpdate.size() / 2;
        return numUpdate.get(middleNum);
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