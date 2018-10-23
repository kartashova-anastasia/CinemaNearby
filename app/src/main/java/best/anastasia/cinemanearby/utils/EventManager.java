package best.anastasia.cinemanearby.utils;

import com.google.android.gms.maps.model.LatLng;

import org.greenrobot.eventbus.EventBus;

import best.anastasia.cinemanearby.concepts.GeoAddress;

public class EventManager {
    public static void sendCinemaUpdate() {
        EventBus.getDefault().postSticky(new CinemaUpdateEvent());
    }

    public static void sendLocationUpdate(LatLng latLng, GeoAddress address) {
        EventBus.getDefault().postSticky(new LocationSelectEvent(latLng, address));
    }
}