package best.anastasia.cinemanearby.mvp;

import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;

import best.anastasia.cinemanearby.CinemaApp;
import best.anastasia.cinemanearby.concepts.Cinema;
import best.anastasia.cinemanearby.db.dao.CinemaDao;
import best.anastasia.cinemanearby.retrofit.GooglePlacesService;
import best.anastasia.cinemanearby.utils.EventManager;
import best.anastasia.cinemanearby.utils.Utils;
import best.anastasia.cinemanearby.views.CinemaView;
import rx.Subscriber;

@InjectViewState
public class CinemaPresenter extends BasePresenter<CinemaView> {
    private String apiKey;
    private String placeId;
    @Inject
    protected GooglePlacesService service;
    @Inject protected CinemaDao cinemaDao;
    private Cinema cinema;

    public CinemaPresenter(String apiKey, String placeId) {
        this.apiKey = apiKey;
        this.placeId = placeId;
        CinemaApp.getComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        cinema = cinemaDao.getCinema(placeId);
        displayCinemaInfo();
        loadCinemaInfo();
    }

    private void displayCinemaInfo() {
        getViewState().showRefreshing(false);
        getViewState().showTitle(cinema.getName());
        getViewState().showAddress(cinema.getAddress());
        getViewState().showPhotos(cinema.getPhotos());
        // TODO
        getViewState().showDeleteButton(cinema.getScope().equals("APP"));
    }

    private void loadCinemaInfo() {
        getViewState().showRefreshing(true);
        service.details(apiKey, placeId, "ru")
                .compose(Utils.applySchedulers())
                .subscribe(new Subscriber<Cinema>() {
                    @Override
                    public void onCompleted() {
                        displayCinemaInfo();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        getViewState().showRefreshing(false);
                        getViewState().showErrorMessage(e.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(Cinema cinema) {
                        cinema.setId(CinemaPresenter.this.cinema.getId());
                        CinemaPresenter.this.cinema = cinema;
                        cinemaDao.createOrUpdate(CinemaPresenter.this.cinema);
                    }
                });
    }



    /*public void onFavoriteClicked() {
        boolean favorite = !cinema.isFavorite();
        cinema.setFavorite(favorite);
        getViewState().showFavorite(favorite);
        EventManager.sendCinemaUpdate();
    }*/



    public void onRefresh() {
        loadCinemaInfo();
    }


}