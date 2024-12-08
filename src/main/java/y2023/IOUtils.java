package y2023;

import java.io.BufferedInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class IOUtils {
    public static Scanner getScanner(String fileName) {
        return new Scanner(new BufferedInputStream(IOUtils.class.getResourceAsStream(fileName)), StandardCharsets.UTF_8);
    }
}
