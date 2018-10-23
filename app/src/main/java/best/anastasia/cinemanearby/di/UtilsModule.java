package best.anastasia.cinemanearby.di;

import dagger.Module;
import dagger.Provides;
import best.anastasia.cinemanearby.utils.LocationManager;

@Module
public class UtilsModule {
    @Provides
    LocationManager provideLocationManager() {
        return new LocationManager();
    }
}