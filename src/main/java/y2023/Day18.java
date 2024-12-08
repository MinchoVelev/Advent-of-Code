package y2023;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Day18 {
    static final byte[] UP = {-1, 0};
    static final byte[] DOWN = {1, 0};
    static final byte[] LEFT = {0, -1};
    static final byte[] RIGHT = {0, 1};
    static class Instruction{
        byte[] direction;
        int moveCount;
        String color;

        public Instruction(byte[] direction, int moveCount, String color) {
            this.direction = direction;
            this.moveCount = moveCount;
            this.color = color;
        }
    }
    public static void main(String[] args) throws Exception {
        Scanner scanner = IOUtils.getScanner("day18.txt");

        List<Instruction> instructions = new LinkedList<>();
        int currentX = 0;
        int currentY = 0;
        int maxX = 0;
        int maxY = 0;
        int minX = 0;
        int minY = 0;
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] split = line.split(" ");

            byte[] direction = switch (split[0]) {
                case "U" -> UP;
                case "D" -> DOWN;
                case "L" -> LEFT;
                case "R" -> RIGHT;
                default -> throw new IllegalStateException("Unknonw direction " + split[0]);
            };

            int count = Integer.parseInt(split[1]);
            String color = split[2].replace("(", "").replace(")", "");
            instructions.add(new Instruction(direction, count, color));

            currentX += direction[0] * count;
            currentY += direction[1] * count;

            if(maxX < currentX){
                maxX = currentX;
            }
            if(maxY < currentY){
                maxY = currentY;
            }

            if( minX > currentX){
                minX = currentX;
            }
            if(minY > currentY){
                minY = currentY;
            }
        }
        int sizeX = maxX + 1 + Math.abs(minX);
        int sizeY = maxY + 1 + Math.abs(minY);
        int startX = Math.abs(minX);
        int startY = Math.abs(minY);

        System.out.printf("Min %d %d | Max %d %d. Will build maze %d %d with starting point %d %d\n", minX, minY, maxX, maxY, sizeX, sizeY, startX, startY);

        char[][] maze = new char[sizeX][sizeY];

        for(int i = 0; i < maze.length; i++){
            Arrays.fill(maze[i], '.');
        }



        currentX = startX;
        currentY = startY;
        int instruction = 0;
        for(var i: instructions){
            for(int c = 0; c < i.moveCount; c++){
                maze[currentX + c * i.direction[0]][currentY + c*i.direction[1]] = '#';
            }
            currentX += i.moveCount*i.direction[0];
            currentY += i.moveCount*i.direction[1];
        }

        int count = 0;
        List<int[]> fill = new LinkedList<>();
        for(int i = 1; i < maze.length-1; i++){
            for (int j = 0; j < maze[i].length; j++){
              if(maze[i][j] == '#'){
                  count++;
                  continue;
              }

              int collisions = 0;
              boolean streak = false;
              int streakSize = 0;
              int[] streakBeginning = null;
              int[] streakEnd = null;
              for(int y = j - 1; y >= 0; y--){
                  boolean isWall =maze[i][y] == '#';
                  if(isWall){
                      if(!streak){
                          streak = true;
                          streakBeginning = new int[]{i, y};
                          streakSize++;
                          collisions++;
                      }else{
                          streakSize++;
                      }
                  }
                  if(!isWall || y == 0){
                      if(streak){
                          if(streakSize > 1){
                              streakEnd = new int[]{i, isWall ? y : y + 1};
                              if(i != 0 && i != maze.length -1){
                                  if(maze[i+1][streakBeginning[1]] == '#' && maze[i+1][streakEnd[1]] == '#'){
                                      collisions--;
                                  }else if (maze[i-1][streakBeginning[1]] == '#' && maze[i-1][streakEnd[1]] == '#'){
                                      collisions--;
                                  }
                              }
                          }
                          streak = false;
                          streakSize = 0;
                      }
                  }
              }
              if(collisions % 2 == 1){
                  fill.add(new int[]{i, j});
              }
            }
        }

        for(var s : fill){
            maze[s[0]][s[1]] = 'O';
        }

        int total = 0;
        for(int i = 0; i < maze.length; i++){
            for (int j = 0; j < maze[i].length; j++){
                System.out.print(maze[i][j]);
                if(maze[i][j] != '.'){
                    total++;
                }
            }
            System.out.println();
        }

        System.out.println("Fill count is: " + total);

    }
}
