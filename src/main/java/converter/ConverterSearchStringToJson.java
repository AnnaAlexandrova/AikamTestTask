package converter;

import Main.AikamTaskMain;
import com.github.tsohr.JSONException;
import com.github.tsohr.JSONObject;
import com.github.tsohr.JSONArray;
import com.github.tsohr.JSONTokener;

public class ConverterSearchStringToJson {
    public static JSONArray convert(String jsonText) {
        try {
            JSONObject jsonObject = new JSONObject(new JSONTokener(jsonText));

            return jsonObject.getJSONArray("criteria");
        } catch (JSONException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
