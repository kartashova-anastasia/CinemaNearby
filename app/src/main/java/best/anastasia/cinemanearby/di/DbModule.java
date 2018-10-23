package best.anastasia.cinemanearby.di;

import android.content.Context;

import javax.inject.Singleton;

import best.anastasia.cinemanearby.db.DatabaseHelper;
import best.anastasia.cinemanearby.db.dao.CinemaDao;
import best.anastasia.cinemanearby.db.dao.FavoriteDao;
import best.anastasia.cinemanearby.db.dao.PeriodDao;
import best.anastasia.cinemanearby.db.dao.PhotoDao;
import best.anastasia.cinemanearby.db.dao.ReviewDao;
import dagger.Module;
import dagger.Provides;

@Module
public class DbModule {
    @Provides
    @Singleton
    DatabaseHelper provideCinemaDatabaseHelper(Context context) {
        return new DatabaseHelper(context);
    }

    @Provides
    @Singleton
    CinemaDao provideCinemaDao(DatabaseHelper helper) {
        return helper.getCinemaDao();
    }

    @Provides
    @Singleton
    FavoriteDao provideFavoriteDao(DatabaseHelper helper) {
        return helper.getFavoriteDao();
    }

    @Provides
    @Singleton
    PhotoDao providePhotoDao(DatabaseHelper helper) {
        return helper.getPhotoDao();
    }

    @Provides
    @Singleton
    ReviewDao provideReviewDao(DatabaseHelper helper) {
        return helper.getReviewDao();
    }


    @Provides
    @Singleton
    PeriodDao providePeriodDao(DatabaseHelper helper) {
        return helper.getPeriodDao();
    }
}