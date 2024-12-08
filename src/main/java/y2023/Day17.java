package y2023;

import java.util.*;

public class Day17 {

    static class Pair {
        final int[] direction;
        int stepsInDirection;
        final int x;
        final int y;
        final int cost;

        public Pair(Pair p){
            this(p.x, p.y, p.cost, p.direction, p.stepsInDirection);
        }
        public Pair(int x, int y, int cost, int[] direction, int stepsInDirection) {
            this.x = x;
            this.y = y;
            this.cost = cost;
            this.direction = direction;
            this.stepsInDirection = stepsInDirection;
        }

          @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair pair = (Pair) o;
            return x == pair.x && y == pair.y && stepsInDirection == pair.stepsInDirection && Arrays.equals(direction, pair.direction);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(x, y, stepsInDirection);
            result = 31 * result + Arrays.hashCode(direction);
            return result;
        }

        @Override
        public String toString() {
            return "Pair{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    static class PairComparator implements Comparator<Pair> {
        @Override
        public int compare(Pair o1, Pair o2) {
            return o1.cost - o2.cost;
        }
    }
    /*
    ->->->->
    \->->->

     */

    static int[][] moves = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

    public static void main(String[] args) throws Exception {
        Scanner scanner = IOUtils.getScanner("day17.txt");

        List<String> lines = new LinkedList<>();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            lines.add(line);
        }

        char[][] grid = new char[lines.size()][];

        int index = 0;
        for (var l : lines) {
            grid[index++] = l.toCharArray();
        }
        PriorityQueue<Pair> pq = new PriorityQueue<>(grid.length * grid[0].length, new PairComparator());
        pq.add(new Pair(0, 0, 0, new int[]{0, 0}, 0));
        HashSet<Pair> visited = new HashSet<>();
        while (!pq.isEmpty()) {
            Pair current = pq.poll();

            if (visited.contains(current)) {

                continue;
            }
            visited.add(current);
            for( var v : moves){
                if(isBackwards(v, current.direction)){
                    continue;
                }

                int nextX = current.x + v[0];
                int nextY = current.y + v[1];

                if(!isSafe(grid, nextX, nextY)){
                    continue;
                }

                int nextCost = current.cost + grid[nextX][nextY] - '0';


                boolean isFw = isForward(v, current.direction);
                Pair next = null;
                if(isFw){
                    if(current.stepsInDirection < 3) {
                        next = new Pair(nextX, nextY, nextCost, v, current.stepsInDirection + 1);
                    }
                }
                else{
                    if(!isBackwards(v, current.direction)) {
                        next = new Pair(nextX, nextY, nextCost, v, 1);
                    }
                }


                if(next != null){
                    if(next.x == grid.length - 1 && next.y == grid[0].length - 1){
                        System.out.println(next.cost);
                        System.exit(0);
                        break;
                    }
                    pq.add(next);
                }
            }
        }
    }

    private static boolean isBackwards(int[] v, int[] direction) {
        return v[0] == -direction[0] && v[1] == -direction[1];
    }

    private static boolean isForward(int[] v, int[] direction) {
        return v[0] == direction[0] && v[1] == direction[1];
    }

    private static boolean isSafe(char[][] grid, int x, int y) {
        return x >= 0 && x < grid.length && y >= 0 && y < grid[x].length;
    }
}
