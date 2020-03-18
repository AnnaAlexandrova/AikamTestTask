package Main;

import controller.WritingJsonController;
import converter.ConvertStatStringToJson;
import converter.ConverterSearchStringToJson;
import controller.ReadingJsonController;
import model.Search;
import model.Stat;
import com.github.tsohr.JSONObject;
import com.github.tsohr.JSONArray;

public class AikamTaskMain {
    private String type;
    private String inputPath;
    private String outputPath;
    private String errorMessage;
    private boolean hasError;

    private JSONObject outputJson = new JSONObject();

    public AikamTaskMain(String[] param) {
        if (param.length != 3) {
            this.hasError = true;
            this.errorMessage = "Неверно указаны параметры программы. Должно быть три параметра";
        } else {
            this.type = param[0];
            this.inputPath = param[1];
            this.outputPath = param[2];
            this.hasError = false;
        }
    }

    public void run() {
        String jsonText = ReadingJsonController.readJsonFile(inputPath);

        if (jsonText == null) {
            this.hasError = true;
            this.errorMessage = "Ошибка при обработке файла Проверьте наличие файла по указанному пути.";
        }

        JSONObject result = new JSONObject();

        if (!hasError) {
            if (type.equals("search")) {
                JSONArray jsonArray = ConverterSearchStringToJson.convert(jsonText);

                if (jsonArray == null) {
                    this.hasError = true;
                    this.errorMessage = "Неправильный формат входящего json-файла. " +
                            "Не найден объект json с ключом [criteria]";
                    return;
                }

                Search searchResult = new Search();
                result = searchResult.getSearchResult(jsonArray);
            } else if (type.equals("stat")) {
                if (!jsonText.contains("startDate") || !jsonText.contains("endDate")) {
                    this.hasError = true;
                    this.errorMessage = "Неправильный формат входящего json-файла. " +
                            "Не найден объект json с ключом [startDate] или [endDate]";
                    return;
                }

                JSONObject statParam = ConvertStatStringToJson.convert(jsonText);

                if (statParam == null) {
                    this.hasError = true;
                    this.errorMessage = "Неправильный формат входящего json-файла.";
                    return;
                }

                Stat stat = new Stat();
                result = stat.getStat(statParam);
            } else {
                this.hasError = true;
                this.errorMessage = "Ошибка в первом параметре. Возможные варианты: search, stat";
            }
        }

        if (!hasError) {
            outputJson = result;
        }
    }

    public void createResultFile() {
        if (hasError) {
            outputJson.put("type", "error");
            outputJson.put("message", errorMessage);
        } else if (outputJson == null) {
            outputJson = new JSONObject();

            outputJson.put("type", "error");
            this.errorMessage = "Ошибка при обработке запроса к БД. Проверьте корректность ввденных данных и соединение с БД.";
            outputJson.put("message", errorMessage);
        }

        WritingJsonController.createJsonFile(outputPath, outputJson);
    }

    public static void main(String[] args) {
        try {
            AikamTaskMain aikamTask = new AikamTaskMain(args);
            aikamTask.run();
            aikamTask.createResultFile();
        } catch (NullPointerException e) {
            JSONObject errorObj = new JSONObject();

            errorObj.put("type", "error");
            errorObj.put("message", "Не введены аргументы программы." +
                    " Пример запуска  java -jar program.jar search input.json output.json ");

            WritingJsonController.createJsonFile("Output.json", errorObj);
        }
    }
}
