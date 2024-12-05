package y2023;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
https://en.wikipedia.org/wiki/Minimum_cut
https://en.wikipedia.org/wiki/Karger%27s_algorithm
 */
public class Day25 {
    static final Map<String, HashSet<String>> graph = new TreeMap<>();
    static Map<String, Integer> traffic;
    public static void main(String[] args) throws Exception {
        Scanner scanner = IOUtils.getScanner("day25.txt");


        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] nodes = line.replace(":", "").split(" ");

            for(int i = 1; i < nodes.length; i++){
                add(nodes[0], nodes[i]);
                //System.out.println(nodes[0] + " " + nodes[i]);
                add(nodes[i], nodes[0]);
            }
        }
        int max = graph.size() - 1;
        int min = 0;

        int connections = graph.entrySet().stream().mapToInt(e -> e.getValue().size()).sum() / 2;
        traffic = new HashMap<>(connections);

        for( int i = 0; i < 1000; i ++){
            int from = (int) ((Math.random() * (max - min)) + min);
            int to = (int) ((Math.random() * (max - min)) + min);

            if( from == to){
                i = i-1;
                continue;
            }
            findPath(from, to);


        }

        List<String> topConnections = printTop(3);
        //System.out.println(graph);
        String[] startingPoints = null;
        for(var c : topConnections){
            startingPoints = c.split(":");
            cut(startingPoints[0], startingPoints[1]);
        }

        //System.out.println(graph);

        HashSet<String> visited1 = new HashSet<>();
        countVertices(startingPoints[0], visited1);

        HashSet<String> visited2 = new HashSet<>();
        countVertices(startingPoints[1], visited2);

        System.out.println(visited1.size() + " * " + visited2.size());

        System.out.println("Size multiplied: " + ((visited1.size()) * visited2.size()));
    }

    private static void countVertices(String startingPoint, HashSet<String> visited) {

        HashSet<String> nodes = graph.get(startingPoint);
        int newInserts = 0;
        for(var n : nodes){
            if(visited.add(n)){
                newInserts++;
                countVertices(n, visited);
            }
        }
    }

    private static void cut(String s, String s1) {
        HashSet<String> strings = graph.get(s);
        strings.remove(s1);
        HashSet<String> strings1 = graph.get(s1);
        strings1.remove(s);
    }

    private static void mark(String from, String to){
        int i = from.compareTo(to);
        String key;
        if(i > 0){
            key = from + ":" + to;
        }else{
            key = to+ ":" +from;
        }
        Integer trafficCount = traffic.computeIfAbsent(key, k -> 0);
        trafficCount += 1;
        traffic.put(key, trafficCount);
    }

    private static List<String> printTop(int top){
        LinkedList<String> topConnections = new LinkedList<>();
        for( int i = 0; i < top; i++){
            Integer max = traffic.values().stream().max(Integer::compareTo).get();
            String maxKey = traffic.entrySet().stream().filter(e -> e.getValue().equals(max)).map(e -> e.getKey()).findAny().get();
            System.out.println("Top " + (i + 1) + ": " + maxKey + " with " + max + " usages");
            traffic.remove(maxKey);
            topConnections.add(maxKey);
        }
        return topConnections;
    }

    static class Move{
        String cameFrom;
        String current;
        int weight;

        public Move(String cameFrom, String current, int weight) {
            this.cameFrom = cameFrom;
            this.current = current;
            this.weight = weight;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Move move = (Move) o;
            return Objects.equals(current, move.current);
        }

        @Override
        public int hashCode() {
            return Objects.hash(current);
        }

        @Override
        public String toString() {
            return "Move{" +
                    "cameFrom='" + cameFrom + '\'' +
                    ", current='" + current + '\'' +
                    ", weight=" + weight +
                    '}';
        }

        static class MoveComparator implements Comparator<Move>{

            @Override
            public int compare(Move o1, Move o2) {
                return o1.weight - o2.weight;
            }
        }
    }

    private static void findPath(int from, int to) {
        String nodeFrom = null;
        String nodeTo = null;
        Iterator<String> iterator = graph.keySet().iterator();
        // can be done in one go

        for( int i = 0; i <= from; i++){
            nodeFrom = iterator.next();
        }

        iterator =graph.keySet().iterator();

        for( int i = 0; i <= to; i++){
            nodeTo = iterator.next();
        }

        PriorityQueue<Move> queue = new PriorityQueue<>(1200, new Move.MoveComparator());

        queue.add(new Move(nodeFrom, nodeFrom, 0));

        HashSet<Move> seen = new HashSet<>();



        while(!queue.isEmpty()){
            Move currentMove = queue.poll();

            if(nodeTo.equals(currentMove.current)){
                seen.add(currentMove);
                break;
            }

            Optional<Move> seenMove = seen.stream().filter(currentMove::equals).findAny();
            if(seenMove.isPresent()){
                Move move = seenMove.get();
                if(move.weight > currentMove.weight){
                    seen.add(currentMove);
                }
                continue;
            }

            for(var node : graph.get(currentMove.current)){
                if(currentMove.cameFrom.equals(node)){
                    continue;
                }
                queue.add(new Move(currentMove.current, node, currentMove.weight + 1));
            }

            seen.add(currentMove);
        }
        String finalNodeTo = nodeTo;
        Move tmp = seen.stream().filter(n -> n.current.equals(finalNodeTo)).findFirst().get();

        while(!nodeFrom.equals(tmp.current) ){
            mark(tmp.current, tmp.cameFrom);
            Move finalTmp = tmp;
            tmp = seen.stream().filter(n -> finalTmp.cameFrom.equals(n.current)).findFirst().get();
        }
    }



    static void add(String a, String b){
        HashSet<String> node = graph.computeIfAbsent(a, s -> new HashSet<>(128));
        node.add(b);
    }
}
