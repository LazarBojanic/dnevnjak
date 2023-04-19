package rs.raf.dnevnjak.serialize;

import com.google.gson.Gson;

public class GsonSerializer {
    private static final Gson gson = new Gson();

    public static String serialize(Object object) {
        return gson.toJson(object);
    }


    public static <T> T deserialize(String string, Class<T> clazz) {
        return gson.fromJson(string, clazz);
    }
}
