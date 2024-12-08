package y2023;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day9 {
    public static void main(String[] args) throws Exception {
        Scanner scanner = IOUtils.getScanner("day9.txt");

        long sum = 0;
        while(scanner.hasNext()){
            String line = scanner.nextLine();
            long value = computeNextValue(line);

            System.out.println(line + ": " + value);

            sum+= value;
        }

        System.out.println("Part1: " + sum);
    }

    private static long computeNextValue(String line) {
        int[] numbers = Arrays.stream(line.trim().split("\\s+")).mapToInt(Integer::parseInt).toArray();

        int[] currentLine = numbers;

        long result = 0;
        while(containsNonZero(currentLine)){
            result += currentLine[currentLine.length-1];

            int[] tmp = new int[currentLine.length - 1];
            for(int i = 0; i < tmp.length; i++){
                tmp[i] = currentLine[i+1] - currentLine[i];
            }
            currentLine = tmp;
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
