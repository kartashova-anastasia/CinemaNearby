package best.anastasia.cinemanearby.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import best.anastasia.cinemanearby.concepts.Review;
import best.anastasia.cinemanearby.concepts.Photo;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface CinemaView extends MvpView {
    void showRefreshing(boolean refreshing);

    void showTitle(String title);

    void showAddress(String address);


    void showPhotos(List<Photo> photos);


    void showErrorMessage(String text);

    void showDeleteButton(boolean show);

    void closeActivity();
}