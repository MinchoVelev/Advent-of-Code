package y2023;

import java.sql.SQLOutput;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Day20 {

    static long lows = 0;
    static long highs = 0;

    static class Command {
        boolean signal;
        String target;
        String source;

        public Command(boolean signal, String target, String source) {
            this.signal = signal;
            this.target = target;
            this.source = source;
        }

        @Override
        public String toString() {
            return "Command{" +source + " -> " + target + ": " + signal + "}";
        }
    }

    static final Queue<Command> COMMAND_QUEUE = new ArrayDeque<>();

    static void enqueue(Command c){
        COMMAND_QUEUE.add(c);
        if(c.signal){
            highs++;
        }else{
            lows++;
        }
    }

    static abstract class Channel {
        List<String> targets = new LinkedList<>();
        String name;
        String type;

        public Channel(String name, String type, String... targets) {
            for (var t : targets) {
                this.targets.add(t);
            }
            this.name = name;
            this.type = type;
        }

        abstract void process(boolean signal, String source);

        @Override
        public String toString() {
            return "Channel{" +
                    "targets=" + targets +
                    ", name='" + name + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }
    }

    static class FlipFlop extends Channel {

        private boolean state = false;

        public FlipFlop(String name, String type, String... targets) {
            super(name, type, targets);
        }

        @Override
        void process(boolean signal, String source) {
            if (signal) {
                return;
            }

            state = !state;

            for (var t : targets) {
                enqueue(new Command(state, t, name));
            }

        }
    }

    static class Broadcaster extends Channel {

        public Broadcaster(String name, String type, String... targets) {
            super(name, type, targets);
        }

        @Override
        void process(boolean signal, String source) {
            for (var t : targets) {
                enqueue(new Command(false, t, name));
            }
        }
    }

    static class Conjunction extends Channel {

        Map<String, Boolean> inputStates = new HashMap<>();


        public Conjunction(String name, String type, Collection<String> inputs, String... targets) {
            super(name, type, targets);
            for(var i: inputs){
                inputStates.put(i, false);
            }
        }

        @Override
        void process(boolean signal, String source) {

            inputStates.put(source, signal);
            boolean targetSignal = inputStates.values().stream().filter(b -> b == false).findAny().isPresent();

            for(var t: targets){
                enqueue(new Command(targetSignal, t, name));
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = IOUtils.getScanner("day20.txt");
        Map<String, Channel> channels = new HashMap<>();
        Map<String, String[]> input = new HashMap<>();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] split = line.split(" -> ");
            String name = split[0];
            String[] targets = split[1].split(", ");

            input.put(name, targets);
        }

        for(var i : input.entrySet()){
            String key = i.getKey();
            if(key.startsWith("broadcaster")){
                channels.put(key, new Broadcaster(key, key, i.getValue()));
            }else if(key.startsWith("%")){
                channels.put(key.substring(1), new FlipFlop(key.substring(1), "%", i.getValue()));
            }else if(key.startsWith("&")){
                Set<String> inputs = input.entrySet().stream().filter(e -> {
                    String[] targets = e.getValue();
                    for (var t : targets) {
                        if (t.contains(key.substring(1))) {
                            return true;
                        }
                    }
                    return false;
                }).map(Map.Entry::getKey).map(k -> k.startsWith("%") || k.startsWith("&") ? k.substring(1) : k).collect(Collectors.toSet());

                channels.put(key.substring(1), new Conjunction(key.substring(1), "&", inputs, i.getValue()));
            }
        }

        for(int i = 0; i < 1000; i++){

            enqueue(new Command(false, "broadcaster", "button"));

            while(!COMMAND_QUEUE.isEmpty()){

               for(int j = 0; j < COMMAND_QUEUE.size(); j++){
                   Command command = COMMAND_QUEUE.poll();
                   //System.out.println(command);

                   Channel target = channels.get(command.target);
                   if(target == null){
                       continue;
                   }
                   target.process(command.signal, command.source);
               }

            }

            //System.out.println("\nQueue is empty\n");

        }

        //result
        System.out.println("Lows: " + lows + " highs: " + highs);
        System.out.println(lows * highs);

    }
}
