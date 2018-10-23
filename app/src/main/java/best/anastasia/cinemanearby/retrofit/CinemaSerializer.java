package best.anastasia.cinemanearby.retrofit;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import best.anastasia.cinemanearby.concepts.Cinema;

public class CinemaSerializer implements JsonSerializer<Cinema> {
    @Override
    public JsonElement serialize(Cinema src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject rootObj = new JsonObject();

        JsonObject locationObj = new JsonObject();
        locationObj.addProperty("lat", src.getLocation().getLatitude());
        locationObj.addProperty("lng", src.getLocation().getLongitude());
        rootObj.add("location", locationObj);

        rootObj.addProperty("accuracy", src.getAccuracy());
        rootObj.addProperty("name", src.getName());
        rootObj.addProperty("phone_number", src.getPhone());
        rootObj.addProperty("address", src.getAddress());

        JsonArray typesArr = new JsonArray();
        typesArr.add("movie_theater");
        rootObj.add("types", typesArr);

        rootObj.addProperty("website", src.getWebsite());
        rootObj.addProperty("language", src.getLanguage() + "-" + src.getLanguage().toUpperCase());
        return rootObj;
    }
}
