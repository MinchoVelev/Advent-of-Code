package y2023;

import com.sun.security.jgss.GSSUtil;

import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

public class Day5 {
    public static void main(String[] args) throws Exception {
        Scanner scanner = IOUtils.getScanner("day5.txt");
        long[] seeds = Arrays.stream(scanner.nextLine().split(":")[1].trim().split("\\s+")).mapToLong(Long::valueOf).toArray();

        ArrayList<Mapper> mappers = createMappers(scanner);
        long min = Long.MAX_VALUE;

        for(int i = 0; i < seeds.length; i++){
                long mappedValue = seeds[i];
                for (Mapper m : mappers) {
                    mappedValue = m.map(mappedValue);
                }
                if(mappedValue < min){
                    min = mappedValue;
                }
        }

        System.out.println("Part 1: " + min);


        long min2 = Long.MAX_VALUE;

        for(int i = 0; i < seeds.length; i+= 2){
            for (long j = seeds[i]; j <  seeds[i] + seeds[i+1]; j++){


            long mappedValue = j;
            for (Mapper m : mappers) {
                mappedValue = m.map(mappedValue);
            }
            if(mappedValue < min2){
                min2 = mappedValue;
            }
            }
        }

        System.out.println("Part 2: " + min2);
    }

    private static ArrayList<Mapper> createMappers(Scanner scanner) {
        ArrayList<Mapper> mappers = new ArrayList<>(7);
        for(int i = 0; i < 7; i++){
            mappers.add(i, new Mapper());
        }

        int index = -1;
        while(scanner.hasNext()) {
            String line = scanner.nextLine();
            if(line.isBlank()){
                continue;
            }
            if(line.contains("map:")){
                index++;
                continue;
            }

            long[] mappingValues = Arrays.stream(line.split("\\s+")).mapToLong(Long::valueOf).toArray();
            mappers.get(index).putInterval(mappingValues[1], mappingValues[0], mappingValues[2]);
        }
        return mappers;
    }

    static class Mapper{
        Node root;
        void putInterval(long sourceIntervalStart, long targetIntervalStart, long intervalSize){
           root = insert(root, sourceIntervalStart, sourceIntervalStart + intervalSize - 1, targetIntervalStart - sourceIntervalStart);
        }

        Node insert(Node parent, long sourceIntervalStart, long sourceIntervalEnd, long diff ){
            if (parent == null){
                return new Node(sourceIntervalStart, sourceIntervalEnd, diff);
            }
            if(parent.intervalStart > sourceIntervalStart){

             parent.left = insert(parent.left, sourceIntervalStart, sourceIntervalEnd, diff);

            }else if (parent.intervalStart < sourceIntervalStart){

                    parent.right = insert(parent.right, sourceIntervalStart, sourceIntervalEnd, diff);
            }else {
                return parent;
            }
            return parent;
        }

        void print(Node n){
            if(n == null){
                return;
            }
            System.out.printf("node [%d,%d] ", n.intervalStart, n.intervalEnd);
            if(n.left != null)
            System.out.printf("left [%d,%d] ", n.left.intervalStart, n.left.intervalEnd);
            if(n.right != null)
            System.out.printf("right [%d,%d] ", n.right.intervalStart, n.right.intervalEnd);
            System.out.println();
            System.out.println();
            print(n.left);
            print(n.right);
        }

        long map(long number){
           long mapping = findMapping(root, number);
           return mapping + number;
        }

        private long findMapping(Node node, long number) {
            if(node == null){
                return 0;
            }
            if(node.intervalStart <= number && node.intervalEnd >= number){
                return node.diff;
            }

            if(node.intervalStart >= number){
                return findMapping(node.left, number);
            }
            return findMapping(node.right, number);
        }
    }
    static class Node{
        public Node(long intervalStart, long intervalEnd, long diff) {
            this.intervalStart = intervalStart;
            this.intervalEnd = intervalEnd;
            this.diff = diff;
        }

        long intervalStart;
        long intervalEnd;
        long diff;
        Node left;
        Node right;
    }
}
