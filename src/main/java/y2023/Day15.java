package y2023;

import java.util.Scanner;

public class Day15 {
    public static void main(String[] args) throws Exception {
        Scanner scanner = IOUtils.getScanner("day15.txt");
        scanner.useDelimiter(",");
        long result = 0;
        while (scanner.hasNext()) {
            String nextChar = scanner.next();
            System.out.println(nextChar);
            long tmp = 0;

            for(var c: nextChar.toCharArray()){
                tmp += c;
                tmp *= 17;
                tmp = tmp % 256;
            }
            result += tmp;
        }

        System.out.println("Total: " + result);

    }
}
