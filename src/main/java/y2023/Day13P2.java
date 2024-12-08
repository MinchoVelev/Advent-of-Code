package y2023;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Day13P2 {
    public static void main(String[] args) throws Exception {
        Scanner scanner = IOUtils.getScanner("day13.txt");

        LinkedList<String> pattern = new LinkedList<>();

        LinkedList<List<String>> patterns =  new LinkedList<>();

        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            if(line.isBlank()){
                patterns.add(pattern);
                pattern = new LinkedList<>();
                continue;
            }

            pattern.add(line);
        }
        patterns.add(pattern);

        long sum = 0l;
        for(var p : patterns){
            char[][] grid = new char[p.size()][];
            int i = 0;
            for(var l : p){
                grid[i++] = l.toCharArray();
            }
            
            Optional<Integer> vm = detectVerticalMirror(grid);
            if(vm.isPresent()){
                System.out.println("Detected a verticall mirror adding " + vm.get() + " to sum");
                sum += vm.get();
            }

            Optional<Integer> hm = detectVerticalMirror(transpose(grid));
            if(hm.isPresent()){
                System.out.println("Detected a horizontal mirror adding " + (hm.get() * 100) + " to sum");
                sum += hm.get() * 100;
            }
        }
        System.out.println("Sum is: " + sum);
    }

    private static char[][] transpose(char[][] grid) {
        char[][] result = new char[grid[0].length][grid.length];
        for(int i = 0; i < grid.length; i ++){
            for(int j = 0; j < grid[i].length;j++){
                result[j][i] = grid[i][j];
            }
        }

        return result;
    }
    private static Optional<Integer> detectVerticalMirror(char[][] grid) {
        outer: for(int i = 0; i < grid[0].length -1; i++){
            boolean smidgeFixed = false;
            for(int x = 0; i + x + 1 < grid[0].length && i - x >= 0; x++){
                for( int y = 0; y < grid.length; y++){
                    if(grid[y][i - x] != grid[y][i + x + 1]){
                        if(smidgeFixed){
                            continue outer;
                        }else{
                            smidgeFixed = true;
                        }
                    }
                }

            }
            if(smidgeFixed){
                return Optional.of(i + 1);
            }
        }
        return Optional.ofNullable(null);
    }
}
