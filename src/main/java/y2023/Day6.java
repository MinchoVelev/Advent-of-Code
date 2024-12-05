package y2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class Day6 {
    public static void main(String[] args) throws Exception {
        part1();
        part2();
    }

    private static void part2() {
        Scanner scanner = IOUtils.getScanner("day6.txt");

        String timesPlain = scanner.nextLine();
        String distancesPlain = scanner.nextLine();

        scanner.close();

        long time = Long.valueOf(timesPlain.split(":")[1].replaceAll("\\s+", ""));
        long distance = Long.valueOf(distancesPlain.split(":")[1].replaceAll("\\s+", ""));


        long result = 0;
        for (int j = 1; j < time; j++) {
            if( ((time - j) * j) > distance){
                result++;
            }
        }

        System.out.println("Part2: " + result);
    }

    private static void part1() {
        Scanner scanner = IOUtils.getScanner("day6.txt");

        String timesPlain = scanner.nextLine();
        String distancesPlain = scanner.nextLine();

        scanner.close();

        int[] times = Arrays.stream(timesPlain.split(":")[1].trim().split("\\s+")).mapToInt(Integer::valueOf).toArray();
        int[] distances = Arrays.stream(distancesPlain.split(":")[1].trim().split("\\s+")).mapToInt(Integer::valueOf).toArray();

        int result = 1;
        for (int i = 0; i < times.length; i++) {
            LinkedList<Integer> raceResult = new LinkedList<>();
            for (int j = 1; j < times[i]; j++) {
                raceResult.add((times[i] - j) * j);
            }
            int distanceToBeat = distances[i];
            result *= raceResult.stream().filter(s -> s > distanceToBeat).count();
        }

        System.out.println("Part1: " + result);
    }
}
