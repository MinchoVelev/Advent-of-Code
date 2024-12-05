package y2023;

import java.sql.Array;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day8P2 {
    static char[] instructions;
    static int index = 0;
    static int mod = 1;

    static char nextInstruction(){
        if(index == mod){
            index = 0;
        }
        return instructions[index++];
    }
    public static void main(String[] args) throws Exception {
        Map<String, List<String>> graph = new HashMap<>();
        Scanner scanner = IOUtils.getScanner("day8.txt");
        instructions = scanner.nextLine().trim().toCharArray();
        mod = instructions.length;
        scanner.nextLine(); //empty line

        List<String> currentNodes = new ArrayList<>();

        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] sides = line.split("=");
            String key = sides[0].trim();

            if(key.endsWith("A")){
                currentNodes.add(key);
            }

            String[] nodes = sides[1].replace('(', ' ').replace(')', ' ').trim().split(",\\s*");
            ArrayList<String> nodeList = new ArrayList<>(2);
            nodeList.add(nodes[0]);
            nodeList.add(nodes[1]);
            graph.put(key, nodeList);
        }

        System.out.println("Nodes ending in A " + currentNodes);
        long count = 0l;
        ArrayList<Long> results = new ArrayList<>();
        while (currentNodes.size() > 0) {
            if(currentNodes.stream().filter(s-> s.endsWith("Z")).count() > 0){
                results.add(count);
                System.out.println("Found a match at " + count);
            }
            currentNodes.removeIf(s-> s.endsWith("Z"));
            count++;
            char inst = nextInstruction();
            for(int i =0; i < currentNodes.size(); i++) {
                String nodeAt = currentNodes.get(i);
                List<String> vertices = graph.get(nodeAt);
                if (inst == 'L') {
                    nodeAt = vertices.get(0);
                } else {
                    nodeAt = vertices.get(1);
                }
                currentNodes.set(i, nodeAt);
            }
        }

        long[] p = new long[1000]; // 1000 feels like a safe number
        
        for(int i = 0; i < 1000; i++){
            p[i] = findAPrime(i, p);
        }



        Map<Long, Long> primeFactors = new HashMap<>();
        for(Long r : results){
            List<Long> tmpPrimes = new ArrayList<>();
            Long tmpR = r;
            for(long prime : p){
                while(tmpR % prime == 0){
                    tmpPrimes.add(prime);
                    tmpR = tmpR / prime;
                }
            }

            Map<Long, Long> primeCounts = tmpPrimes.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            for(Map.Entry<Long, Long> e : primeCounts.entrySet()){
                if(primeFactors.containsKey(e.getKey())){
                    Long aLong = primeFactors.get(e.getKey());
                    if(aLong < e.getValue()){
                        primeFactors.put(e.getKey(),e.getValue());
                    }
                }else{
                    primeFactors.put(e.getKey(),e.getValue());
                }
            }
        }



        System.out.println(primeFactors);
        long result = 1;
        for(Map.Entry<Long, Long> e : primeFactors.entrySet()){
            result *= Math.pow(e.getKey(), e.getValue());
        }

        System.out.println("Part 2: "+ result);
    }

    private static long findAPrime(int i, long[] p) {
        if(i == 0){
            return 2;
        }
        long n = 0l;
        a: for( n = p[i-1] + 1; n < Long.MAX_VALUE; n++){

            for(long j = i-1; j>=0; j--){
                if(n % p[(int) j] == 0){
                    continue a;
                }
            }
            return n;
        }
        return -1;
    }
}
