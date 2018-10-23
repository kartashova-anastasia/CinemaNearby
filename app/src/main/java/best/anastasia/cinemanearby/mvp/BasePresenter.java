package best.anastasia.cinemanearby.mvp;

import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class BasePresenter<V extends MvpView> extends MvpPresenter<V> {
    private CompositeSubscription subscription = new CompositeSubscription();

    protected void unsubscribeOnDestroy(Subscription subscription) {
        this.subscription.add(subscription);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        subscription.clear();
    }
}