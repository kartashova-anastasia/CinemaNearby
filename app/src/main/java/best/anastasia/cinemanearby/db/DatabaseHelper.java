package best.anastasia.cinemanearby.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import best.anastasia.cinemanearby.concepts.Cinema;
import best.anastasia.cinemanearby.concepts.Favorite;
import best.anastasia.cinemanearby.concepts.Period;
import best.anastasia.cinemanearby.concepts.Photo;
import best.anastasia.cinemanearby.concepts.Review;
import best.anastasia.cinemanearby.db.dao.CinemaDao;
import best.anastasia.cinemanearby.db.dao.FavoriteDao;
import best.anastasia.cinemanearby.db.dao.PeriodDao;
import best.anastasia.cinemanearby.db.dao.PhotoDao;
import best.anastasia.cinemanearby.db.dao.ReviewDao;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private CinemaDao cinemaDao;
    private FavoriteDao favoriteDao;
    private PhotoDao photoDao;
    private ReviewDao reviewDao;
    private PeriodDao periodDao;

    public DatabaseHelper(Context context) {
        super(context, DatabaseContract.DB_NAME, null, DatabaseContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Cinema.class);
            TableUtils.createTable(connectionSource, Favorite.class);
            TableUtils.createTable(connectionSource, Photo.class);
            TableUtils.createTable(connectionSource, Review.class);
            TableUtils.createTable(connectionSource, Period.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Cinema.class, true);
            TableUtils.dropTable(connectionSource, Favorite.class, true);
            TableUtils.dropTable(connectionSource, Photo.class, true);
            TableUtils.dropTable(connectionSource, Review.class, true);
            TableUtils.dropTable(connectionSource, Period.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public CinemaDao getCinemaDao() {
        if (cinemaDao == null) {
            try {
                cinemaDao = new CinemaDao(connectionSource, Cinema.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return cinemaDao;
    }

    public FavoriteDao getFavoriteDao() {
        if (favoriteDao == null) {
            try {
                favoriteDao = new FavoriteDao(connectionSource, Favorite.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return favoriteDao;
    }

    public PhotoDao getPhotoDao() {
        if (photoDao == null) {
            try {
                photoDao = new PhotoDao(connectionSource, Photo.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return photoDao;
    }

    public ReviewDao getReviewDao() {
        if (reviewDao == null) {
            try {
                reviewDao = new ReviewDao(connectionSource, Review.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return reviewDao;
    }


    public PeriodDao getPeriodDao() {
        if (periodDao == null) {
            try {
                periodDao = new PeriodDao(connectionSource, Period.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return periodDao;
    }
}