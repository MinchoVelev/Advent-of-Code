package y2023;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day7P2 {
    public static void main(String[] args) throws Exception {
        Scanner scanner = IOUtils.getScanner("day7.txt");

        ArrayList<Hand> hands = new ArrayList<>();

        while (scanner.hasNext()) {
            String line = scanner.nextLine().trim();
            String[] split = line.split("\\s+");
            hands.add(new Hand(split[0], Long.valueOf(split[1])));
        }

        hands.sort(new HandComparator());

        long winnings = 0l;
        for (int i = 0; i < hands.size(); i++) {
            winnings += hands.get(i).bid * (i + 1);
        }

        System.out.println("Part1: " + winnings);
    }

    static class Hand {
        public Hand(String cards, long bid) {
            this.cards = cards;
            this.bid = bid;
            rank = rank(cards);
        }

        String cards;
        long bid;
        int rank;

        static int rank(String cards) {

            Map<String, Long> cardsInGroups = Arrays.stream(cards.split("")).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            int jCount = 0;

            if (cardsInGroups.containsKey("J")) {
                jCount = ((Long) cardsInGroups.remove("J")).intValue();
                if(jCount == 5){
                    cardsInGroups.put("J", 5l);
                    jCount = 0;
                }
            }

            ArrayList<Long> sorted = new ArrayList<>(cardsInGroups.size());
            sorted.addAll(cardsInGroups.values());
            sorted.sort(Collections.reverseOrder());

            sorted.set(0, sorted.get(0)+jCount);

            return sorted.stream().map(s -> s*s).mapToInt(Long::intValue).sum();
        }

        @Override
        public String toString() {
            return "Hand{" +
                    "cards='" + cards + '\'' +
                    ", bid=" + bid +
                    '}';
        }
    }

    static class HandComparator implements Comparator<Hand> {
        static final Map<Character, Integer> ORDERED = new HashMap<>();

        static {
            ORDERED.put('2', 1);
            ORDERED.put('3', 2);
            ORDERED.put('4', 3);
            ORDERED.put('5', 4);
            ORDERED.put('6', 5);
            ORDERED.put('7', 6);
            ORDERED.put('8', 7);
            ORDERED.put('9', 8);
            ORDERED.put('T', 9);
            ORDERED.put('J', 0);
            ORDERED.put('Q', 10);
            ORDERED.put('K', 11);
            ORDERED.put('A', 12);
        }

        @Override
        public int compare(Hand o1, Hand o2) {
            int rankDiff = o1.rank - o2.rank;
            if (rankDiff != 0) {
                return rankDiff;
            }

            char[] o1c = o1.cards.toCharArray();
            char[] o2c = o2.cards.toCharArray();
            for (int i = 0; i < 5; i++) {
                Integer value1 = ORDERED.get(o1c[i]);
                Integer value2 = ORDERED.get(o2c[i]);

                if (value1 != value2) {
                    return value1 - value2;
                }
            }

            return 0;
        }
    }
}
