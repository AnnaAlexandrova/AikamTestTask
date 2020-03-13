package converter;

import com.github.tsohr.JSONObject;
import com.github.tsohr.JSONArray;
import com.github.tsohr.JSONTokener;

public class ConverterSearchStringToJson {
    public static JSONArray convert(String jsonText) {
        JSONObject jsonObject = new JSONObject(new JSONTokener(jsonText));

        return jsonObject.getJSONArray("criteria");
    }
}
