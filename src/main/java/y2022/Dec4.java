package y2022;

import java.util.Scanner;

import y2022.utils.IOUtils;

public class Dec4 {
    public static void main(String[] args) {
        System.out.println("Part 1:");
        part1();

        System.out.println("-------------------");

        System.out.println("Part 2:");
        part2();
    }

    private static void part2() {
        try (Scanner scanner = IOUtils.getScanner("Dec4.input", Dec4.class)) {
            int count = 0;
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] ranges = line.split(",");
                String[] range1 = ranges[0].split("-");
                String[] range2 = ranges[1].split("-");

                int range1Start = Integer.parseInt(range1[0]);
                int range1End = Integer.parseInt(range1[1]);
                int range2Start = Integer.parseInt(range2[0]);
                int range2End = Integer.parseInt(range2[1]);
                // start1 < start2 < end1
                // start1 < end2 < end1
                
                if(range1Start <= range2Start && range2Start <= range1End){
                    count++;
                }else if(range1Start <= range2End && range2End <= range1End){
                    count++;
                }else if(range2Start <= range1End && range1End <= range2End){
                    count++;
                }else if(range2Start <= range1Start && range1Start <= range2End){
                    count++;
                }


            }
            System.out.println(count);
        }
    }

    private static void part1() {
        try (Scanner scanner = IOUtils.getScanner("Dec4.input", Dec4.class)) {
            int count = 0;
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] ranges = line.split(",");
                String[] range1 = ranges[0].split("-");
                String[] range2 = ranges[1].split("-");

                int range1Start = Integer.parseInt(range1[0]);
                int range1End = Integer.parseInt(range1[1]);
                int range2Start = Integer.parseInt(range2[0]);
                int range2End = Integer.parseInt(range2[1]);

                if(range1Start <= range2Start && range1End >= range2End){
                    count++;
                }else if(range2Start <= range1Start && range2End >= range1End){
                    count++;
                }

            }
            System.out.println(count);
        }
    }
}
