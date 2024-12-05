package y2023;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day3 {
    private static int SIZE = 140;
    private static Map<String, List<Integer>> gears = new HashMap<>();
    public static void main(String[] args) {
        Scanner scanner = IOUtils.getScanner("day3.txt");

        long sum = 0l;


        char[][] engine = new char[SIZE][];
        int x = 0;
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            engine[x++] = line.toCharArray();
        }
        scanner.close();


        Stack<Integer> digits = new Stack<>();
        boolean inRow = false;
        for (int y = 0; y < SIZE; y++) {
            for (x = 0; x < SIZE; x++) {
                char current = engine[y][x];
                if (isDigit(current)) {
                    digits.push(Character.getNumericValue(current));
                    inRow = true;
                    if (x != SIZE - 1) {
                        continue;
                    }
                }
                if (!inRow) {
                    continue;
                }

                inRow = false;

                int beginning = x - digits.size();
                int end = x-1;
                if(x == SIZE -1 && isDigit(current)){
                    beginning++;
                    end++;
                }

                int theNumber = toIntAndEmpty(digits);
                if (touchesSymbols(engine, y, beginning, end, theNumber)) {

                    System.out.printf("%d,%d ends the number %d and it touches a symbol\n", y, x, theNumber);
//                    for(int i = beginning; i <= end; i++){
//                        engine[y][i] = '.';
//                    }
                    sum += theNumber;

                } else {
                    System.out.printf("%d,%d ends the number %d and it does not touch a symbol\n", y, x, theNumber);
                }

            }
        }
        System.out.println("Part 1: " + sum);

        int part2Sum = gears.values().stream().filter(v -> v.size() == 2).map(v -> v.stream().reduce(1, Math::multiplyExact)).mapToInt(Integer::intValue).sum();
        System.out.println("Part 2: " + part2Sum);
//        System.out.println();
//        System.out.println();
//
//        for (int i = 0; i<SIZE; i++){
//            for (int j = 0; j<SIZE; j++){
//                System.out.print(engine[i][j]);
//            }
//            System.out.println();
//        }

    }

    private static int toIntAndEmpty(Stack<Integer> digits) {
        int result = 0;
        int multiplier = 1;
        while (!digits.empty()) {
            result += digits.pop() * multiplier;
            multiplier *= 10;
        }
        return result;
    }

    private static boolean touchesSymbols(char[][] engine, int row, int columnBeginning, int columnEnd, int theNumber) {
        //System.out.printf("Checking row %d for range [%d,%d]\n", row, columnBeginning, columnEnd);
        boolean result = false;
        //check above and below
        for (int i = columnBeginning; i <= columnEnd; i++) {
            if (row > 0) {
                if (isSymbol(engine,row - 1,i, theNumber)) {
                    result = true;
                }
            } if (row < SIZE - 1) {
                if (isSymbol(engine,row + 1,i, theNumber)) {
                    result = true;
                }
            }
        }

        if (columnBeginning > 0) {
            //check left
            if (isSymbol(engine,row,columnBeginning - 1, theNumber)) {
                result = true;
            }
            //check upper left diagonal
            if (row > 0) {
                if (isSymbol(engine,row - 1,columnBeginning - 1, theNumber)) {
                    result = true;
                }
            }

            //check lower left diagonal
            if (row < SIZE - 1) {
                if (isSymbol(engine,row + 1,columnBeginning - 1, theNumber)) {
                    result = true;
                }
            }
        }

        if (columnEnd < SIZE - 1) {
            // check right
            if (isSymbol(engine,row,columnEnd + 1, theNumber)) {
                result = true;
            }

            //check upper right diagonal
            if (row > 0) {
                if (isSymbol(engine,row - 1,columnEnd + 1, theNumber)) {
                    result = true;
                }
            }

            //check lower right diagonal
            if (row < SIZE - 1) {
                if (isSymbol(engine,row + 1,columnEnd + 1, theNumber)) {
                    result = true;
                }
            }
        }

        return result;
    }

    private static boolean isSymbol(char[][] engine, int y, int x, int number) {
        char c = engine[y][x];

        if(c == '*'){
            String key = y + "-" + x;
            if(!gears.containsKey(key)){
                gears.put(key, new LinkedList<>());
            }
            List<Integer> numbers = gears.get(key);
            numbers.add(number);
            gears.put(key, numbers);
        }

        return c != '.';
    }


    private static boolean isDigit(char current) {
        return current <= '9' && current >= '0';
    }
}
