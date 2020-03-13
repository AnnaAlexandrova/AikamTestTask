package controller;

import com.github.tsohr.JSONObject;
import java.io.*;

public class WritingJsonController {
    public static void createJsonFile(String path, JSONObject jsonObject) {
        try (PrintWriter printWriter = new PrintWriter(path)) {
            String jsonText = jsonObject.toString();

            printWriter.print(jsonText);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
