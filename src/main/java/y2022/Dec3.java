package y2022;

import java.util.Scanner;

import y2022.utils.DuplicateSearchingBinaryTree;
import y2022.utils.IOUtils;

public class Dec3 {
    public static void main(String[] args) {
        System.out.println("Part 1:");
        part1();

        System.out.println("-------------------");

        System.out.println("Part 2:");
        part2();
    }

    private static void part1() {
        try (Scanner scanner = IOUtils.getScanner("Dec3.input", Dec3.class)) {
            int sum = 0;

            while (scanner.hasNext()) {
                char[] line = scanner.nextLine().toCharArray();
                // char[] line = "BdbzzddChsWrRFbzBrszbhWMLNJHLLLLHZtSLglFNZHLJH".toCharArray();

                DuplicateSearchingBinaryTree t = new DuplicateSearchingBinaryTree();

                for (int i = 0; i < line.length; i++) {
                    char c = line[i];
                    int priority;
                    int modifier;

                    modifier = c >= 'a' ? -'a' + 1 : -'A' + 27;
                    priority = c + modifier;

                    sum += t.add(priority, i >= line.length / 2);
                }
            }
            System.out.println(sum);
        }
    }

    private static void part2() {
        try (Scanner scanner = IOUtils.getScanner("Dec3.input", Dec3.class)) {
            int sum = 0;
            int lineCount = 0;
            int[][] items = new int[3][];
            while (scanner.hasNext()) {
                char[] line = scanner.nextLine().toCharArray();

                items[lineCount % 3] = new int[53];

                for (int i = 0; i < line.length; i++) {
                    char c = line[i];

                    int modifier = c >= 'a' ? -'a' + 1 : -'A' + 27;
                    int priority = c + modifier;

                    items[lineCount % 3][priority] += 1;
                }

                if (lineCount % 3 == 2) {
                    sum += findBadge(items);
                }

                lineCount++;
            }
            System.out.println(sum);
        }
    }

    private static int findBadge(int[][] items) {
        for (int i = 1; i < 53; i++) {
            //System.out.println(items[0][i] + "," + items[1][i] + "," + items[2][i]);
            if (items[0][i] > 0 && items[1][i] > 0 && items[2][i] > 0) {
                //System.out.println("Matching item: " + i);
                return i;
            }
        }
        throw new RuntimeException("could not find badge");
    }
}
