package y2022.utils;

import java.io.BufferedInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class IOUtils {
    public static Scanner getScanner(String fileName, Class<?> clazz) {
        return new Scanner(new BufferedInputStream(clazz.getResourceAsStream(fileName)), StandardCharsets.UTF_8);
    }
}