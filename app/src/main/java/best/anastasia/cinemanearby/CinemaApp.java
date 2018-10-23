package best.anastasia.cinemanearby;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;

import best.anastasia.cinemanearby.di.AppModule;
import best.anastasia.cinemanearby.di.AppComponent;
import best.anastasia.cinemanearby.di.DaggerAppComponent;
import io.fabric.sdk.android.Fabric;



public class CinemaApp extends Application {
    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        setupTheme();
        setupCrashlytics();
        setupAppComponent();
    }

    private void setupTheme() {
        // TODO:
    }

    private void setupCrashlytics() {
        // Отключем Crashlytics для Debug-сборок
        CrashlyticsCore core = new CrashlyticsCore.Builder()
                .disabled(BuildConfig.DEBUG).build();
        Crashlytics crashlytics = new Crashlytics.Builder()
                .core(core)
                .build();
        Fabric.with(this, crashlytics);
    }

    private void setupAppComponent() {
       component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        component.inject(this);
    }

    public static AppComponent getComponent() {
        return component;
    }
}