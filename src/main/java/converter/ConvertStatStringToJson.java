package converter;

import com.github.tsohr.JSONObject;
import com.github.tsohr.JSONTokener;

public class ConvertStatStringToJson {
    public static JSONObject convert(String jsonText) {
        return new JSONObject(new JSONTokener(jsonText));
    }
}
