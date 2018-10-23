package best.anastasia.cinemanearby.utils;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

public class LocationManager implements GoogleApiClient.ConnectionCallbacks,
        com.google.android.gms.location.LocationListener {
    private GoogleApiClient googleApiClient;
    private LocationListener listener;

    public void attachActivity(Activity activity) {
        setupGoogleApi(activity);
    }

    public void detachActivity() {
        googleApiClient = null;
    }

    private void setupGoogleApi(Activity activity) {
        googleApiClient = new GoogleApiClient
                .Builder(activity)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .build();

        googleApiClient.connect();
    }

    @SuppressWarnings("MissingPermission")
    public void startTracking() {
        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (lastLocation != null) {
            onLocationChanged(lastLocation);
        } else {
            checkLocationStatus();
        }
    }

    @SuppressWarnings("MissingPermission")
    private void checkLocationStatus() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        PendingResult<LocationSettingsResult> settingsRequest =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());

        settingsRequest.setResultCallback(result -> {
            final Status status = result.getStatus();
            if (status.getStatusCode() == LocationSettingsStatusCodes.SUCCESS) {
                // Локация включена, запрашиваем обновления
                subscribeToLocation();
            } else if (status.getStatusCode() == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
                // Запрашиваем включение локации
                listener.onRequestLocation(status);
            } else {
                // Локация выключена и нет возможности запроса
                listener.onPermissionDenied();
            }
        });
    }

    @SuppressWarnings("MissingPermission")
    private void subscribeToLocation() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        LocationServices.FusedLocationApi
                .requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        listener.onLocationChanged(location);
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    public void setListener(LocationListener listener) {
        this.listener = listener;
    }
}