package y2023;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day19 {
    static int x = 0;
    static int m = 1;
    static int a = 2;
    static int s = 3;

    static class Part {
        final int[] rating;

        public Part(int x, int m, int a, int s) {
            this.rating = new int[]{x, m, a, s};
        }

        int x() {
            return rating[0];
        }

        int m() {
            return rating[1];
        }

        int a() {
            return rating[2];
        }

        int s() {
            return rating[3];
        }

        @Override
        public String toString() {
            return "Part{" +
                    "rating=" + Arrays.toString(rating) +
                    '}';
        }
    }

    static class Rule {
        Predicate<Part> condition;
        String target;

        public Rule(Predicate<Part> condition, String target) {
            this.condition = condition;
            this.target = target;
        }

        @Override
        public String toString() {
            return "Rule{" +
                    "condition=" + condition +
                    ", target='" + target + '\'' +
                    '}';
        }
    }

    static final Map<String, List<Rule>> WORKFLOWS = new HashMap<>();
    static Pattern partPattern = Pattern.compile(".*x=(\\d+).+m=(\\d+).+a=(\\d+).+s=(\\d+).*");

    public static void main(String[] args) throws Exception {
        Scanner scanner = IOUtils.getScanner("day19.txt");

        boolean readingParts = false;
        ArrayList<Part> parts = new ArrayList<>(1024);

        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            if (line.equals("")) {
                readingParts = true;
                continue;
            }

            if (readingParts) {
                Matcher matcher = partPattern.matcher(line);
                if (!matcher.matches()) {
                    throw new IllegalStateException("Failed to parse part " + line);
                }
                int x = Integer.parseInt(matcher.group(1));
                int m = Integer.parseInt(matcher.group(2));
                int a = Integer.parseInt(matcher.group(3));
                int s = Integer.parseInt(matcher.group(4));

                parts.add(new Part(x, m, a, s));

            } else {

                String[] split = line.split("\\{");
                String name = split[0];
                String[] rules = split[1].replace("}", "").split(",");
                ArrayList<Rule> workflow = new ArrayList<>();
                for (var r : rules) {
                    String[] splitRule = r.split(":"); //px{a<2006:qkq,m>2090:A,rfg}

                    if (splitRule.length == 1) {
                        workflow.add(new Rule(s -> true, splitRule[0]));
                    } else {
                        String comparator = splitRule[0].contains(">") ? ">" : "<";
                        String[] splitCondition = splitRule[0].split(comparator);
                        String metric = splitCondition[0];
                        int value = Integer.parseInt(splitCondition[1]);
                        Predicate<Part> p;
                        Function<Part, Integer> accessor = switch (metric) {
                            case "x" -> part -> part.x();
                            case "m" -> part -> part.m();
                            case "a" -> part -> part.a();
                            case "s" -> part -> part.s();
                            default -> throw new IllegalStateException("Failed to parse metric " + metric);
                        };
                        if (comparator.equals(">")) {
                            p = part -> accessor.apply(part) > value;
                        } else {
                            p = part -> accessor.apply(part) < value;
                        }
                        workflow.add(new Rule(p, splitRule[1]));
                    }

                    WORKFLOWS.put(name, workflow);
                }
            }
        }
        //Start applying
        List<Part> accepted = new LinkedList<>();
        List<Part> rejected = new LinkedList<>();

        for(var part : parts) {
            List<Rule> workflow = WORKFLOWS.get("in");
            outer:
            do {
                for (var r : workflow) {
                    if (r.condition.test(part)) {
                        if (r.target.equals("A")) {
                            accepted.add(part);
                            break outer;
                        } else if (r.target.equals("R")) {
                            rejected.add(part);
                            break outer;
                        } else {
                            workflow = WORKFLOWS.get(r.target);
                            break;
                        }
                    }
                }
            } while (true);
        }
        System.out.println(accepted.size());
        System.out.println(rejected.size());

        Integer sum = accepted.stream().mapToInt(p -> p.x() + p.m() + p.a() + p.s()).sum();
        System.out.println("Sum is: " + sum);
    }
}
