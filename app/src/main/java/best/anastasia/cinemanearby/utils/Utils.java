package best.anastasia.cinemanearby.utils;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Utils {
    public static <T> Observable.Transformer<T, T> applySchedulers() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Функция получения расстояния между точками
     * @param point1 координаты точки 1
     * @param point2 координаты точки 2
     * @return расстояние в километрах
     */
    public static double distanceBetweenPoints(Location point1, Location point2) {
        double theta = point1.getLongitude() - point2.getLongitude();
        double dist = Math.sin(Math.toRadians(point1.getLatitude()))
                * Math.sin(Math.toRadians(point2.getLatitude()))
                + Math.cos(Math.toRadians(point1.getLatitude()))
                * Math.cos(Math.toRadians(point2.getLatitude()))
                * Math.cos(Math.toRadians(theta));
        dist = Math.acos(dist);
        dist = Math.toDegrees(dist);
        dist = dist * 60 * 1.1515 * 1.609344;
        return dist;
    }

    public static String getLocationStr(double lat, double lon) {
        return lat + "," + lon;
    }

    public static String getLocationStr(Location location) {
        return getLocationStr(location.getLatitude(), location.getLongitude());
    }

    public static String getLocationStr(LatLng latLng) {
        return getLocationStr(latLng.latitude, latLng.longitude);
    }
}