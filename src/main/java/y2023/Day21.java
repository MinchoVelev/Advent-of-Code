package y2023;

import java.util.*;

public class Day21 {

    static class Holder{
        int[] point;

        public Holder(int[] point) {
            this.point = point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Holder holder = (Holder) o;
            return Arrays.equals(point, holder.point);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(point);
        }
    }
    public static void main(String[] args) throws Exception {
        Scanner scanner = IOUtils.getScanner("day21.txt");
            List<String> lines = new LinkedList<>();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            lines.add(line);
        }
        int[] start = null;
        char[][] grid = new char[lines.size()][];
        int index = 0;
        for(var l: lines){
            grid[index++] = l.toCharArray();
            int s = l.indexOf('S');
            if(s >= 0){
                start = new int[]{index - 1, s};
            }
        }
        //3650 too low
        List<Holder> positions = new ArrayList<>();
        ArrayDeque<int[]> bfsQueue = new ArrayDeque<>();
        Set<Holder> visited = new HashSet<>();
        int moves = 64;
        bfsQueue.add(new int[]{start[0], start[1], moves});
        int[][] directions = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        while(!bfsQueue.isEmpty()){
            int[] current = bfsQueue.poll();
            Holder currentHolder = new Holder(Arrays.copyOf(current, 2));

            if(visited.contains(currentHolder)){
                continue;
            }
            visited.add(currentHolder);
            if(current[2] == 0){
                positions.add(currentHolder);
                continue;
            }

            if(current[2] % 2 == 0){
                positions.add(currentHolder);
            }



            for(var vect: directions){
                int[] next = {current[0] + vect[0], current[1] + vect[1], current[2] - 1};
                if(!isValid(next, grid)){
                    continue;
                }
                bfsQueue.add(next);
            }


        }

        System.out.println(positions.size());

        for (int x = 0; x < grid.length; x++){
            for (int y = 0; y < grid[x].length; y++){
                if(positions.contains(new Holder(new int[]{x, y}))){
                    System.out.print("O");
                }else{
                    System.out.print(grid[x][y]);
                }
            }
            System.out.println();
        }


    }

    private static boolean isValid(int[] next, char[][] grid) {
        return next[0] >= 0 && next[0] < grid.length && next[1] >= 0 && next[1] < grid[0].length && grid[next[0]][next[1]] != '#';
    }
}
