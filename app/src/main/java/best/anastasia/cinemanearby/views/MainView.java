package best.anastasia.cinemanearby.views;

import android.location.Location;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.google.android.gms.common.api.Status;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface MainView extends MvpView {
    void showPermissionDenied();

    void requestLocation(Status status);

    void showCinemaList();

    void showRefreshing(boolean refresh);
}