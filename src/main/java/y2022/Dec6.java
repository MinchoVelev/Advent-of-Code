package y2022;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Dec6 {
    public static void main(String[] args) throws Exception{
        System.out.println("Part 1:");
        part1();

        System.out.println("-------------------");

        System.out.println("Part 2:");
        part2();
    }
    private static void part1()throws Exception{
        detect(4);
    }

    private static void part2()throws Exception{
        detect(14);
    }
    private static void detect(int uniqueCount) throws IOException {

        try (BufferedInputStream is = new BufferedInputStream(Dec6.class.getResourceAsStream("Dec6.input"))) {
            List<String> inputs = new ArrayList<>(512);
            byte[] buff = new byte[1];

            for(int j = 0; j < uniqueCount; j++){
                inputs.add(readNext(is, buff));
            }
            
            Set<String> check;
            for (int i = uniqueCount - 1; is.available() > 0; i++) {
                check = new HashSet<>();
                inputs.add(readNext(is, buff));

                for(int j = 0; j < uniqueCount; j++){
                    check.add(inputs.get(i - j));
                }
                
                if (check.size() == uniqueCount) {
                    System.out.println(i + 1);
                    break;
                }
            }
        }
    }

    private static String readNext(BufferedInputStream is, byte[] buff) throws IOException {
        is.read(buff);
        return new String(buff, StandardCharsets.UTF_8);
    }

}
