package y2023;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public class Day19P2 {
    static int x = 0;
    static int m = 1;
    static int a = 2;
    static int s = 3;

    static class Part {
        final int[][] ratingRange;

        public Part(int[] x, int[] m, int[] a, int[] s) {
            this.ratingRange = new int[][]{x, m, a, s};
        }

        public Part(Part p) {
            this(new int[]{p.ratingRange[x][0], p.ratingRange[x][1]},
                    new int[]{p.ratingRange[m][0], p.ratingRange[m][1]},
                    new int[]{p.ratingRange[a][0], p.ratingRange[a][1]},
                    new int[]{p.ratingRange[s][0], p.ratingRange[s][1]}
            );
        }

        @Override
        public String toString() {
            return "{" + ratingRange[x][0] + "-" + ratingRange[x][1] + ", " +  ratingRange[m][0] + "-" + ratingRange[m][1]+", "+ratingRange[a][0] + "-" + ratingRange[a][1] + ", " +  ratingRange[s][0] + "-" + ratingRange[s][1] +"}";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Part part = (Part) o;
            return Arrays.equals(ratingRange, part.ratingRange);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(ratingRange);
        }
    }

    static class Rule {
        int index;
        String operator;

        int value;
        String target;

        public Rule(int index, String operator, int value, String target) {
            this.index = index;
            this.operator = operator;
            this.value = value;
            this.target = target;
        }
    }

    static final Map<String, List<Rule>> WORKFLOWS = new HashMap<>();

    public static void main(String[] args) throws Exception {
        Scanner scanner = IOUtils.getScanner("day19.txt");

        ArrayList<Part> parts = new ArrayList<>(1024);

        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            if (line.equals("")) {
                break;
            }


            String[] split = line.split("\\{");
            String name = split[0];
            String[] rules = split[1].replace("}", "").split(",");
            ArrayList<Rule> workflow = new ArrayList<>();
            for (var r : rules) {
                String[] splitRule = r.split(":"); //px{a<2006:qkq,m>2090:A,rfg}
                if (splitRule.length == 1) { //is last rule
                    workflow.add(new Rule(0, "-", 0, splitRule[0]));
                    continue;
                }
                String operator = splitRule[0].contains(">") ? ">" : "<";
                String[] comparisonPart = splitRule[0].split(operator);
                int index = switch (comparisonPart[0]) {
                    case "x" -> x;
                    case "m" -> m;
                    case "a" -> a;
                    case "s" -> s;
                    default -> throw new IllegalStateException("Failed to parse index: " + comparisonPart[0]);
                };

                int value = Integer.parseInt(comparisonPart[1]);

                workflow.add(new Rule(index, operator, value, splitRule[1]));
            }

            WORKFLOWS.put(name, workflow);
        }

        // start processing
        Set<Part> result = new HashSet<>();
        List<Rule> workflow = WORKFLOWS.get("in");
        Part part = new Part(new int[]{1, 4000}, new int[]{1, 4000}, new int[]{1, 4000}, new int[]{1, 4000});

        result.addAll(run(part, workflow));

        System.out.println(result);

        long combinations = result.stream().mapToLong(p -> (long) (p.ratingRange[x][1] - p.ratingRange[x][0] + 1) *
                (p.ratingRange[m][1] - p.ratingRange[m][0] + 1) *
                (p.ratingRange[a][1] - p.ratingRange[a][0] + 1) *
                (p.ratingRange[s][1] - p.ratingRange[s][0] + 1)).sum();
        System.out.println(combinations);
    }

    private static List<Part> run(Part p, List<Rule> workflow) {
        Part duplicate = new Part(p);
        List<Part> result = new LinkedList<>();
        for (var rule : workflow) {
            if (canBePositive(duplicate, rule)) {
                Part newPart = new Part(duplicate);
                int[] partRange = newPart.ratingRange[rule.index];
                if (rule.operator.equals("-")) {
                    if (rule.target.equals("A")) {
                        result.add(newPart);
                    }
                } else if (rule.operator.equals(">")) {
                    partRange[0] = rule.value + 1;
                } else {
                    partRange[1] = rule.value - 1;
                }
                if(rule.target.equals("A")){
                    result.add(newPart);
                }else if (!rule.target.equals("R")){
                    List<Rule> nextWorkflow = WORKFLOWS.get(rule.target);
                    result.addAll(run(newPart, nextWorkflow));
                }
            }
            if (canBeNegative(duplicate, rule)) {
                if (rule.operator.equals(">")) {
                    duplicate.ratingRange[rule.index][1] = rule.value;
                } else {
                    duplicate.ratingRange[rule.index][0] = rule.value;
                }
            } else {
                break;
            }
        }
        return result;
    }

    private static boolean canBePositive(Part part, Rule rule) {
        int[] range = part.ratingRange[rule.index];

        if (rule.operator.equals("-")) {
            return true;
        }
        if (rule.operator.equals(">")) {
            return range[1] > rule.value;
        } else {
            return range[0] < rule.value;
        }
    }

    private static boolean canBeNegative(Part part, Rule rule) {
        int[] range = part.ratingRange[rule.index];

        if (rule.operator.equals("-")) {
            return false;
        }
        if (rule.operator.equals(">")) {
            return range[0] <= rule.value;
        } else {
            return range[1] >= rule.value;
        }
    }
}