package y2023;

import java.util.*;

public class Day8P1 {
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
        String nodeAt = "AAA";

        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] sides = line.split("=");
            String key = sides[0].trim();

            String[] nodes = sides[1].replace('(', ' ').replace(')', ' ').trim().split(",\\s*");
            ArrayList<String> nodeList = new ArrayList<>(2);
            nodeList.add(nodes[0]);
            nodeList.add(nodes[1]);
            graph.put(key, nodeList);
        }

        int count = 0;
        while (!nodeAt.equals("ZZZ")) {
            count++;
            List<String> vertices = graph.get(nodeAt);
            char inst = nextInstruction();
            System.out.printf("At node %s, instruction %s\n", nodeAt, inst);
            if (inst == 'L') {
                nodeAt = vertices.get(0);
            } else {
                nodeAt = vertices.get(1);
            }
        }
        System.out.println("Part 1: "+ count);
    }
}
