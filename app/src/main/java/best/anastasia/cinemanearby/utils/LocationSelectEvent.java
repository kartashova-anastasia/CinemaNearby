package best.anastasia.cinemanearby.utils;

import com.google.android.gms.maps.model.LatLng;

import best.anastasia.cinemanearby.concepts.GeoAddress;

public class LocationSelectEvent {
    public LatLng latLng;
    public GeoAddress address;

    public LocationSelectEvent(LatLng location, GeoAddress address) {
        this.latLng = location;
        this.address = address;
    }
}