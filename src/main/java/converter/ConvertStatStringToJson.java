package converter;

import com.github.tsohr.JSONException;
import com.github.tsohr.JSONObject;
import com.github.tsohr.JSONTokener;

public class ConvertStatStringToJson {
    public static JSONObject convert(String jsonText) {
        try {
            return new JSONObject(new JSONTokener(jsonText));
        } catch (JSONException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
