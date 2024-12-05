package y2023;

import java.util.*;

public class Day16P2 {
    static final boolean DEBUG = false;
    public static void main(String[] args) throws Exception {
        Scanner scanner = IOUtils.getScanner("day16.txt");

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

        int sIndex = 0;
        Movement[] starters = new Movement[grid.length * 2 + grid[0].length * 2];

        for(int i = 0; i < grid.length; i++){
            starters[sIndex++] = new Movement(i, 0, 0, 1);
            starters[sIndex++] = new Movement(i, grid[0].length - 1, 0, -1);
        }
        for(int i = 0; i < grid[0].length; i++){
            starters[sIndex++] = new Movement(0, i, 1, 0);
            starters[sIndex++] = new Movement(grid.length - 1, i, -1, 0);
        }

        int max = Arrays.stream(starters).parallel().map( s -> {

            ArrayList<Movement> movements = new ArrayList<>();
            movements.add(s);
            Set<Movement> energized = new HashSet<>();
            int eSize;
            int eSizeCount = 0;
            while (movements.size() > 0) {
                eSize = energized.size();
                if (eSizeCount > 10) {
                    //System.out.println("Loop detected!");
                    break;
                }



                ArrayList<Movement> newMovements = new ArrayList<>();
                for (Movement m : movements) {

                    if (m.x == -1 || m.y == -1 || m.x >= grid.length || m.y >= grid[0].length) {
                        continue;
                    }
                    energized.add(new Movement(m.x, m.y, 0, 0));
                    switch (grid[m.x][m.y]) {
                        case '.':
                            newMovements.add(advance(m));
                            break;
                        case '-':
                            if (m.dX != 0) {
                                newMovements.add(new Movement(m.x, m.y + 1, 0, 1));
                                newMovements.add(new Movement(m.x, m.y - 1, 0, -1));
                            } else {
                                newMovements.add(advance(m));
                            }
                            break;
                        case '|':
                            if (m.dY != 0) {
                                newMovements.add(new Movement(m.x + 1, m.y, 1, 0));
                                newMovements.add(new Movement(m.x - 1, m.y, -1, 0));
                            } else {
                                newMovements.add(advance(m));
                            }
                            break;
                        case '\\':
                            if (m.dX == 1) {
                                newMovements.add(new Movement(m.x, m.y + 1, 0, 1));
                            } else if (m.dX == -1) {
                                newMovements.add(new Movement(m.x, m.y - 1, 0, -1));
                            } else if (m.dY == 1) {
                                newMovements.add(new Movement(m.x + 1, m.y, 1, 0));
                            } else if (m.dY == -1) {
                                newMovements.add(new Movement(m.x - 1, m.y, -1, 0));
                            }
                            break;
                        case '/':
                            if (m.dX == 1) {
                                newMovements.add(new Movement(m.x, m.y - 1, 0, -1));
                            } else if (m.dX == -1) {
                                newMovements.add(new Movement(m.x, m.y + 1, 0, 1));
                            } else if (m.dY == 1) {
                                newMovements.add(new Movement(m.x - 1, m.y, -1, 0));
                            } else if (m.dY == -1) {
                                newMovements.add(new Movement(m.x + 1, m.y, 1, 0));
                            }
                            break;
                        default:
                            throw new IllegalStateException("unknown sign " + grid[m.x][m.y]);
                    }
                }
                movements = newMovements;
                if (eSize == energized.size()) {
                    eSizeCount++;
                } else {
                    eSizeCount = 0;
                }
            }
            return energized.size();
        }).max(Integer::compareTo).get();


        System.out.println("Max energized: " + max);
    }

    static Movement advance(Movement m){
        return new Movement(m.x + m.dX, m.y + m.dY, m.dX, m.dY);
    }
    static class Movement {
        int x;
        int y;
        int dX;
        int dY;

        public Movement(int x, int y, int dX, int dY) {
            this.x = x;
            this.y = y;
            this.dX = dX;
            this.dY = dY;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Movement movement = (Movement) o;
            return x == movement.x && y == movement.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
