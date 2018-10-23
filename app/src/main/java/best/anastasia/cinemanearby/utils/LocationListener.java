package best.anastasia.cinemanearby.utils;

import android.location.Location;
import com.google.android.gms.common.api.Status;

public interface LocationListener {
    void onRequestLocation(Status status);

    void onLocationChanged(Location location);

    void onPermissionDenied();
}