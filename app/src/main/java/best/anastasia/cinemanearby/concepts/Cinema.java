package best.anastasia.cinemanearby.concepts;

import android.location.Location;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Calendar;
import java.util.List;
import java.util.Date;

import javax.inject.Inject;

import best.anastasia.cinemanearby.CinemaApp;
import best.anastasia.cinemanearby.db.contacts.CinemaContract;
import best.anastasia.cinemanearby.db.dao.FavoriteDao;
import best.anastasia.cinemanearby.db.dao.PeriodDao;
import best.anastasia.cinemanearby.db.dao.PhotoDao;
import best.anastasia.cinemanearby.db.dao.ReviewDao;

@DatabaseTable(tableName = CinemaContract.TABLE_NAME)
public class Cinema {
    @DatabaseField(generatedId = true, columnName = CinemaContract.ID)
    private int id;
    @DatabaseField(columnName = CinemaContract.ADDRESS)
    private String address;
    @DatabaseField(columnName = CinemaContract.PHONE)
    private String phone;
    @DatabaseField(columnName = CinemaContract.LATITUDE)
    private Double latitude;
    @DatabaseField(columnName = CinemaContract.LONGITUDE)
    private Double longitude;
    @DatabaseField(columnName = CinemaContract.NAME)
    private String name;
    @DatabaseField(columnName = CinemaContract.OPEN_NOW)
    private boolean openNow;
    @DatabaseField(columnName = CinemaContract.PLACE_ID)
    private String placeId;
    @DatabaseField(columnName = CinemaContract.SCOPE)
    private String scope;
    @DatabaseField(columnName = CinemaContract.URL)
    private String url;
    @DatabaseField(columnName = CinemaContract.WEBSITE)
    private String website;
    @DatabaseField(columnName = CinemaContract.RATING)
    private Double rating;
    // Данные для серилизации при отправке запроса на добавление кинотеатра
    private int accuracy;
    private String language;
    // DAO для загрузки данных из сторонних таблиц
    @Inject protected transient PhotoDao photoDao;
    @Inject protected transient ReviewDao reviewDao;
    @Inject protected transient FavoriteDao favoriteDao;
    @Inject protected transient PeriodDao periodDao;

    public Cinema() {
        CinemaApp.getComponent().inject(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean hasPhone() {
        return !TextUtils.isEmpty(phone);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOpenNow() {
        return openNow;
    }

    public void setOpenNow(boolean openNow) {
        this.openNow = openNow;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean hasWebsite() {
        return !TextUtils.isEmpty(website);
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Location getLocation() {
        final Location location = new Location("empty");
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        return location;
    }

    public void setFavorite(boolean value) {
        Favorite favorite = favoriteDao.getFavorite(placeId);
        favorite = favorite == null ? new Favorite(placeId, value) : favorite;
        favorite.setFavorite(value);
        favoriteDao.createOrUpdate(favorite);
    }

    public boolean isFavorite() {
        return favoriteDao.isFavorite(placeId);
    }

    public List<Photo> getPhotos() {
        return photoDao.getPhotos(placeId);
    }

    public void setPhotos(List<Photo> photos) {
        photoDao.createList(photos, placeId);
    }

    public List<Review> getReviews() {
        return reviewDao.getReviews(placeId);
    }

    public void setReviews(List<Review> reviews) {
        reviewDao.createList(reviews, placeId);
    }

    public List<Period> getPeriods() {
        return periodDao.getPeriods(placeId);
    }

    @Nullable
    public String getWorkTime() {
        final List<Period> periods = getPeriods();
        String openTime = null, closeTime = null;
        if (periods != null && periods.size() > 0) {
            final Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            final int day = c.get(Calendar.DAY_OF_WEEK);
            for (Period period : periods) {
                // Находим время открытия сегодня
                if (!period.isClose() && period.getDay() == day) {
                    openTime = period.getTime();
                } else if (period.isClose() && period.getDay() == day
                        || period.getDay() == day + 1) {
                    closeTime = period.getTime();
                }
            }
            if (openTime != null && closeTime != null) {
                return openTime.substring(0, 2) + ":" + openTime.substring(2, 4)
                        + " - " + closeTime.substring(0, 2) + ":" + closeTime.substring(2, 4);
            }
        }
        return null;
    }

    public void setPeriods(List<Period> periods) {
        periodDao.createList(periods, placeId);
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}