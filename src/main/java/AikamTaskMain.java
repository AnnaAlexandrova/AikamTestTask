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
                    this.errorMessage = "Ошибка при чтении файла, проверьте правильность структуры входного файла.";
                    this.hasError = true;
                }

                Search searchResult = new Search();
                result = searchResult.getSearchResult(jsonArray);
            } else if (type.equals("stat")) {
                JSONObject statParam = ConvertStatStringToJson.convert(jsonText);

                Stat stat = new Stat();
                result = stat.getStat(statParam);
            } else {
                this.hasError = true;
                this.errorMessage = "Ошибка в первом параметре. Возможные варианты: search stat";
            }
        }

        if (hasError) {
            outputJson.put("type", "error");
            outputJson.put("message", errorMessage);
        } else if (result == null) {
            outputJson.put("type", "error");
            this.errorMessage = "Ошибка при обработке запроса к БД. Проверьте корректность ввденных данных.";
            outputJson.put("message", errorMessage);
        } else {
            outputJson = result;
        }

        WritingJsonController.createJsonFile(outputPath, outputJson);
    }

    public static void main(String[] args) {
        try {
            AikamTaskMain aikamTask = new AikamTaskMain(args);
            aikamTask.run();
        } catch (NullPointerException e) {
            JSONObject errorObj = new JSONObject();
            
            errorObj.put("type", "error");
            errorObj.put("message", "Не введены аргументы программы." +
                    " Пример запуска  java -jar program.jar search input.json output.json ");

            WritingJsonController.createJsonFile("Output.json", errorObj);
        }
    }
}
