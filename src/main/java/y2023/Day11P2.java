package y2023;

import java.util.*;

public class Day11P2 {
static class Coordinate{
        int x;
        int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "[" + x + ", " + y + "]";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coordinate that = (Coordinate) o;
            return x == that.x && y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
    public static void main(String[] args) throws Exception {
        Scanner scanner = IOUtils.getScanner("day11_real.txt");
        int size = scanner.nextLine().length();
        scanner.close();
        scanner = IOUtils.getScanner("day11_real.txt");

        int[] expandingColumns = new int[size];

        LinkedList<String> lines = new LinkedList<>();
        int index = 0;
        Set<Integer> expandingRows = new HashSet<>();

        ArrayList<Coordinate> galaxies = new ArrayList<>();
        long sum = 0l;

        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            lines.add(line);
            if(!line.contains("#")){
                expandingRows.add(index);
            }

            char[] charsInLine = line.toCharArray();

            for(int i = 0; i < charsInLine.length; i++){
                if(charsInLine[i] == '#'){
                    expandingColumns[i] = 1;
                    galaxies.add(new Coordinate(index, i));
                }
            }

            index++;
        }
        int modifier = 1000000;
        for(int i = 0; i < galaxies.size(); i++){
            for (int j = i+1; j < galaxies.size(); j++){
                if(i == j){
                    continue;
                }
                Coordinate galaxyA = galaxies.get(i);
                Coordinate galaxyB = galaxies.get(j);
                int expandedRowsBetween = getExpandedRows(galaxyA.x, galaxyB.x, expandingRows);
                int expandedColumnsBetween = getExpandedColumns(galaxyA.y, galaxyB.y, expandingColumns);
                long distance = Math.abs(galaxyA.x - galaxyB.x) + Math.abs(galaxyA.y - galaxyB.y) + ((expandedRowsBetween + expandedColumnsBetween) * (modifier-1));
                System.out.printf("Between galaxies %s and %s there are %d expanding rows and %d expanding columns and the distance is %d\n", galaxyA, galaxyB, expandedRowsBetween, expandedColumnsBetween, distance);
                sum += distance;
            }

        }
        System.out.println("The sum is: " + sum);
    }

    private static int getExpandedColumns(int y, int y1, int[] expandingColumns) {
        int expanded = 0;
        for(int i = Math.min(y, y1)+1; i < Math.max(y, y1); i++){
            if(expandingColumns[i] == 0){
                expanded++;
            }
        }
        return expanded;
    }

    private static int getExpandedRows(int x, int x1, Set<Integer> expandingRows) {
        int expanded = 0;
        for(int i = Math.min(x, x1)+1; i < Math.max(x, x1); i++){
            if(expandingRows.contains(i)){
                expanded++;
            }
        }
        return expanded;
    }
}
