package best.anastasia.cinemanearby.utils;

import android.location.Location;

public class LocationUpdateEvent {
    public Location location;

    public LocationUpdateEvent(Location location) {
        this.location = location;
    }
}