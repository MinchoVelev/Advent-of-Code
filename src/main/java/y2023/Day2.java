package y2023;

import java.util.*;

public class Day2 {
    //only 12 red cubes, 13 green cubes, and 14 blue cubes
    private static int RL = 12;
    private static int GL = 13;
    private static int BL = 14;

    private static int R = 0;
    private static int G = 1;
    private static int B = 2;

    private static int MAX = RL+GL+BL;

    public static void main(String[] args) throws Exception {
        Scanner scanner = IOUtils.getScanner("day2.txt");

        int sum1 = 0;
        int sum2 = 0;
        while(scanner.hasNext()){
            String line = scanner.nextLine();
            int prefixEnd = line.indexOf(":");
            int gameIndex = Integer.parseInt(line.substring(line.indexOf(" ")+ 1, prefixEnd));

            int[] colors = new int[3];

            String[] rounds = line.substring(prefixEnd + 1).split(";");

            for(var r : rounds){

                for( var c: r.split(",")){

                    String[] singleColor = c.trim().split(" ");
                    int value = Integer.parseInt(singleColor[0].trim());

                    int colorCode = toColorCode(singleColor[1].trim());

                    if(colors[colorCode] < value){
                        colors[colorCode] = value;
                    }
                }

            }

            //is the game possible?
            if(colors[R] <= RL && colors[G] <= GL && colors[B] <= BL){
                sum1 += gameIndex;
            }

            sum2 += colors[R]*colors[G]*colors[B];
        }
        System.out.println("Part1: " + sum1);
        System.out.println("Part2: " + sum2);
    }

    private static int toColorCode(String colorName) {
        int colorCode;
        switch(colorName){
            case "red":
                colorCode = R;
                break;
            case "green":
                colorCode = G;
                break;
            case "blue":
                colorCode= B;
                break;
            default: colorCode = -1;
        }
        return colorCode;
    }
}
