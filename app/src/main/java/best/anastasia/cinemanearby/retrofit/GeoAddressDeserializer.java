package best.anastasia.cinemanearby.retrofit;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import best.anastasia.cinemanearby.concepts.GeoAddress;

public class GeoAddressDeserializer implements JsonDeserializer<GeoAddress> {
    @Override
    public GeoAddress deserialize(JsonElement json, Type typeOfT,
                                  JsonDeserializationContext context) throws JsonParseException {
        final GeoAddress address = new GeoAddress();
        if (!json.getAsJsonObject().get("status").getAsString().equals("OK")) {
            return null;
        }
        JsonObject addressListObj = json.getAsJsonObject();
        JsonArray addressArr = addressListObj.get("results").getAsJsonArray();
        JsonObject addressObj = addressArr.get(0).getAsJsonObject();
        if (addressObj == null) {
            return null;
        }
        address.setHouse(addressObj.get("address_components").getAsJsonArray().get(0)
                .getAsJsonObject().get("short_name").getAsString());
        address.setStreet(addressObj.get("address_components").getAsJsonArray().get(1)
                .getAsJsonObject().get("short_name").getAsString());
        address.setCity(addressObj.get("address_components").getAsJsonArray().get(2)
                .getAsJsonObject().get("short_name").getAsString());

        return address;
    }
}