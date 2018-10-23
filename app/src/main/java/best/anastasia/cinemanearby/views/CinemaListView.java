package best.anastasia.cinemanearby.views;

import android.location.Location;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import best.anastasia.cinemanearby.concepts.Cinema;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface CinemaListView extends MvpView {
    void showNoNetwork();

    void showPermissionDenied();

    void showNoCinemas();

    void showCinemas(List<Cinema> cinemas);

    void navigateToSettingsScreen();

    void openCinemaDetails(String placeId);

    void changeLocation(Location location);
}