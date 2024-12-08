package y2023;

import java.util.*;

public class Day14P2 {
    public static void main(String[] args) throws Exception {
        Scanner scanner = IOUtils.getScanner("day14.txt");

        List<String> lines = new LinkedList<>();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            lines.add(line);
        }

        char[][] grid = new char[lines.size()][];
        int index = 0;
        for(var l : lines){
            grid[index++] = l.toCharArray();
        }
        int firstLoop = -1;
        int secondLoop = -1;
        int targetRemainder = -1;
        HashSet<Integer> seenHashes = new HashSet<>();
        for (int c = 0; c < 1000000000; c++) {
            if(targetRemainder != -1){
                if((c - firstLoop) % secondLoop == targetRemainder){
                    System.out.println("Quitting at " + c);
                    break;
                }
            }
            int sizeBefore = seenHashes.size();
            seenHashes.add(Arrays.deepHashCode(grid));
            if(sizeBefore == seenHashes.size()){
                System.out.println("Loop detected at index " + c);
                System.out.println("Hashes size: " + sizeBefore);
                seenHashes.clear();
                seenHashes.add(Arrays.deepHashCode(grid));
                if(firstLoop == -1){
                    firstLoop = sizeBefore;
                }else if(secondLoop == -1){
                    secondLoop = sizeBefore;
                    targetRemainder = (1000000000 - firstLoop) % secondLoop;
                }


            }
            // North
            for (int i = 1; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    if (grid[i][j] == '#' || grid[i][j] == '.') {
                        continue;
                    }

                    for (int x = i - 1; x >= 0; x--) {
                        if (grid[x][j] == '.') {
                            //System.out.printf("Moving %d,%d on to %d, %d\n", x+1, j, x, j);
                            grid[x][j] = 'O';
                            grid[x + 1][j] = '.';
                        } else {
                            break;
                        }

                    }
                }
            }

            //West
            for (int i = 0; i < grid.length; i++) {
                for (int j = 1; j < grid[i].length; j++) {
                    if (grid[i][j] == '#' || grid[i][j] == '.') {
                        continue;
                    }

                    for (int x = j - 1; x >= 0; x--) {
                        if (grid[i][x] == '.') {
                            //System.out.printf("Moving %d,%d on to %d, %d\n", x+1, j, x, j);
                            grid[i][x] = 'O';
                            grid[i][x + 1] = '.';
                        } else {
                            break;
                        }

                    }
                }
            }

            //South
            for (int i = grid.length - 2; i >= 0; i--) {
                for (int j = 0; j < grid[i].length; j++) {
                    if (grid[i][j] == '#' || grid[i][j] == '.') {
                        continue;
                    }

                    for (int x = i + 1; x < grid[i].length; x++) {
                        if (grid[x][j] == '.') {
                            //System.out.printf("Moving %d,%d on to %d, %d\n", x+1, j, x, j);
                            grid[x][j] = 'O';
                            grid[x - 1][j] = '.';
                        } else {
                            break;
                        }

                    }
                }
            }

            // East
            for (int i = 0; i < grid.length; i++) {
                for (int j = grid[i].length - 2; j >= 0; j--) {
                    if (grid[i][j] == '#' || grid[i][j] == '.') {
                        continue;
                    }

                    for (int x = j + 1; x < grid[i].length; x++) {
                        if (grid[i][x] == '.') {
                            //System.out.printf("Moving %d,%d on to %d, %d\n", x+1, j, x, j);
                            grid[i][x] = 'O';
                            grid[i][x - 1] = '.';
                        } else {
                            break;
                        }

                    }
                }
            }
        }

        long weights = 0l;
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++) {
                if ( grid[i][j] == 'O'){
                    weights += grid.length - i;
                }
                System.out.print(grid[i][j]);
            }
            System.out.println();
        }

        System.out.println("Weight is " + weights);

    }
}
