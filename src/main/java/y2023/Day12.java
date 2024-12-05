package y2023;

import java.util.*;

public class Day12 {
    public static void main(String[] args) throws Exception {
        Scanner scanner = IOUtils.getScanner("day12.txt");

        long sum = 0l;
        int index = 0;
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            String input = parts[0];
            int[] targets = Arrays.stream(parts[1].split(",")).mapToInt(Integer::valueOf).toArray();

            long permutations = getPermutations(input, targets);
            System.out.println("Line " + index++ + " " + input + " " + Arrays.toString(targets) + " result " + permutations );
            sum += permutations;
        }

        System.out.println("Correct permutations: " + sum);

    }
    // . = working and # = broken
    private static long getPermutations(String input, int[] targets) {
        //System.out.println("checking " + input + " with targets " + Arrays.toString(targets));

        if(targets.length == 0){
            if(input.contains("#")){
                return 0;
            }else{
                return 1;
            }
        }

        if(input.length() == 0){
            if(targets.length == 0){
                return 1;
            }else{
                return 0;
            }
        }

        if(input.charAt(0) == '.'){
            return getPermutations(input.substring(1), targets);
        }
        else if(input.charAt(0) == '#'){
            if(input.length() < targets[0] || input.substring(0, targets[0]).contains(".")){
                return 0;
            }
            else if(input.length() > targets[0] && input.charAt(targets[0]) == '#'){
                return 0;
            }
            return getPermutations(input.substring(Math.min(targets[0] + 1, input.length())), Arrays.copyOfRange(targets, 1, targets.length));
        }else if(input.charAt(0) == '?'){
            long brokenPermutations = getPermutations("#" + input.substring(1), targets.clone());
            long workingPermutations = getPermutations("." + input.substring(1), targets.clone());
            //System.out.println("? result = " + (brokenPermutations + workingPermutations));
            return brokenPermutations + workingPermutations;
        }
        throw new IllegalStateException("Should match some previous case");
    }
}
