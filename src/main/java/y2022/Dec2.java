package y2022;

import java.io.IOException;
import java.util.Scanner;

import y2022.utils.IOUtils;

public class Dec2 {
    private Dec2() {
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Part 1:");
        part1();
        System.out.println("-----------------------------");
        System.out.println("Part 2:");
        part2();
    }

    private static void part1() {
        try (Scanner scanner = IOUtils.getScanner("Dec2.input", Dec2.class)) {
            int oponent;
            int me;
            int score = 0;
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                oponent = line.charAt(0) - 'A' + 1;
                me = line.charAt(2) - 'X' + 1;

                int outcome = me - oponent;
                score += me;

                if (outcome == 0) {
                    score += 3;
                } else if (outcome == -2 || outcome == 1) {
                    score += 6;
                }
            }
            System.out.println(score);

            // 2 3 1
            // 1 2 3
            // 1 1 -2
        }
    }

    private static void part2() {
        try (Scanner scanner = IOUtils.getScanner("Dec2.input", Dec2.class)) {
            int oponent;
            int outcome;
            int me = -1;
            int score = 0;
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                oponent = line.charAt(0) - 'A' + 1;
                outcome = line.charAt(2) - 'X' + 1;

                score += (outcome - 1) * 3;

                if (outcome == 1) {
                    me = (oponent - 1) % 3;
                } else if (outcome == 2) {
                    me = oponent;
                } else if (outcome == 3 ) {
                    me = (oponent + 1) % 3;
                }

                if (me == 0 ) {
                    me = 3;
                }
                score += me;
                //System.out.println("me " + me + " round " + ((outcome - 1) * 3));
            }
            
          
            System.out.println(score);

            //to lose (1)
            // 1 2 3
            // 2 3 1

            // 1 2 3
            // 3 1 2
        }
    }

}
