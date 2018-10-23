package best.anastasia.cinemanearby.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.List;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.OnClick;

import best.anastasia.cinemanearby.R;
import best.anastasia.cinemanearby.concepts.Review;
import best.anastasia.cinemanearby.concepts.Photo;
import best.anastasia.cinemanearby.fragments.NestedListView;
import best.anastasia.cinemanearby.fragments.PhotoViewPagerAdapter;
import best.anastasia.cinemanearby.mvp.CinemaPresenter;
import best.anastasia.cinemanearby.utils.UiUtils;
import best.anastasia.cinemanearby.views.CinemaView;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.OnClick;

public class CinemaActivity extends BaseActivity implements CinemaView,
        SwipeRefreshLayout.OnRefreshListener {
    public static final String PLACE_ID_EXTRA = "place_id_extra";


    private PhotoViewPagerAdapter photosAdapter;
    //private TextArrayAdapter openHoursAdapter;
    private boolean showDelete;

    @BindView(R.id.collapsingToolbar) protected CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.refreshLayout) protected SwipeRefreshLayout refreshLayout;
    @BindView(R.id.circleIndicator) protected CirclePageIndicator indicator;

    @BindView(R.id.lvWorkTime) protected NestedListView lvWorkTime;
    @BindView(R.id.viewpager) protected ViewPager photosViewPager;
    @BindView(R.id.tvAddress) protected TextView tvAddress;

    @BindView(R.id.toolbar) protected Toolbar toolbar;


    @BindDrawable(R.drawable.ic_heart_outline) protected Drawable add;
    @BindDrawable(R.drawable.ic_heart) protected Drawable remove;

    @InjectPresenter
    CinemaPresenter presenter;

    @ProvidePresenter
    public CinemaPresenter provideCinemaDetailsPresenter() {
        return new CinemaPresenter(
                getString(R.string.google_places_key),
                getIntent().getStringExtra(PLACE_ID_EXTRA));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(toolbar);
        refreshLayout.setOnRefreshListener(this);
        setupOpenHoursListView();
        setupPhotosViewPager();
        UiUtils.setToolbarHomeEnabled(this, true);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                return true;
            }

            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    private void setupPhotosViewPager() {
        photosAdapter = new PhotoViewPagerAdapter(getSupportFragmentManager(), null);
        photosViewPager.setAdapter(photosAdapter);
        indicator.setViewPager(photosViewPager);
        indicator.setSnap(true);
    }

    private void setupOpenHoursListView() {
     //   openHoursAdapter = new TextArrayAdapter(getString(R.string.no_data), this, R.layout.item_text);
     //   lvWorkTime.setAdapter(openHoursAdapter);
    }

    @Override
    public void showRefreshing(boolean refreshing) {
        refreshLayout.setRefreshing(refreshing);
    }

    @Override
    public void showTitle(@NonNull String title) {
        collapsingToolbar.setTitle(title);
    }

    @Override
    public void showAddress(@NonNull String address) {
        tvAddress.setText(address);
    }


    @Override
    public void showErrorMessage(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDeleteButton(boolean show) {
        this.showDelete = show;
        invalidateOptionsMenu();
    }


    @Override
    public void onRefresh() {
        presenter.onRefresh();
    }

    @Override
    public void showPhotos(List<Photo> photos) {
        photosAdapter.changeData(photos);
    }

    @Override
    public void closeActivity() {
        finish();
    }



    @Override
    protected int getLayoutId() {
        return R.layout.activity_cinema;
    }

    public static Intent getStartIntent(Context context, String placeId) {
        final Intent intent = new Intent(context, CinemaActivity.class);
        intent.putExtra(PLACE_ID_EXTRA, placeId);
        return intent;
    }
}