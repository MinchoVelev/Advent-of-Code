package y2023;

import java.util.*;

public class Day15P2 {

    static class Pair{
        String key;
        Integer value;

        @Override
        public String toString() {
           return "[" + key + " " + value + "]";
        }

        public Pair(String key, Integer value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair pair = (Pair) o;
            return Objects.equals(key, pair.key);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key);
        }
    }
    static class HM{
        LinkedList<Pair>[] boxes;
        HM(){
            boxes = new LinkedList[256];

            for(int i = 0; i < boxes.length; i++){
                boxes[i] = new LinkedList<Pair>();
            }
        }
        void put(String label, int value){
            LinkedList<Pair> box = boxes[hash(label)];
            for(var p: box){
                if(p.key.equals(label)){
                    p.value = value;
                    return;
                }
            }
            box.addLast(new Pair(label, value));
        }
        void remove(String label){
            boxes[hash(label)].remove(new Pair(label, 0));
        }
    }
    public static void main(String[] args) throws Exception {
        Scanner scanner = IOUtils.getScanner("day15.txt");
        HM map = new HM();
        scanner.useDelimiter(",");
        while (scanner.hasNext()) {
            String nextChar = scanner.next();
            if(nextChar.contains("-")){
                String label = nextChar.split("-")[0];
                map.remove(label);
            }else{
                String[] split = nextChar.split("=");
                String label = split[0];
                Integer focalLength = Integer.parseInt(split[1]);

                map.put(label, focalLength);

            }
        }
        int index = 0;
        for(var b: map.boxes){
            if(b.size()>0){
                System.out.println("Box " + index + " " + b);
            }
            index++;
        }

        long result = 0;

        for(int x = 0; x < map.boxes.length; x++){
            int i = 1;
            for (var p : map.boxes[x]){
                result += (1 + x) * (i++) * (p.value);
            }
        }

        System.out.println("Result: " + result);

    }

    private static int hash(String label) {
        int tmp = 0;
        for(var c: label.toCharArray()){
            tmp += c;
            tmp *= 17;
            tmp = tmp % 256;
        }
        return tmp;
    }
}
