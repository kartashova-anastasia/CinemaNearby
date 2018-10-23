package best.anastasia.cinemanearby.mvp;

import android.app.Activity;
import android.location.Location;

import com.arellomobile.mvp.InjectViewState;
import com.google.android.gms.common.api.Status;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import best.anastasia.cinemanearby.CinemaApp;
import best.anastasia.cinemanearby.concepts.CinemaListResponse;
import best.anastasia.cinemanearby.db.dao.CinemaDao;
import best.anastasia.cinemanearby.retrofit.GooglePlacesService;
import best.anastasia.cinemanearby.utils.LocationUpdateEvent;
import best.anastasia.cinemanearby.utils.Utils;
import best.anastasia.cinemanearby.utils.LocationManager;
import best.anastasia.cinemanearby.utils.LocationListener;
import best.anastasia.cinemanearby.views.MainView;
import rx.Subscriber;
import rx.Subscription;

@InjectViewState
public class MainPresenter extends BasePresenter<MainView> implements LocationListener {
    @Inject
    protected LocationManager locationManager;
    private Location location;
    private String pageToken;
    private String apiKey;

    @Inject protected GooglePlacesService service;
    @Inject protected CinemaDao cinemaDao;

    public MainPresenter(String apiKey) {
        this.apiKey = apiKey;
        CinemaApp.getComponent().inject(this);
    }

    public void attachActivity(Activity activity) {
        locationManager.attachActivity(activity);
        locationManager.setListener(this);
    }

    @Override
    public void detachView(MainView view) {
        super.detachView(view);
        locationManager.detachActivity();
    }

    private String getLocationStr(Location location) {
        return location.getLatitude() + "," + location.getLongitude();
    }

    private void loadCinemaList() {
        getViewState().showRefreshing(true);
        Subscription subscription = service.nearBySearch(apiKey, getLocationStr(location), "ru", pageToken)
                .compose(Utils.applySchedulers())
                .subscribe(new Subscriber<CinemaListResponse>() {
                    @Override
                    public void onCompleted() {
                        getViewState().showRefreshing(false);
                        getViewState().showCinemaList();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        getViewState().showRefreshing(false);
                        getViewState().showCinemaList();
                    }

                    @Override
                    public void onNext(CinemaListResponse response) {
                        if (pageToken == null) {
                            // Данные загружаются заново/в первый раз
                            cinemaDao.removeList();
                        }
                        pageToken = response.getToken();
                        cinemaDao.createList(response.getCinemaList());
                    }
                });
        unsubscribeOnDestroy(subscription);
    }

    public void onPermissionAgreed() {
        locationManager.startTracking();
    }

    public void onPermissionDenied() {
        getViewState().showPermissionDenied();
    }

    public void onLocationAvailable() {
        locationManager.startTracking();
    }

    public void onRefresh() {
        pageToken = null;
        loadCinemaList();
    }

    public void onEndScroll() {
        if (pageToken != null) {
            loadCinemaList();
        }
    }

    @Override
    public void onRequestLocation(Status status) {
        getViewState().requestLocation(status);
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null && this.location == null) {
            // Если локации на данный момент нет - берём любую
            EventBus.getDefault().post(new LocationUpdateEvent(location));
            this.location = location;
            loadCinemaList();
        } else if (this.location != null && location != null
                && this.location.getAccuracy() < location.getAccuracy()) {
            // Если локация уже есть, то берём новую только, если у неё выше точность
            EventBus.getDefault().post(new LocationUpdateEvent(location));
            this.location = location;
            loadCinemaList();
        }
    }
}