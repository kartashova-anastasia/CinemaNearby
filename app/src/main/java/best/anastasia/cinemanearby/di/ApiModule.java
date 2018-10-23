package best.anastasia.cinemanearby.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import best.anastasia.cinemanearby.concepts.Cinema;
import best.anastasia.cinemanearby.concepts.CinemaListResponse;
import best.anastasia.cinemanearby.concepts.GeoAddress;
import best.anastasia.cinemanearby.retrofit.CinemaDeserializer;
import best.anastasia.cinemanearby.retrofit.CinemaListDeserializer;
import best.anastasia.cinemanearby.retrofit.CinemaSerializer;
import best.anastasia.cinemanearby.retrofit.CinemaSingleDeserializer;
import best.anastasia.cinemanearby.retrofit.GeoAddressDeserializer;
import best.anastasia.cinemanearby.retrofit.GooglePlacesService;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {
    @Provides
    CinemaDeserializer provideCinemaDeserializer() {
        return new CinemaDeserializer();
    }

    @Provides
    CinemaListDeserializer provideCinemaListDeserializer(CinemaDeserializer deserializer) {
        return new CinemaListDeserializer(deserializer);
    }

    @Provides
    CinemaSingleDeserializer provideCinemaSingleDeserializer(CinemaDeserializer deserializer) {
        return new CinemaSingleDeserializer(deserializer);
    }

    @Provides
    GeoAddressDeserializer provideGeoAddressDeserializer() {
        return new GeoAddressDeserializer();
    }

    @Provides
    CinemaSerializer provideCinemaRequestSerializer() {
        return new CinemaSerializer();
    }

    @Provides
    @Singleton
    GooglePlacesService provideGooglePlacesService(CinemaListDeserializer listDeserializer,
                                                   CinemaSingleDeserializer cinemaDeserializer,
                                                   GeoAddressDeserializer addressDeserializer,
                                                   CinemaSerializer requestSerializer) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(CinemaListResponse.class, listDeserializer)
                .registerTypeAdapter(Cinema.class, cinemaDeserializer)
                .registerTypeAdapter(GeoAddress.class, addressDeserializer)
                .registerTypeAdapter(Cinema.class, requestSerializer)
                .create();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.addInterceptor(loggingInterceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GooglePlacesService.BASE_URL)
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit.create(GooglePlacesService.class);
    }
}