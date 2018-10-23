package best.anastasia.cinemanearby.db.dao;

import android.support.annotation.NonNull;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import best.anastasia.cinemanearby.db.contacts.ReviewContract;
import best.anastasia.cinemanearby.concepts.Photo;
import best.anastasia.cinemanearby.concepts.Review;
import rx.Observable;

public class PhotoDao extends BaseDaoImpl<Photo, Integer> {
    public PhotoDao(ConnectionSource connectionSource, Class<Photo> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    @Override
    public int create(Photo data) {
        try {
            return super.create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int delete(Collection<Photo> data) {
        try {
            return super.delete(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void createList(List<Photo> dataList, String placeId) {
        // Удаляем все старые фотографии для этого placeID
        delete(getPhotos(placeId));
        // Добавляем к новым placeID
        Observable.from(dataList).forEach(p -> p.setPlaceId(placeId));
        // Вставляем новые
        Observable.from(dataList).forEach(this::create);
    }

    @NonNull
    public List<Photo> getPhotos(String placeId) {
        try {
            return queryForEq(ReviewContract.PLACE_ID, placeId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(0);
    }
}
