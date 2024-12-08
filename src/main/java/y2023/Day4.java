package y2023;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Day4 {
    public static void main(String[] args) throws Exception {
        Scanner scanner = IOUtils.getScanner("day4.txt");
        int sum = 0;
        long[] cardCopies = new long[223];
        Arrays.fill(cardCopies, 1l);
        int index = -1;
        while(scanner.hasNext()) {
            index++;
            String line = scanner.nextLine();

            String[] cards = line.split(":")[1].trim().split("\\|");

            HashSet<Integer> leftNumbers = Arrays.stream(cards[0].trim().split("\\s+")).map(Integer::valueOf).collect(Collectors.toCollection(HashSet::new));
            HashSet<Integer> rightNumbers = Arrays.stream(cards[1].trim().split("\\s+")).map(Integer::valueOf).collect(Collectors.toCollection(HashSet::new));

            long matches = leftNumbers.stream().filter(rightNumbers::contains).mapToInt(Integer::intValue).count();

            if(matches == 0){
                continue;
            }

            int points = (int) Math.pow(2, matches - 1);
            sum += points;

            for(int i = 1; i <= matches; i++){
                cardCopies[index+i] =  cardCopies[index+i] + cardCopies[index];
            }


        }
        System.out.println("Part1: " + sum);
        System.out.println("Part2: " + Arrays.stream(cardCopies).sum());
    }
}
