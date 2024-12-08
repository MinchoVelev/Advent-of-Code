package y2023;

import java.util.Arrays;
import java.util.Scanner;

public class Day9P2 {
    public static void main(String[] args) throws Exception {
        Scanner scanner = IOUtils.getScanner("day9.txt");

        long sum = 0;
        while(scanner.hasNext()){
            String line = scanner.nextLine();
            long value = computePreviousValue(line);

            System.out.println(line + ": " + value);

            sum+= value;
        }

        System.out.println("Part2: " + sum);
    }

    private static long computePreviousValue(String line) {
        int[] numbers = Arrays.stream(line.trim().split("\\s+")).mapToInt(Integer::parseInt).toArray();

        int[] currentLine = numbers;

        long result = currentLine[0];
        int c = 0;
        while(containsNonZero(currentLine)){
            int[] tmp = new int[currentLine.length - 1];
            for(int i = 0; i < tmp.length; i++){
                tmp[i] = currentLine[i+1] - currentLine[i];
            }
            currentLine = tmp;

            if(c++ %2 ==0){
                result = result - currentLine[0];
            }
            else{
                result = result + currentLine[0];
            }

        }

        return result;
    }

    private static boolean containsNonZero(int[] currentLine) {
        for(int n : currentLine){
            if(n != 0){
                return true;
            }
        }
        return false;
    }
}
