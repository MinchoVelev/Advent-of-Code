package y2023;

import java.util.*;

public class Day12P2 {
    public static void main(String[] args) throws Exception {
        Scanner scanner = IOUtils.getScanner("day12.txt");

        long sum = 0l;
        int index = 0;
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            String input = parts[0];
            int[] tmpTargets = Arrays.stream(parts[1].split(",")).mapToInt(Integer::valueOf).toArray();
            int[] targets = new int[tmpTargets.length*5];
            for(int i = 0; i < targets.length; i += tmpTargets.length){
                System.arraycopy(tmpTargets, 0, targets, i, tmpTargets.length);
            }

            StringBuilder sb = new StringBuilder(input.length() * 5 + 5);
            for(int i = 0; i < 5; i ++){
               sb.append(input).append("?");
            }
            sb.setLength(sb.length() - 1);
            input = sb.toString();

            long permutations = getPermutations(input, targets);
            //System.out.prlongln("Line " + index++ + " " + input + " " + Arrays.toString(targets) + " result " + permutations );
            sum += permutations;
            CACHE.clear();
        }

        System.out.println("Correct permutations: " + sum);

    }

    static class Key{
        String i;
        int[] t;

        public Key(String i, int[] t) {
            this.i = i;
            this.t = t;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Key key = (Key) o;
            return Objects.equals(i, key.i) && Arrays.equals(t, key.t);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(i);
            result = 31 * result + Arrays.hashCode(t);
            return result;
        }
    }

    static long cache(Key key, long result){
        CACHE.put(key, result);
        return result;
    }
    static Map<Key, Long>  CACHE = new HashMap<>();
    // . = working and # = broken
    private static long getPermutations(String input, int[] targets) {
        //System.out.prlongln("checking " + input + " with targets " + Arrays.toString(targets));
        Key key = new Key(input, targets);
        if(CACHE.containsKey(key)){
            return CACHE.get(key);
        }
        if(targets.length == 0){
            if(input.contains("#")){
                return cache(key, 0);
            }else{
                return cache(key,1);
            }
        }

        if(input.length() == 0){
            if(targets.length == 0){
                return cache(key, 1);
            }else{
                return cache(key, 0);
            }
        }

        if(input.charAt(0) == '.'){
            return getPermutations(input.substring(1), targets);
        }
        else if(input.charAt(0) == '#'){
            if(input.length() < targets[0] || input.substring(0, targets[0]).contains(".")){
                return cache(key,0);
            }
            else if(input.length() > targets[0] && input.charAt(targets[0]) == '#'){
                return cache(key,0);
            }
            return getPermutations(input.substring(Math.min(targets[0] + 1, input.length())), Arrays.copyOfRange(targets, 1, targets.length));
        }else if(input.charAt(0) == '?'){
            long brokenPermutations = getPermutations("#" + input.substring(1), targets.clone());
            long workingPermutations = getPermutations("." + input.substring(1), targets.clone());
            //System.out.prlongln("? result = " + (brokenPermutations + workingPermutations));
            return cache(key,brokenPermutations + workingPermutations);
        }
        throw new IllegalStateException("Should match some previous case");
    }

}
