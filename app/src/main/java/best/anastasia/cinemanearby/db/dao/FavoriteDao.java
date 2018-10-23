package best.anastasia.cinemanearby.db.dao;


import android.support.annotation.Nullable;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;


import best.anastasia.cinemanearby.concepts.Favorite;
import best.anastasia.cinemanearby.db.contacts.FavoriteContract;

public class FavoriteDao extends BaseDaoImpl<Favorite, Integer> {
    public FavoriteDao(ConnectionSource connectionSource, Class<Favorite> dataClass)
            throws SQLException {
        super(connectionSource, dataClass);
    }

    @Override
    public int create(Favorite data) {
        try {
            return super.create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int update(Favorite data) {
        try {
            return super.update(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public CreateOrUpdateStatus createOrUpdate(Favorite data) {
        try {
            return super.createOrUpdate(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int delete(Favorite data) {
        try {
            return super.delete(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Nullable
    public Favorite getFavorite(String placeId) {
        try {
            QueryBuilder qb = queryBuilder();
            qb.where().eq(FavoriteContract.PLACE_ID, placeId);
            return (Favorite) qb.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isFavorite(String placeId) {
        try {
            QueryBuilder<Favorite, Integer> qb = queryBuilder();
            qb.where()
                    .eq(FavoriteContract.PLACE_ID, placeId)
                    .and()
                    .eq(FavoriteContract.FAVORITE, true);
            return qb.countOf() > 0L;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
