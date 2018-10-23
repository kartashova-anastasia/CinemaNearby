package best.anastasia.cinemanearby.retrofit;

import android.support.annotation.Nullable;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import best.anastasia.cinemanearby.concepts.Cinema;
import best.anastasia.cinemanearby.concepts.CinemaListResponse;

public class CinemaListDeserializer implements JsonDeserializer<CinemaListResponse> {
    private CinemaDeserializer deserializer;

    public CinemaListDeserializer(CinemaDeserializer cinemaDeserializer) {
        this.deserializer = cinemaDeserializer;
    }

    @Override
    public CinemaListResponse deserialize(JsonElement json, Type typeOfT,
                                          JsonDeserializationContext context)
            throws JsonParseException {

        final CinemaListResponse response = new CinemaListResponse();
        final List<Cinema> cinemasList = new ArrayList<>();
        final JsonObject responseObj = json.getAsJsonObject();
        final JsonArray cinemasArr = responseObj.get("results").getAsJsonArray();
        for (int i = 0; i < cinemasArr.size(); i++) {
            cinemasList.add(deserializer.deserialize(cinemasArr.get(i)));
        }
        response.setCinemaList(cinemasList);
        response.setToken(stringFromJson(responseObj.get("next_page_token")));
        return response;
    }

    @Nullable
    private String stringFromJson(@Nullable JsonElement stringElem) {
        if (stringElem == null)
            return null;
        return stringElem.getAsString();
    }
}