package best.anastasia.cinemanearby.mvp;

import android.location.Location;
import android.text.TextUtils;

import com.arellomobile.mvp.InjectViewState;
import com.google.common.collect.Collections2;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;


import best.anastasia.cinemanearby.CinemaApp;
import best.anastasia.cinemanearby.concepts.Cinema;
import best.anastasia.cinemanearby.db.dao.CinemaDao;
import best.anastasia.cinemanearby.utils.LocationUpdateEvent;
import best.anastasia.cinemanearby.utils.Utils;
import best.anastasia.cinemanearby.views.CinemaListView;

@InjectViewState
public class CinemaListPresenter extends BasePresenter<CinemaListView> {
    protected static final int ORDER_BY_RATING = 0;
    protected static final int ORDER_BY_DISTANCE = 1;

    private int orderBy = ORDER_BY_DISTANCE;
    private String searchText;
    private List<Cinema> cinemaList;
    private boolean favoriteOnly;
    private boolean openNow;
    private Location curLocation;

    @Inject
    protected CinemaDao cinemaDao;

    public CinemaListPresenter(boolean favoriteOnly) {
        this.favoriteOnly = favoriteOnly;
        CinemaApp.getComponent().inject(this);
    }

  /*  @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().showClearButtonEnabled(false);
    }*/


    @Override
    public void detachView(CinemaListView view) {
        super.detachView(view);
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(LocationUpdateEvent e) {
        this.curLocation = e.location;
        getViewState().changeLocation(e.location);
    }

    private void loadCinemaList() {
        this.cinemaList = cinemaDao.getAllCinemaList();
        displayCinemaList();
    }

    public void onSortDistanceClicked() {
        orderBy = ORDER_BY_DISTANCE;
        displayCinemaList();
    }

    public void onSortRatingClicked() {
        orderBy = ORDER_BY_RATING;
        displayCinemaList();
    }

    public void onSettingsClicked() {
        getViewState().navigateToSettingsScreen();
    }


    private void displayCinemaList() {
        sortCinemaList(cinemaList);
        getViewState().showCinemas(filterCinemaList(cinemaList));
    }

    public void onDataLoaded() {
        loadCinemaList();
    }

    protected List<Cinema> filterCinemaList(List<Cinema> cinemaList) {
        List<Cinema> filteredCinemaList = new ArrayList<>(cinemaList);
        // Фильтр по избранному
        if (favoriteOnly) {
            filteredCinemaList = new ArrayList<>(Collections2.filter(filteredCinemaList,
                    Cinema::isFavorite));
        }
        // Фильтр по имени
        filteredCinemaList = new ArrayList<>(Collections2.filter(filteredCinemaList,
                c -> TextUtils.isEmpty(searchText) || c != null &&
                        c.getName().toLowerCase().startsWith(searchText.toLowerCase())));
        // Фильтр "открыто сейчас"
        filteredCinemaList = new ArrayList<>(Collections2.filter(filteredCinemaList,
                c -> !openNow || c != null && c.isOpenNow()));
        return filteredCinemaList;
    }

    private void sortCinemaList(List<Cinema> cinemaList) {
        if (orderBy == ORDER_BY_DISTANCE) {
            Collections.sort(cinemaList, (c1, c2) -> Utils.distanceBetweenPoints(
                    c1.getLocation(), curLocation)
                    - Utils.distanceBetweenPoints(c2.getLocation(), curLocation) >= 0 ? 1 : 0);
        } else {
            Collections.sort(cinemaList, (c1, c2) -> c1.getRating() >= c2.getRating() ? 1 : 0);
        }
    }

    public void onCinemaClicked(int position) {
        getViewState().openCinemaDetails(cinemaList.get(position).getPlaceId());
        cinemaList.get(position).getPeriods();
    }

    public void onOpenNowClicked(boolean isChecked) {
        openNow = isChecked;
        displayCinemaList();
    }
}