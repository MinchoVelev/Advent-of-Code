package y2023;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Day18P2 {
    static final byte[] UP = {-1, 0};
    static final byte[] DOWN = {1, 0};
    static final byte[] LEFT = {0, -1};
    static final byte[] RIGHT = {0, 1};
    public static void main(String[] args) throws Exception {
        Scanner scanner = IOUtils.getScanner("day18.txt");

        long currentX = 0;
        long currentY = 0;

        long boundaryPoints = 0;
        long[] prev = new long[]{-1, -1};
        long area = 0;

        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String code = line.split(" ")[2];
            long count = Integer.parseInt(code.substring(2, code.length() - 2), 16);
            long directionNumber = Long.parseLong(code.substring(code.length() - 2, code.length() - 1));

            byte[] direction = switch ((int) directionNumber) {
                case 3 -> UP;
                case 1 -> DOWN;
                case 2 -> LEFT;
                case 0 -> RIGHT;
                default -> throw new IllegalStateException("Unknonw direction " + directionNumber);
            };

            // https://en.wikipedia.org/wiki/Shoelace_formula
            // https://artofproblemsolving.com/wiki/index.php/Shoelace_Theorem
                boundaryPoints += count;
                for(long c = 0; c < count; c++){
                    long newX = currentX + c * direction[0];
                    long newY = currentY + c * direction[1];
                    if(prev[0] != -1){
                        area += ((long)(newX + prev[0]) * (newY - prev[1])) / 2;
                    }
                    prev[0] = newX;
                    prev[1] = newY;

                }
                currentX += count*direction[0];
                currentY += count*direction[1];
        }

        long A = Math.abs(area); // inner area only

        // https://en.wikipedia.org/wiki/Pick%27s_theorem

        // i: interior points, b: boundary points, A: area
        // A = i + (b/2) - 1 -> i = A - (b/2) + 1
        long interiorPoints = A - boundaryPoints / 2 + 1;
        System.out.println("Boundary points: " + boundaryPoints + " inner area: " + A + " boundary points " + boundaryPoints);

        System.out.println(interiorPoints + boundaryPoints);
    }
}
