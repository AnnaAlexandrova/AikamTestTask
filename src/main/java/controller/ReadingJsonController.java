package controller;

import java.io.FileInputStream;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class ReadingJsonController {
    public static String readJsonFile(String path) {
        try {
            Scanner scanner = new Scanner(new FileInputStream(path));
            StringBuilder json = new StringBuilder();

            while (scanner.hasNext()) {
                json.append(scanner.nextLine());
            }

            return json.toString();
        } catch (FileNotFoundException e) {
            return null;
        }
    }
}
