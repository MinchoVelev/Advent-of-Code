package y2023;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Scanner;

public class Day1 {
    public static void main(String[] args) throws Exception {
        Scanner scanner = IOUtils.getScanner("day1.txt");
        int sum = 0;
        while(scanner.hasNext()){
            String line = scanner.nextLine();
            line = line.toLowerCase(Locale.ENGLISH)
                    .replaceAll("oneight", "18")
                    .replaceAll("twone", "21")
                    .replaceAll("threeight", "38")
                    .replaceAll("fiveight", "58")
                    .replaceAll("sevenine", "79")
                    .replaceAll("eightwo", "82")
                    .replaceAll("eighthree", "83")
                    .replaceAll("nineight", "98")
                    .replaceAll("one", "1")
                    .replaceAll("two", "2")
                    .replaceAll("three", "3")
                    .replaceAll("four", "4")
                    .replaceAll("five", "5")
                    .replaceAll("six", "6")
                    .replaceAll("seven", "7")
                    .replaceAll("eight", "8")
                    .replaceAll("nine", "9");
                    //.replaceAll("zero", "0");
            boolean foundFirst = false;
            int lineNumber = 0;
            int secondChar = 0;
            for (char c : line.toCharArray()){
                int charNumber = c - (int) '0';
                if(charNumber >= 0 && charNumber <= 9){
                    if(!foundFirst){
                        lineNumber = charNumber * 10;
                        foundFirst = true;
                    }
                    secondChar = charNumber;
                }
            }
            lineNumber += secondChar;
            sum += lineNumber;
        }
        System.out.println(sum);
    }
}
