package best.anastasia.cinemanearby.retrofit;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import best.anastasia.cinemanearby.concepts.Cinema;

public class CinemaSingleDeserializer implements JsonDeserializer<Cinema> {
    private CinemaDeserializer deserializer;

    public CinemaSingleDeserializer(CinemaDeserializer deserializer) {
        this.deserializer = deserializer;
    }

    @Override
    public Cinema deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject cinemaObj = json.getAsJsonObject().get("result").getAsJsonObject();
        return deserializer.deserialize(cinemaObj);
    }
}