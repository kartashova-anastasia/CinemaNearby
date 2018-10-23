package best.anastasia.cinemanearby.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.google.android.gms.common.api.Status;

import best.anastasia.cinemanearby.R;

import best.anastasia.cinemanearby.utils.UiUtils;
import butterknife.BindView;
import butterknife.OnPageChange;



import best.anastasia.cinemanearby.fragments.CinemaListFragment;
import best.anastasia.cinemanearby.fragments.MainViewPagerAdapter;
import best.anastasia.cinemanearby.mvp.MainPresenter;
import best.anastasia.cinemanearby.views.MainView;
import best.anastasia.cinemanearby.mvp.MainPresenter;

public class MainActivity extends BaseActivity implements MainView {
    private static final String TAG_ALL_CINEMAS_FRAGMENT = "all_cinemas_fragment";
    private static final String TAG_FAV_CINEMAS_FRAGMENT = "fav_cinemas_fragment";
    private static final int LOCATION_SETTINGS_REQUEST_CODE = 0;

    @InjectPresenter
    MainPresenter presenter;
    private MainViewPagerAdapter adapter;


    @BindView(R.id.drawerLayout) protected DrawerLayout drawerLayout;
    @BindView(R.id.llNoPermission) protected View vNoPermission;
    @BindView(R.id.viewpager) protected ViewPager viewPager;
    @BindView(R.id.toolbar) protected Toolbar toolbar;
    private ActionBarDrawerToggle toggle;

    @ProvidePresenter
    MainPresenter provideMainPresenter() {
        return new MainPresenter(getString(R.string.google_places_key));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        UiUtils.setToolbarTitle(this, R.string.main_activity_title);
        UiUtils.setToolbarHomeEnabled(this, true);
        setupViewPager(savedInstanceState);
        setupDrawerLayout();
        presenter.attachActivity(this);
        //requestPermissions();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void requestLocation(Status status) {
        try {
            status.startResolutionForResult(this, LOCATION_SETTINGS_REQUEST_CODE);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
            presenter.onPermissionDenied();
        }
    }

    @Override
    public void showCinemaList() {
        vNoPermission.setVisibility(View.GONE);
        adapter.showCinemaList();
    }

    @Override
    public void showRefreshing(boolean refresh) {
        adapter.showRefreshing(refresh);
    }

    public void onRefresh() {
        presenter.onRefresh();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOCATION_SETTINGS_REQUEST_CODE && resultCode == RESULT_OK) {
            // Включили локацию
            presenter.onLocationAvailable();
        } else if (requestCode == LOCATION_SETTINGS_REQUEST_CODE) {
            presenter.onPermissionDenied();
        }
    }

    public void onEndScroll() {
        presenter.onEndScroll();
    }

    private void setupViewPager(Bundle savedInstanceState) {
        adapter = new MainViewPagerAdapter(getSupportFragmentManager());
        if (savedInstanceState != null) {
            adapter.addFragment(getSupportFragmentManager().getFragment(
                    savedInstanceState, TAG_ALL_CINEMAS_FRAGMENT), getString(R.string.main_all_tab));
            adapter.addFragment(getSupportFragmentManager().getFragment(
                    savedInstanceState, TAG_FAV_CINEMAS_FRAGMENT), getString(R.string.main_fav_tab));

        } else {
            adapter.addFragment(CinemaListFragment.newInstance(false), getString(R.string.main_all_tab));
            adapter.addFragment(CinemaListFragment.newInstance(true), getString(R.string.main_fav_tab));

        }
        viewPager.setAdapter(adapter);
       }

    @OnPageChange(R.id.viewpager)
    public void onPageChanged(int position) {
        UiUtils.setToolbarTitle(this, adapter.getTitle(position));
    }

    @SuppressWarnings("deprecation")
    protected void setupDrawerLayout() {
        drawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        //navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name);
        toggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(toggle);
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        switch (item.getItemId()) {
            case R.id.menu_all:
                viewPager.setCurrentItem(0);
                return true;
            case R.id.menu_fav:
                viewPager.setCurrentItem(1);
                return true;
            case R.id.menu_settings:
                startActivity(SettingsActivity.getStartIntent(this));
                return true;

             default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showPermissionDenied() {
        vNoPermission.setVisibility(View.VISIBLE);
    }

    public Location getLocation() {
        // TODO
        Location mockLocation = new Location("mock");
        mockLocation.setLatitude(54.8580093);
        mockLocation.setLongitude(83.1100235);
        return mockLocation;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    public static Intent getStartIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    public static Intent getHomeIntent(Context context) {
        final Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(homeIntent);
        return homeIntent;
    }
}