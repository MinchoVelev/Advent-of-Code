package y2023;

import java.util.*;

public class Day22P2 {

    static class Brick{
        int[] start;
        int[] end;

        public Brick(int[] start, int[] end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public String toString() {
            return "Brick{" +
                    "start=" + Arrays.toString(start) +
                    ", end=" + Arrays.toString(end) +
                    '}';
        }
    }
    public static void main(String[] args) throws Exception {
        Scanner scanner = IOUtils.getScanner("day22.txt");


        List<Brick> bricks = new ArrayList<>();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();

            String[] split = line.split("~");
            String[] coordinatesStart = split[0].split(",");
            String[] coordinatesEnd = split[1].split(",");

            int[] start = new int[3];
            int[] end = new int[3];

            for(int i = 0; i < 3; i++){
                start[i] = Integer.parseInt(coordinatesStart[i]);
                end[i] = Integer.parseInt(coordinatesEnd[i]);
            }

            bricks.add(new Brick(start, end));
        }

        Collections.sort(bricks, new Comparator<Brick>() {
            @Override
            public int compare(Brick o1, Brick o2) {
                return Math.min(o1.start[2], o1.end[2]) - Math.min(o2.start[2], o2.end[2]);
            }
        });

            for(int i = 0; i < bricks.size(); i++){
                int floor = 0;
                Brick brick = bricks.get(i);
                if(Math.min(brick.start[2], brick.end[2]) == 1){
                    continue;
                }

                for(int j = 0; j < i; j ++ ){
                    if(intersect(brick, bricks.get(j))){
                        floor = Math.max(floor, bricks.get(j).end[2]);
                    }
                }

                brick.end[2] = brick.end[2] - brick.start[2] + floor + 1;
                brick.start[2] = floor + 1;
            }

        HashMap<Integer, HashSet<Integer>> supports = new HashMap<>();
        HashMap<Integer, HashSet<Integer>> supported = new HashMap<>();

        for(int i = 0; i < bricks.size(); i++){
                Brick upperBrick = bricks.get(i);
                for(int j = 0; j < i; j ++){
                    Brick lowerBrick = bricks.get(j);
                    if(upperBrick.start[2] == lowerBrick.end[2] + 1 && intersect(lowerBrick, upperBrick)){
                        HashSet<Integer> supportsSet = supports.get(j);
                        if(supportsSet == null){
                            supportsSet = new HashSet<>();
                            supports.put(j, supportsSet);
                        }
                        HashSet<Integer> supportedSet = supported.get(i);
                        if(supportedSet == null){
                            supportedSet = new HashSet<>();
                            supported.put(i, supportedSet);
                        }

                        supportsSet.add(i);
                        supportedSet.add(j);
                    }
                }
            }
        long count = 0l;

        ArrayDeque<Integer> queue = new ArrayDeque<>();
        System.out.println(supports);
        System.out.println(supported);
        for( int i = 0; i < bricks.size(); i++){
            queue.add(i);
            HashSet<Integer> falling = new HashSet<>();
            falling.add(i);

            while(!queue.isEmpty()){
                Integer current = queue.poll();
                HashSet<Integer> supportedByCurrent = supports.get(current);
                if(supportedByCurrent == null){
                    continue;
                }

                for (var b : supportedByCurrent){
                    HashSet<Integer> supportingCurrent = supported.get(b);
                    HashSet<Integer> tmp = new HashSet<>(supportingCurrent);
                    tmp.removeAll(falling);
                    if(tmp.size() == 0){
                        queue.add(b);
                        falling.add(b);
                    }
                }
            }
            count += falling.size() - 1;
        }

        System.out.println("All fallen: " + count);
    }


    private static boolean intersect(Brick brick, Brick brick1) {
        return Math.max(brick.start[0], brick1.start[0]) <= Math.min(brick.end[0], brick1.end[0]) &&Math.max(brick.start[1], brick1.start[1]) <= Math.min(brick.end[1], brick1.end[1]);
    }
}
