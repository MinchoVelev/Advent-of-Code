package y2022;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import y2022.utils.IOUtils;

public class Dec5 {
    private static Pattern INSTRUCTION_PATTERN = Pattern.compile("move (\\d+) from (\\d+) to (\\d+)");

    public static void main(String[] args) {
        System.out.println("Part 1:");
        part1();

        System.out.println("-------------------");

        System.out.println("Part 2:");
        part2();
    }

    private static void part1() {
        orchestrate(Dec5::move9000);
    }

    private static void part2() {
        orchestrate(Dec5::move9001);
    }

    private static void orchestrate(MoveFunction f) {
        try (Scanner scanner = IOUtils.getScanner("Dec5.input", Dec5.class)) {
            List<Deque<String>> stacks = loadStacks(scanner);
            System.out.println(stacks);

            while (scanner.hasNext()) {
                String instruction = scanner.nextLine();
                Matcher matcher = INSTRUCTION_PATTERN.matcher(instruction);
                if (!matcher.matches()) {
                    throw new RuntimeException("Instruction doesn't match pattern " + instruction);
                }
                int count = Integer.parseInt(matcher.group(1));
                int from = Integer.parseInt(matcher.group(2));
                int to = Integer.parseInt(matcher.group(3));

                Deque<String> stackFrom = stacks.get(from - 1);
                Deque<String> stackTo = stacks.get(to - 1);

                f.apply(count, stackFrom, stackTo);
            }
            for (Deque<String> r : stacks) {
                System.out.print(r.getLast());
            }
            System.out.println();
        }
    }

    private static void move9000(int count, Deque<String> stackFrom, Deque<String> stackTo) {
        for (int i = 0; i < count; i++) {
            stackTo.addLast(stackFrom.pollLast());
        }
    }

    private static void move9001(int count, Deque<String> stackFrom, Deque<String> stackTo) {
        Deque<String> tmp = new ArrayDeque<>(count);
        for (int i = 0; i < count; i++) {
            tmp.addFirst(stackFrom.pollLast());
        }
        for (int i = 0; i < count; i++) {
            stackTo.addLast(tmp.pollFirst());
        }
    }

    private static List<Deque<String>> loadStacks(Scanner scanner) {
        List<Deque<String>> stacks = new ArrayList<>(16);
        String line = scanner.nextLine();
        int stacksCount = (line.length() + 1) / 4;
        System.out.println("Stack count: " + stacksCount);
        for (int i = 0; i < stacksCount; i++) {
            stacks.add(new ArrayDeque<>());
        }
        int i = 0;
        do {
            System.out.println(line);
            for (int j = 0; j < line.length(); j += 4) {
                String crate = line.substring(j + 1, j + 2);

                if (!" ".equals(crate)) {
                    Deque<String> stack = stacks.get(i % stacksCount);
                    stack.addFirst(crate);
                }
                i++;
            }

            line = scanner.nextLine();

        } while (!line.contains("1"));

        scanner.nextLine(); // skip spacer

        return stacks;
    }

    public interface MoveFunction {
        public void apply(int c, Deque<String> from, Deque<String> to);
    }
}
