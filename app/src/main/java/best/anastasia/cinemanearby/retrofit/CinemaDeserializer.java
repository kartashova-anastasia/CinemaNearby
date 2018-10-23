package best.anastasia.cinemanearby.retrofit;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import best.anastasia.cinemanearby.concepts.Cinema;
import best.anastasia.cinemanearby.concepts.Period;
import best.anastasia.cinemanearby.concepts.Review;
import best.anastasia.cinemanearby.concepts.Photo;

public class CinemaDeserializer {
    public Cinema deserialize(JsonElement json) {
        final Cinema model = new Cinema();
        JsonObject cinemaObj = json.getAsJsonObject();

        model.setAddress(stringFromJson(cinemaObj.get("vicinity")));
        model.setPhone(stringFromJson(cinemaObj.get("international_phone_number")));
        model.setLatitude(cinemaObj.get("geometry").getAsJsonObject().get("location").getAsJsonObject().get("lat").getAsDouble());
        model.setLongitude(cinemaObj.get("geometry").getAsJsonObject().get("location").getAsJsonObject().get("lng").getAsDouble());
        model.setName(stringFromJson(cinemaObj.get("name")));
        model.setOpenNow(getOpenNow(cinemaObj.get("opening_hours")));
        model.setPlaceId(stringFromJson(cinemaObj.get("place_id")));
        model.setScope(stringFromJson(cinemaObj.get("scope")));
        model.setUrl(stringFromJson(cinemaObj.get("url")));
        model.setWebsite(stringFromJson(cinemaObj.get("website")));
        model.setRating(doubleFromJson(cinemaObj.get("rating")));

        model.setPeriods(getPeriodsList(cinemaObj.get("opening_hours")));
        model.setPhotos(getPhotosList(cinemaObj.get("photos")));
        model.setReviews(getReviewsList(cinemaObj.getAsJsonArray("reviews")));
        return model;
    }

    private boolean getOpenNow(@Nullable JsonElement hoursElem) {
        if (hoursElem == null)
            return false;
        JsonElement openNowElem = hoursElem.getAsJsonObject().get("open_now");
        return openNowElem != null && openNowElem.getAsBoolean();
    }


    @NonNull
    private List<Period> getPeriodsList(@Nullable JsonElement hoursElem) {
        ArrayList<Period> periodsList = new ArrayList<>();
        if (hoursElem != null) {
            JsonElement periodsElem = hoursElem.getAsJsonObject().get("periods");
            if (periodsElem != null) {
                JsonArray periods = periodsElem.getAsJsonArray();
                for (int i = 0; i < periods.size(); i++) {
                    JsonObject periodObj = periods.get(i).getAsJsonObject();
                    Period open = new Period();
                    open.setClose(false);
                    open.setDay(periodObj.get("close").getAsJsonObject().get("day").getAsInt());
                    open.setTime(periodObj.get("close").getAsJsonObject().get("time").getAsString());
                    periodsList.add(open);

                    Period close = new Period();
                    close.setClose(true);
                    open.setDay(periodObj.get("open").getAsJsonObject().get("day").getAsInt());
                    open.setTime(periodObj.get("open").getAsJsonObject().get("time").getAsString());
                    periodsList.add(open);
                }
            }
        }
        return periodsList;
    }

    @Nullable
    private String stringFromJson(@Nullable JsonElement stringElem) {
        if (stringElem == null)
            return null;
        return stringElem.getAsString();
    }

    @Nullable
    private Double doubleFromJson(@Nullable JsonElement doubleObj) {
        if (doubleObj == null)
            return null;
        return doubleObj.getAsDouble();
    }

    @NonNull
    private List<Photo> getPhotosList(@Nullable JsonElement photosObj) {
        List<Photo> photosList = new ArrayList<>();
        if (photosObj != null) {
            JsonArray photosArr = photosObj.getAsJsonArray();
            for (int i = 0; i < photosArr.size(); i++) {
                Photo photo = new Photo();
                photo.setLink(photosArr.get(i).getAsJsonObject().get("photo_reference").getAsString());
                photosList.add(photo);
            }
        }
        return photosList;
    }

    @NonNull
    private List<Review> getReviewsList(@Nullable JsonElement reviewsObj) {
        List<Review> reviewsList = new ArrayList<>();
        if (reviewsObj != null) {
            JsonArray reviewsArr = reviewsObj.getAsJsonArray();
            for (int i = 0; i < reviewsArr.size(); i++) {
                reviewsList.add(getReviewFromJson(reviewsArr.get(i).getAsJsonObject()));
            }
        }
        return reviewsList;
    }

    @NonNull
    private Review getReviewFromJson(@NonNull JsonObject reviewObj) {
        final Review review = new Review();
        review.setAuthor(reviewObj.get("author_name").getAsString());
        review.setAuthorUrl(reviewObj.get("author_url").getAsString());
        review.setLanguage(reviewObj.get("language").getAsString());
        review.setProfilePhotoUrl(reviewObj.get("profile_photo_url").getAsString());
        review.setRating(reviewObj.get("rating").getAsInt());
        review.setTimeDesc(reviewObj.get("relative_time_description").getAsString());
        review.setText(reviewObj.get("text").getAsString());
        review.setTime(reviewObj.get("time").getAsInt());
        return review;
    }
}