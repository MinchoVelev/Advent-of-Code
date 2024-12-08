package y2023;

import java.sql.Array;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class Day10 {
    static int startX;
    static int startY;

    enum Exits{
        NORTH, EAST, SOUTH, WEST;
    }
    static class Point{
        Exits[] exits;

        boolean hasExit(Exits exit){
            return Arrays.stream(exits).filter(s -> s.equals(exit)).findAny().isPresent();
        }

        Point(Exits... e){
            this.exits = e;

        }

        Exits theOther(Exits enteredFrom){
            if(exits[0] == enteredFrom){
                return exits[1];
            }else{
                return exits[0];
            }
        }

        @Override
        public String toString() {
            return Arrays.toString(exits);
        }
    }
    public static void main(String[] args) throws Exception {
        Scanner scanner = IOUtils.getScanner("day10.txt");
        long sum = 0;
        LinkedList<String> lines = new LinkedList<>();
        while(scanner.hasNext()){
           lines.add(scanner.nextLine());
        }
        Point[][] maze = new Point[lines.size()][lines.get(0).length()];
        
        int j = 0;
        for(String line : lines){
            Point[] points = new Point[line.length()];
            for(int i = 0; i < points.length; i++){
                Exits[] exits = new Exits[2];
                switch(line.charAt(i)){
                    case '-':
                        points[i] = new Point(Exits.EAST, Exits.WEST);
                        break;
                    case 'J':
                        points[i] = new Point(Exits.NORTH, Exits.WEST);
                        break;
                    case '7':
                        points[i] = new Point(Exits.SOUTH, Exits.WEST);
                        break;
                    case 'F':
                        points[i] = new Point(Exits.SOUTH, Exits.EAST);
                        break;
                    case 'L':
                        points[i] = new Point(Exits.NORTH, Exits.EAST);
                        break;
                    case '|':
                        points[i] = new Point(Exits.NORTH, Exits.SOUTH);
                        break;
                    case 'S':
                        startX = j;
                        startY = i;
                        break;
                    case '.':
                        points[i] = new Point();
                        break;
                    default:
                        throw new IllegalStateException("Cannot parse " + line.charAt(i));
                }
            }
            maze[j++] = points;
        }

        System.out.println(startX + " " + startY);

        Exits[] startE = determineStartExits(maze, startX, startY);
        System.out.println("Start exits " + maze[startX][startY].toString());

        Exits enteredFrom = startE[0];
        int x = startX;
        int y = startY;

        int count = 0;
        do{
            count++;
            Exits next = maze[x][y].theOther(enteredFrom);
            switch(next){
                case SOUTH:
                    enteredFrom = Exits.NORTH;
                    x = x+1;
                    y = y;
                    break;
                case NORTH:
                    enteredFrom = Exits.SOUTH;
                    x = x-1;
                    y = y;
                    break;
                case EAST:
                    enteredFrom = Exits.WEST;
                    x = x;
                    y = y + 1;
                    break;
                case WEST:
                    enteredFrom = Exits.EAST;
                    x = x;
                    y = y - 1;
                    break;
                default:
                    throw new IllegalStateException("Could not determine next move " + x + "," + y + " entered from " + enteredFrom.name());
            }
            System.out.println("Moved " + next.name() + " to " + x + "," + y);
        }while(x != startX || y!=startY);

        System.out.println("Part1: " + count / 2.0);
    }

    private static Exits[] determineStartExits(Point[][] maze, int startX, int startY) {
        Point start = maze[startX][startY];
        Exits[] startExits;
        boolean up =hasUp(maze, startX, startY);
        boolean down =hasDown(maze, startX, startY);
        boolean left = hasLeft(maze, startX, startY);
        boolean right = hasRight(maze, startX, startY)
;        // -
        if(left && right){
            startExits = new Exits[]{Exits.WEST, Exits.EAST};
            maze[startX][startY] = new Point(startExits);
            return startExits;
        }
        // |
        if(up && down){
            startExits = new Exits[]{Exits.SOUTH, Exits.NORTH};
            maze[startX][startY] = new Point(startExits);
            return startExits;
        }
        // L
        if(up && right){
            startExits = new Exits[]{Exits.NORTH, Exits.EAST};
            maze[startX][startY] = new Point(startExits);
            return startExits;
        }

        // J
        if(up && left){
            startExits = new Exits[]{Exits.NORTH, Exits.WEST};
            maze[startX][startY] = new Point(startExits);
            return startExits;
        }

        // F
        if(down && right){
            startExits = new Exits[]{Exits.SOUTH, Exits.EAST};
            maze[startX][startY] = new Point(startExits);
            return startExits;
        }

        // 7
        if(down && left){
            startExits = new Exits[]{Exits.SOUTH, Exits.WEST};
            maze[startX][startY] = new Point(startExits);
            return startExits;
        }
    throw new IllegalStateException("Could not determine start type");

    }
    static boolean hasLeft(Point[][] maze, int x, int y){
        return y != 0 && maze[x][y-1].hasExit(Exits.EAST);
    }
    static boolean hasRight(Point[][] maze, int x, int y){
        return y != maze[x].length-1  && maze[x][y+1].hasExit(Exits.WEST);
    }
    static boolean hasUp(Point[][] maze, int x, int y){
        return x != 0 && maze[x-1][y].hasExit(Exits.SOUTH);
    }
    static boolean hasDown(Point[][] maze, int x, int y){
        return x != maze.length-1 && maze[x+1][y].hasExit(Exits.NORTH);
    }


}
