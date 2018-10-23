package best.anastasia.cinemanearby.db.dao;

import android.support.annotation.NonNull;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import best.anastasia.cinemanearby.concepts.Review;
import best.anastasia.cinemanearby.db.contacts.ReviewContract;
import rx.Observable;

public class ReviewDao extends BaseDaoImpl<Review, Integer> {
    public ReviewDao(ConnectionSource connectionSource, Class<Review> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    @Override
    public int create(Review data) {
        try {
            return super.create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int delete(Collection<Review> data) {
        try {
            return super.delete(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void createList(List<Review> dataList, String placeId) {
        // Удаляем все старые отзывы для этого placeID
        delete(getReviews(placeId));
        // Добавляем к новым placeID
        Observable.from(dataList).forEach(r -> r.setPlaceId(placeId));
        // Вставляем новые
        Observable.from(dataList).forEach(this::create);
    }

    @NonNull
    public List<Review> getReviews(String placeId) {
        try {
            return queryForEq(ReviewContract.PLACE_ID, placeId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(0);
    }
}