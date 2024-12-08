package y2023;

import java.util.*;

public class Day11 {
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
        Scanner scanner = IOUtils.getScanner("day11.txt");
        int size = scanner.nextLine().length();
        scanner.close();
        scanner = IOUtils.getScanner("day11.txt");

        int[] expandingColumns = new int[size];

        LinkedList<String> lines = new LinkedList<>();
        int index = 0;
        Set<Integer> expandingRows = new HashSet<>();

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
                }
            }

            index++;
        }

        int newRows = size + expandingRows.size();
        int newColumns = size;
        for(int c : expandingColumns){
            if(c == 0){
                newColumns++;
            }
        }

        int[][] maze = new int[newRows][newColumns];
        int rowModifier = 0;
        int columnModifier = 0;

        System.out.printf("old size %dx%d, new size %d,%d\n", size, size, newRows, newColumns);

        ArrayList<Coordinate> galaxies = new ArrayList<>();
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if(expandingColumns[j] == 0){
                    maze[i + rowModifier][j + columnModifier++] = 0;
                    maze[i + rowModifier][j + columnModifier] = 0;
                }
                else{
                    int value;
                    if(lines.get(i).charAt(j) == '.'){
                        value = 0;
                    } else{
                      value = 1;
                      galaxies.add(new Coordinate(i + rowModifier, j + columnModifier));
                    }
                    maze[i + rowModifier][j + columnModifier] = value;
                }
            }
            if(expandingRows.contains(i)){
                maze[i + ++rowModifier] = new int[newColumns];

            }
            columnModifier = 0;
        }
        long sum = 0l;

        for(int i = 0; i < galaxies.size(); i++){
            for (int j = i+1; j < galaxies.size(); j++){
                if(i == j){
                    continue;
                }
                Coordinate galaxyA = galaxies.get(i);
                Coordinate galaxyB = galaxies.get(j);
                sum += Math.abs(galaxyA.x - galaxyB.x) + Math.abs(galaxyA.y - galaxyB.y);
            }

        }
        System.out.println("The sum is: " + sum);
    }
}
