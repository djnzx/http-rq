package org.alexr.httprequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class IsIntIdeas {

    static boolean isIntSafe(String input) {
        for (int i = 0; i < input.length(); i++) {
            if (!Character.isDigit(input.charAt(i))) return false;
        }
        return true;
    }

    static boolean isInt(String input) {
        return input != null && isIntSafe(input);
    }

    public static void main(String[] args) throws IOException {
        List<String> strings = Files.readAllLines(Paths.get("1.txt"));
    }
}

