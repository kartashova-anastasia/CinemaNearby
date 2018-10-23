package best.anastasia.cinemanearby.di;

import javax.inject.Singleton;

import best.anastasia.cinemanearby.CinemaApp;
import best.anastasia.cinemanearby.concepts.Cinema;
import best.anastasia.cinemanearby.mvp.CinemaListPresenter;
import best.anastasia.cinemanearby.mvp.CinemaPresenter;
import best.anastasia.cinemanearby.mvp.MainPresenter;
import best.anastasia.cinemanearby.retrofit.CinemaListDeserializer;
import best.anastasia.cinemanearby.di.ApiModule;
import best.anastasia.cinemanearby.di.AppModule;
import best.anastasia.cinemanearby.di.DbModule;
import best.anastasia.cinemanearby.di.UtilsModule;

import dagger.Component;

@Component(modules = {AppModule.class, ApiModule.class, DbModule.class, UtilsModule.class})
@Singleton
public interface AppComponent {
    void inject(Cinema model);



    void inject(CinemaPresenter presenter);


    void inject(CinemaListPresenter presenter);

    void inject(MainPresenter presenter);

    void inject(CinemaListDeserializer module);


    void inject(CinemaApp app);


}
