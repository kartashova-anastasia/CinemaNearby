package best.anastasia.cinemanearby.retrofit;

import best.anastasia.cinemanearby.concepts.Cinema;
import best.anastasia.cinemanearby.concepts.CinemaListResponse;
import best.anastasia.cinemanearby.concepts.GeoAddress;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface GooglePlacesService {
    String BASE_URL = "https://maps.googleapis.com/";

    @GET("maps/api/place/nearbysearch/json?rankby=distance&types=movie_theater")
    Observable<CinemaListResponse> nearBySearch(@Query("key") String key,
                                                @Query("location") String location,
                                                @Query("language") String language,
                                                @Query("pagetoken") String pageToken);

    @GET("maps/api/place/details/json")
    Observable<Cinema> details(@Query("key") String key,
                               @Query("placeid") String placeId,
                               @Query("language") String language);


    @GET("maps/api/geocode/json")
    Observable<GeoAddress> geocode(@Query("latlng") String latLng,
                                   @Query("sensor") boolean sensor,
                                   @Query("language") String lang);
}