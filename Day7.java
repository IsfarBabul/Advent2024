import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Day7 {
    public static void main(String[] args) {

        ArrayList<String> fileData = getFileData("src/Day7Input.txt");
        //System.out.println(fileData);   //FILE DATA VALUES


        ArrayList<Long> testValues = new ArrayList<>();
        ArrayList<ArrayList<Integer>> numbersForTestValues = new ArrayList<>();

        for (int i = 0; i < fileData.size(); i++) {
            //System.out.println(fileData.get(i));          //FILE DATA VALUES PER LINE
            testValues.add(Long.valueOf(fileData.get(i).substring(0, fileData.get(i).indexOf(":"))));
            String[] valuesToTest = fileData.get(i).substring(fileData.get(i).indexOf(":") + 2).split(" ");
            ArrayList<Integer> intValues = new ArrayList<>();
            for (int j = 0; j < valuesToTest.length; j++) {
                intValues.add(Integer.valueOf(valuesToTest[j]));
            }
            //System.out.println(valuesToTest);   //value IDs
            numbersForTestValues.add(intValues);
        }

        //System.out.println("INT VALUES: " + numbersForTestValues);      //array of arrays of number of each test value

        //System.out.println(testValues);    //array of test values

        // you now have an ArrayList of Strings for each number in the file
        // do Advent 2024 day 7!

        //a plan to do this

        //find out the number of combinations needed --> 2^(n-1) where n is 1 less than the number of numbers
        /*
        Example with 4 possible combinations
        0 --> + +
        1 --> + *
        2 --> * +System.out.println(testValues);
        3 --> * *
         */


        ArrayList<Integer> sampleArray = new ArrayList<>();
        sampleArray.add(5);
        sampleArray.add(12);
        sampleArray.add(2);
        sampleArray.add(5);
        sampleArray.add(5);
        sampleArray.add(12);
        sampleArray.add(2);
        sampleArray.add(5);
        System.out.println(zeroPadding(sampleArray.size() - 1, decimalToBinary(14)));
        System.out.println(operateOnNumbers(14, sampleArray));

        //System.out.println(wholeBinary(512));
        /*System.out.println(zeroPadding(25, decimalToBinary(17)));
        System.out.println(decimalToBinary(17));
        System.out.println("Wombo Combo: " + operateOnNumbers(3, sampleArray));*/

        System.out.println((int) Math.pow(2, 4));

        int numberOfCombinations = (int) Math.pow(2, sampleArray.size() - 1) - 1;
        System.out.println(numberOfCombinations);

        boolean test = determineTestValueSuccess(testValues.get(1), numbersForTestValues.get(1));

        for (int i = 0; i <= 24; i++) {
            System.out.println("" + i + determineTestValueSuccess(i, new ArrayList<>(Arrays.asList(2, 0, 3, 2))));
        }
        
        System.out.println("Test Answer: " + test);

        //System.out.println(testValues);
        //System.out.println(numbersForTestValues);


        for (int i = 0; i < 28; i++) {
            System.out.println(decimalToTrinary(i));
        }


        //-----------------------------------------------------------------------------------------
        long total = 0;
        for (int i = 0; i < testValues.size(); i++) {
            if (determineTestValueSuccess(testValues.get(i), numbersForTestValues.get(i))) {
                total += testValues.get(i);
            }
        }

        System.out.println("The answer for Advent 2024 Day 7 Part 1 is: " + total);
        //-----------------------------------------------------------------------------------------

        //-----------------------------------------------------------------------------------------
        long completeTotal = 0;
        for (int i = 0; i < testValues.size(); i++) {
            if (determineCompleteTestValueSuccess(testValues.get(i), numbersForTestValues.get(i))) {
                completeTotal += testValues.get(i);
            }
        }

        System.out.println("The answer for Advent 2024 Day 7 Part 2 is: " + completeTotal);
        //-----------------------------------------------------------------------------------------


    }

    public static boolean determineTestValueSuccess(long testValue, ArrayList<Integer> numbersForTestValue) {
        boolean foundSuccess = false;
        int numberOfCombinations = (int) Math.pow(2, numbersForTestValue.size() - 1) - 1;
        for (int i = numberOfCombinations; i >= 0; i--) {
            if (operateOnNumbers(i, numbersForTestValue) == testValue) {
                foundSuccess = true;
            }
        }
        return foundSuccess;
    }

    public static boolean determineCompleteTestValueSuccess(long testValue, ArrayList<Integer> numbersForTestValue) {
        boolean foundSuccess = false;
        int numberOfCombinations = (int) Math.pow(3, numbersForTestValue.size() - 1) - 1;
        for (int i = numberOfCombinations; i >= 0; i--) {
            if (completeOperateOnNumbers(i, numbersForTestValue) == testValue) {
                foundSuccess = true;
            }
        }
        return foundSuccess;
    }


    //finds the answer of the numbers using a specific combination
    public static long operateOnNumbers(int combinationNumber, ArrayList<Integer> arrayOfNumbers) {     //BIG DANGER: YOU NEED TO HAVE THIS TYPE LONG OR ELSE IT DOESN'T WORK
        long total = arrayOfNumbers.getFirst();
        ArrayList<Integer> binaryNumber = zeroPadding(arrayOfNumbers.size() - 1, decimalToBinary(combinationNumber));
        for (int i = 0; i < binaryNumber.size(); i++) {
            //System.out.println(binaryNumber.size());
            if (binaryNumber.get(i) == 1) {
                total *= arrayOfNumbers.get(i + 1); //MULTIPLICATION
            } else {
                total += arrayOfNumbers.get(i + 1); //ADDITION
            }
        }

        return total;
    }

    public static long completeOperateOnNumbers(int combinationNumber, ArrayList<Integer> arrayOfNumbers) {     //BIG DANGER: YOU NEED TO HAVE THIS TYPE LONG OR ELSE IT DOESN'T WORK
        long total = arrayOfNumbers.getFirst();
        ArrayList<Integer> trinaryNumber = zeroPadding(arrayOfNumbers.size() - 1, decimalToTrinary(combinationNumber));
        for (int i = 0; i < trinaryNumber.size(); i++) {
            if (trinaryNumber.get(i) == 2) {
                total = concatenateNumbers(total, arrayOfNumbers.get(i + 1)); //CONCATENATION
            } else if (trinaryNumber.get(i) == 1) {
                total *= arrayOfNumbers.get(i + 1); //MULTIPLICATION
            } else {
                total += arrayOfNumbers.get(i + 1); //ADDITION
            }
        }

        return total;
    }

    public static long concatenateNumbers(long num1, long num2) {                  //RETURN VALUE AS LONG IS A MUST
        String stringNum1 = String.valueOf(num1);
        String stringNum2 = String.valueOf(num2);
        String concatenatedNum = stringNum1 + stringNum2;
        return Long.parseLong(concatenatedNum);
    }


    //turns a decimal number into an arrayList with the digits to its binary number form
    public static ArrayList<Integer> decimalToBinary(int decimalNum) {
        ArrayList<Integer> binaryNum = new ArrayList<>();
        int maxBinary = 1;
        while (maxBinary * 2 <= decimalNum) {
            maxBinary *= 2;
        }
        while (maxBinary != 1) {
            if (decimalNum - maxBinary >= 0) {
                binaryNum.add(1);
                decimalNum -= maxBinary;
            } else {
                binaryNum.add(0);
            }
            maxBinary /= 2;
        }
        binaryNum.add(decimalNum);
        return binaryNum;
    }

    public static ArrayList<Integer> decimalToTrinary(int decimalNum) {
        ArrayList<Integer> binaryNum = new ArrayList<>();
        int maxBinary = 1;
        while (maxBinary * 3 <= decimalNum) {
            maxBinary *= 3;
        }
        while (maxBinary != 1) {
            int num = 0;
            while (decimalNum >= maxBinary) {
                decimalNum -= maxBinary;
                num++;
            }
            binaryNum.add(num);
            maxBinary /= 3;
        }
        binaryNum.add(decimalNum);
        return binaryNum;
    }


    //pads zeros in front of the binary arrayList since certain numbers require it
    public static ArrayList<Integer> zeroPadding(int numCombinations, ArrayList<Integer> binaryNum) {
        int paddingAmount = numCombinations - binaryNum.size();
        if (paddingAmount > 0) {
            for (int i = 0; i < paddingAmount; i++) {
                binaryNum.addFirst(0);
            }
        }
        return binaryNum;
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