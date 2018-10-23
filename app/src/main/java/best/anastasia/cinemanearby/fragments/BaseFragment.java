package best.anastasia.cinemanearby.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;

import butterknife.ButterKnife;

public abstract class BaseFragment extends MvpAppCompatFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View contentView = inflater.inflate(getLayout(), container, false);
        ButterKnife.bind(this, contentView);
        return contentView;
    }

    public abstract void onDataLoaded();

    public abstract void showRefreshing(boolean refresh);

    public abstract int getLayout();
}