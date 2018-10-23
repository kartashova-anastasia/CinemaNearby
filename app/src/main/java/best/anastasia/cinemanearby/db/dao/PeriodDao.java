package best.anastasia.cinemanearby.db.dao;

import android.support.annotation.NonNull;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.sql.SQLException;

import best.anastasia.cinemanearby.concepts.Period;
import best.anastasia.cinemanearby.db.contacts.PeriodContract;
import rx.Observable;

public class PeriodDao extends BaseDaoImpl<Period, Integer> {
    public PeriodDao(ConnectionSource connectionSource, Class<Period> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    @Override
    public int create(Period data) {
        try {
            return super.create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int delete(Collection<Period> data) {
        try {
            return super.delete(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void createList(List<Period> dataList, String placeId) {
        // Удаляем все старые часы для этого placeID
        delete(getPeriods(placeId));
        // Добавляем к новым placeID
        Observable.from(dataList).forEach(p -> p.setPlaceId(placeId));
        // Вставляем новые
        Observable.from(dataList).forEach(this::create);
    }

    @NonNull
    public List<Period> getPeriods(String placeId) {
        try {
            return queryForEq(PeriodContract.PLACE_ID, placeId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(0);
    }
}