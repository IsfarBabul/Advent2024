import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day5 {
    public static void main(String[] args) {

        //ArrayList<String> fileData = getFileData("src/Day5Input.txt");
        ArrayList<String> fileData = getFileData("src/exampleText");
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

        for (int i = 0; i < numUpdates.size(); i++) {                     //looks through each numUpdates
            //captures the original instance of numUpdate for iteration through each number later
            ArrayList<Integer> validateNumUpdate = new ArrayList<>(numUpdates.get(i));     //warning: do not simply set this new variable equal to the old one or else you get an issue with references
            if (!checkUpdateValidity(rules, numUpdates.get(i))) {         //ensures we are finding incorrect numUpdate lines
                System.out.println("Incorrect: " + validateNumUpdate);
                ArrayList<String> relevantRules = createRelevantRules(numUpdates.get(i), rules);
                System.out.println("Rel rules: " + relevantRules);
                for (int j = 0; j < numUpdates.get(i).size(); j++) {                                   //use this array to iterate through all numbers in this array so we apply the rules of each number in each numUpdate line
                    ArrayList<String> specificRules = parseRules(numUpdates.get(i).get(j), rules);     //identify the rules to see if our array conforms with them
                    System.out.println(specificRules);
                    int count = 0;
                    while (!checkUpdateValidity(specificRules, validateNumUpdate)) {   //checks the validity of our array after the changes
                        moveNum(numUpdates.get(i).get(j), validateNumUpdate);
                        System.out.println(validateNumUpdate);
                        //count++;
                    }
                    System.out.println(j);
                }
                System.out.println("Final: " + validateNumUpdate);
                answerForInvalids += returnMiddleNum(validateNumUpdate);
            }
        }

        int answerForCorrected = 0;

        for (int i = 0; i < numUpdates.size(); i++) {
            ArrayList<String> relevantRules = createRelevantRules(numUpdates.get(i), rules);
            answerForCorrected += findMiddleNum(numUpdates.get(i), relevantRules);
        }

        //ArrayList<String> relevantRules = createRelevantRules(numUpdates.get(i));


        /*ArrayList<Integer> test = new ArrayList<>();          //moveNum test
        test.add(5);
        test.add(6);
        test.add(8);
        test.add(13);
        for (int i = 0; i < 34; i++) {
            moveNum(6, test);
            System.out.println(i + " " + test);
        }*/

        String s = " 75|49";
        System.out.println(s.indexOf(String.valueOf(75)));

        System.out.println("Your answer to Advent 2024 Day 5 Part 1 is: " + answerForValids);
        System.out.println("Your answer to Advent 2024 Day 5 Part 2 is: " + answerForCorrected);
        // you now have an ArrayList of Strings of the rules and updates in the file
        // do Advent 2024 day 5!
        System.out.println(parseRules(75, rules));
    }

    public static void moveNum(int num, ArrayList<Integer> numUpdate) {
        int index = numUpdate.indexOf(num);                                                       //1 2 3
        if (index + 1 != numUpdate.size()) {
            numUpdate.add(index + 1, numUpdate.remove(index));
        } else {
            numUpdate.addFirst(numUpdate.remove(index));
        }
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

    public static ArrayList<String> parseRelevantRules(ArrayList<Integer> numUpdate, ArrayList<String> rules) {
        ArrayList<String> specificRules = new ArrayList<>();
        for (int i = 0; i < rules.size(); i++) {
            for (int j = 0; j < numUpdate.size(); j++) {
                if (rules.get(i).contains(String.valueOf(numUpdate.get(j)))) {
                    boolean duplicate = false;
                    for (int k = 0; k < specificRules.size(); k++) {
                        if (specificRules.get(k).equals(rules.get(i))) {
                            duplicate = true;
                        }
                    }
                    if (!duplicate) {
                        specificRules.add(rules.get(i));
                    }
                }
            }
        }
        return specificRules;
    }

    public static ArrayList<String> createRelevantRules(ArrayList<Integer> numUpdate, ArrayList<String> rules) {
        ArrayList<String> relevantRules = new ArrayList<>();
        for (int i = 0; i < numUpdate.size(); i++) {
            for (int j = 0; j < numUpdate.size(); j++) {
                if (numUpdate.get(i) != numUpdate.get(j)) {
                    String newRule = numUpdate.get(i) + "|" + numUpdate.get(j);
                    for (int k = 0; k < rules.size(); k++) {
                        if (newRule.equals(rules.get(k))) {
                            relevantRules.add(numUpdate.get(i) + "|" + numUpdate.get(j));
                        }
                    }
                }
            }
        }
        return relevantRules;
    }

    public static int findMiddleNum(ArrayList<Integer> numUpdate, ArrayList<String> relevantRules) {
        int middleNum = 0;
        System.out.println(numUpdate);
        for (int i = 0; i < numUpdate.size(); i++) {
            int appearsLeft = 0;
            int appearsRight = 0;
            for (int j = 0; j < relevantRules.size(); j++) {
                if (relevantRules.get(j).indexOf(numUpdate.get(i)) == 0) {
                    appearsLeft++;
                }
                if (relevantRules.get(j).indexOf(numUpdate.get(i)) == 3) {
                    appearsRight++;
                }
            }
            if(appearsLeft == appearsRight) {
                middleNum = numUpdate.get(i);
                System.out.println(appearsLeft + " " + appearsRight + "MIDDLE NUMBER: " + numUpdate.get(i));
            }
        }
        return middleNum;
    }

    //61|13, 61|29, 29|13
    //61|13
    //61|29, 29|13
    //numUpdate to identify the numbers (may not need)
    //relevantRules takes the rules and fits them together
    //masterRules is the 2d arraylist that gets returned
    public static ArrayList<ArrayList<String>> returnAllChainOfRules(ArrayList<String> relevantRules, ArrayList<String> chainOfRules, ArrayList<ArrayList<String>> masterRules) {
        if (chainOfRules.isEmpty()) {
            for (int i = 0; i < relevantRules.size(); i++) {
                chainOfRules.add(relevantRules.get(i));   //branch starts off with each rule as the start
                returnAllChainOfRules(relevantRules, chainOfRules, masterRules);
            }
        } else {
            for (int i = 0; i < relevantRules.size(); i++) {
                if (relevantRules.get(i).substring(3).equals(chainOfRules.getLast().substring(0, 2))) {  //compares the second number of the last rule in chainOfRules with the first number of the rule we're looking to add
                    chainOfRules.add(relevantRules.get(i));
                    returnAllChainOfRules(relevantRules, chainOfRules, masterRules);
                }
                masterRules.add(chainOfRules);    //if none are found then the chain has ended; we add the entire chain of rules to masterRules
                chainOfRules = new ArrayList<>();
            }
            return masterRules;
        }


        for (int i = 0; i < relevantRules.size(); i++) {
            chainOfRules.add(relevantRules.get(i));
            for (int j = 0; j < relevantRules.size(); j++) {
                if (relevantRules.get(i).substring(3).equals(relevantRules.get(j).substring(0, 2))) {

                }
            }
        }

        /*ArrayList<String> longestChainRule = new ArrayList<>();
        for (int i = 0; i < masterRules.size(); i++) {
            if (masterRules.get(i).size() > longestChainRule.size()) {
                longestChainRule = new ArrayList<>(masterRules.get(i));
            }
        }*/
        return masterRules;
    }

    public static Boolean checkUpdateValidity(ArrayList<String> rules, ArrayList<Integer> numUpdate) {
        boolean isInvalid = false;
        for (int i = 0; i < numUpdate.size(); i++) {  //looks at each number in one line of numUpdates
            int chosenNumber = numUpdate.get(i);      //number indexed with "i" is the chosen number **********
            for (int j = 0; j < rules.size(); j++) {  //looks at each rule in the rules arrayList; searches for ones with the number indexed with i; identifies the number's index relative to the "|";
                String rule = rules.get(j);           //focuses on the rule in question
                int barIndex = rule.indexOf("|");     //finds the index of the bar
                int chosenNumberIndex = rule.indexOf(String.valueOf(chosenNumber));      //index of the chosen number **********
                //System.out.println("j's index: " + j + " " + rule.indexOf(String.valueOf(chosenNumber)) + " " + chosenNumber + " in " + rule);
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
                            break;
                        } else if (chosenNumberIndex > otherNumberIndex && numUpdate.indexOf(chosenNumber) < numUpdate.indexOf(otherNumber)) {
                            isInvalid = true;
                            break;
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