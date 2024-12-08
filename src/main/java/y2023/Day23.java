package y2023;

import java.util.*;
import java.util.function.Function;

public class Day23 {

    static final int[][] DIRECTIONS = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

    static final Map<Character, int[][]> ACTIONS = new HashMap<>();

    static final Function<Vertex, List<Vertex>> LC = k -> new LinkedList<>();

    static {
        ACTIONS.put('.', DIRECTIONS);
        ACTIONS.put('^', new int[][]{new int[]{-1, 0}});
        ACTIONS.put('>', new int[][]{new int[]{0, 1}});
        ACTIONS.put('<', new int[][]{new int[]{0, -1}});
        ACTIONS.put('v', new int[][]{new int[]{1, 0}});
    }

    static class Vertex {
        int[] point;

        public Vertex(int... point) {
            this.point = point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Vertex vertex = (Vertex) o;
            return Arrays.equals(point, vertex.point);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(point);
        }

        @Override
        public String toString() {
            return "(x: " + point[0] + ", y: " + point[1] + (point.length == 2 ? ")" : (", distance: " + point[2] + ")"));
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = IOUtils.getScanner("day23.txt");

        int lines = 0;
        while (scanner.hasNext()) {
            scanner.nextLine();
            lines++;
        }
        scanner.close();

        scanner = IOUtils.getScanner("day23.txt");

        char[][] maze = new char[lines][];
        int index = 0;
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            maze[index++] = line.toCharArray();
        }

        HashSet<Vertex> points = new HashSet<>();

        Vertex start = null;
        Vertex end = null;

        for (int x = 0; x < maze[0].length; x++) {
            if (maze[0][x] == '.') {
                start = new Vertex(new int[]{0, x});
            }
        }
        for (int x = 0; x < maze[lines - 1].length; x++) {
            if (maze[lines - 1][x] == '.') {
                end = new Vertex(new int[]{lines - 1, x});
            }
        }

        points.add(start);
        points.add(end);

        for (int x = 0; x < maze.length; x++) {
            for (int y = 0; y < maze[x].length; y++) {
                int neighbours = 0;
                if (maze[x][y] != '#') {
                    for (var d : DIRECTIONS) {
                        int nx = x + d[0];
                        int ny = y + d[1];
                        if (isValid(nx, ny, maze) && maze[nx][ny] != '#') {
                            neighbours++;
                        }
                    }
                }
                if (neighbours >= 3) {
                    points.add(new Vertex(x, y));
                }
            }

        }
        System.out.println(points);

        HashMap<Vertex, List<Vertex>> graph = new HashMap<>();

        for (var p : points) {
            ArrayDeque<Vertex> queue = new ArrayDeque<>();
            HashSet<Vertex> seen = new HashSet<>();

            Vertex startingPoint = new Vertex(p.point[0], p.point[1]);
            queue.add(new Vertex(p.point[0], p.point[1], 0));
            seen.add(startingPoint);
            while (!queue.isEmpty()) {
                Vertex popped = queue.pop();
                int[] point = popped.point;
                int cx = popped.point[0];
                int cy = popped.point[1];

                char symbol = maze[point[0]][point[1]];
                int[][] possibleActions = ACTIONS.get(symbol);

                for (var v : possibleActions) {
                    int nx = point[0] + v[0];
                    int ny = point[1] + v[1];

                    if (!isValid(nx, ny, maze) || maze[nx][ny] == '#' || seen.contains(new Vertex(nx, ny))) {
                        continue;
                    }

                    Vertex newVertex = new Vertex(nx, ny);
                    Vertex nextWithDistance = new Vertex(nx, ny, popped.point[2] + 1);
                    if (points.contains(newVertex)) {
                        List<Vertex> vertices = graph.computeIfAbsent(startingPoint, LC);
                        vertices.add(nextWithDistance);
                    } else {
                        seen.add(newVertex);
                        queue.add(nextWithDistance);
                    }
                }
            }
        }
        System.out.println(graph);

        long distance = findDistance(start, end, graph);

        System.out.println("Max distance: " + distance);

    }

    static long findDistance(Vertex point, Vertex target, Map<Vertex, List<Vertex>> graph){
        if(point.equals(target)){
            return 0;
        }

        long distance = Long.MIN_VALUE;

        for(var v: graph.computeIfAbsent(point, LC)){
            distance = Math.max(distance, v.point[2] + findDistance(new Vertex(v.point[0], v.point[1]), target, graph));
        }

        return distance;
    }

    private static boolean isValid(int nx, int ny, char[][] maze) {
        return nx >= 0 && nx < maze.length && ny >= 0 && ny < maze[nx].length;
    }
}
