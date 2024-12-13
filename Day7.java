import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day7 {
    public static void main(String[] args) {

        ArrayList<String> fileData = getFileData("src/Day7Input.txt");
        System.out.println(fileData);



        // you now have an ArrayList of Strings for each number in the file
        // do Advent 2024 day 7!

        //a plan to do this

        //find out the number of combinations needed --> 2^(n-1) where n is 1 less than the number of numbers
        /*
        Example with 4 possible combinations
        0 -->
        1
        2
        3 --> 3/2 = 1 > 0
         */


        ArrayList<Integer> sampleArray = new ArrayList<>();
        sampleArray.add(5);
        sampleArray.add(12);
        sampleArray.add(2);
        System.out.println(operateOnNumbers(4, sampleArray));

        //System.out.println(wholeBinary(512));
        System.out.println(zeroPadding(25, decimalToBinary(17)));
        System.out.println(decimalToBinary(17));

    }


    //finds the answer of the numbers using a specific combination
    public static int operateOnNumbers(int combinationNumber, ArrayList<Integer> arrayOfNumbers) {
        ArrayList<Integer> binaryNumber = decimalToBinary(combinationNumber);


        return 0;
    }

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