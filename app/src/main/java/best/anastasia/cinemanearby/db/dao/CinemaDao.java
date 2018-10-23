package best.anastasia.cinemanearby.db.dao;

import android.support.annotation.Nullable;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;


import best.anastasia.cinemanearby.concepts.Cinema;
import best.anastasia.cinemanearby.db.contacts.CinemaContract;
import rx.Observable;

public class CinemaDao extends BaseDaoImpl<Cinema, Integer> {
    public CinemaDao(ConnectionSource connectionSource, Class<Cinema> dataClass)
            throws SQLException {
        super(connectionSource, dataClass);
    }

    /**
     * Возвращает полный список кинотеатров, включая добавленные пользователем
     */
    public List<Cinema> getAllCinemaList() {
        try {
            return queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Возвращает список кинотеатров от Google
     */
    public List<Cinema> getGlobalCinemaList() {
        try {
            return queryForEq(CinemaContract.SCOPE, "GOOGLE");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Возвращает кинотеатр по его Place ID
     * @param placeId place ID кинотеатра
     * @return объект с кинотеатром, если он есть в базе, null в ином случае
     */
    @Nullable
    public Cinema getCinema(String placeId) {
        try {
            return queryBuilder().where()
                    .eq(CinemaContract.PLACE_ID, placeId).queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int create(Cinema data) {
        try {
            return super.create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void createList(List<Cinema> dataList) {
        // Добавляем новые в БД
        Observable.from(dataList).forEach(this::create);
    }

    public void removeList() {
        // Удаляем из БД старые кинотеатры из Google
        delete(getAllCinemaList());
    }

    @Override
    public int update(Cinema data) {
        try {
            return super.update(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public CreateOrUpdateStatus createOrUpdate(Cinema data) {
        try {
            return super.createOrUpdate(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new CreateOrUpdateStatus(false, false, 0);
    }

    @Override
    public int delete(Cinema data) {
        try {
            return super.delete(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int delete(Collection<Cinema> data) {
        try {
            return super.delete(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
