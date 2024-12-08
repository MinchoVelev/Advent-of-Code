package y2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Day24 {
    public static void main(String[] args) throws Exception {
        Scanner scanner = IOUtils.getScanner("day24.txt");
        ArrayList<long[]> hails = new ArrayList<>();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] split = line.replace(" ", "").replace("@", ",").split(",");
            long[] numbers = Arrays.stream(split).mapToLong(Long::valueOf).toArray();
            hails.add(numbers);
        }

        long collisions = 0;

        for(int i = 0; i < hails.size(); i++){
            for(int j = i+1; j < hails.size(); j++){
                long[] hailA = hails.get(i);
                long[] hailB = hails.get(j);

                 double slopeA = findSlope(hailA);
                 double slopeB = findSlope(hailB);
                 if(parallel(slopeA, slopeB)){
                     continue;
                 }
                 double x = findX(hailA, hailB, slopeA, slopeB);
                 double y = finxY(hailA, slopeA, x);

                 if(inRange(x, y) && inFuture(x,y,hailA) && inFuture(x,y, hailB)){
                    collisions++;
                 }

            }
        }

        System.out.println("longersections in range: " + collisions);
    }

    private static boolean inFuture(double x, double y, long[] hailA) {
        boolean checkX;
        if(hailA[3] > 0){
            checkX = x >= hailA[0];
        }else{
            checkX = x <= hailA[0];
        }

        boolean checkY;
        if(hailA[4] > 0){
            checkY = y >= hailA[1];
        }else{
            checkY = y <= hailA[1];
        }

        return checkX && checkY;
    }

    private static boolean inRange(double x, double y) {
        boolean result = x >= 200000000000000l && x <= 400000000000000l && y >= 200000000000000l && y <= 400000000000000l;
        //System.out.prlongln("Checking if " + x + " and " + y + " are in range? " + result);
        return result;
    }

    private static double finxY(long[] hailA, double slopeA, double x) {
        return slopeA*x - slopeA*hailA[0] + hailA[1];
    }

    private static double findX(long[] hailA, long[] hailB, double slopeA, double slopeB) {
        return - ( -slopeA*hailA[0] + hailA[1] + slopeB*hailB[0] - hailB[1] ) / (slopeA-slopeB);
    }

    private static boolean parallel(double hailA, double hailB) {
        return hailA == hailB;
    }

    private static double findSlope(long[] hailA) {
        long x1= hailA[0];
        long x2 = x1 + hailA[3];
        long y1 = hailA[1];
        long y2 = y1 + hailA[4];

        return  (y2-y1)/((double)x2-x1);
    }
}
