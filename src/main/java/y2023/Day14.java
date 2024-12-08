package y2023;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Day14 {
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

        for(int i = 1; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++){
                if(grid[i][j] == '#' ||grid[i][j] == '.' ){
                    continue;
                }

                for(int x = i - 1; x >= 0; x--){
                    if(grid[x][j] == '.'){
                        //System.out.printf("Moving %d,%d on to %d, %d\n", x+1, j, x, j);
                        grid[x][j] = 'O';
                        grid[x+1][j] = '.';
                    } else{
                        break;
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
