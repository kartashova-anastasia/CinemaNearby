package best.anastasia.cinemanearby.fragments;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.util.List;

import best.anastasia.cinemanearby.activities.CinemaActivity;
import best.anastasia.cinemanearby.activities.SettingsActivity;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;


import best.anastasia.cinemanearby.R;
import best.anastasia.cinemanearby.activities.MainActivity;
import best.anastasia.cinemanearby.concepts.Cinema;
import best.anastasia.cinemanearby.mvp.CinemaListPresenter;
import best.anastasia.cinemanearby.views.CinemaListView;


public class CinemaListFragment extends BaseFragment implements CinemaListView, OnItemClickListener {
    private static final String FAVORITE_ONLY_EXTRA = "favorite_only";

    @BindView(R.id.refreshLayout) protected SwipeRefreshLayout refreshLayout;
    @BindView(R.id.llNoNetwork) protected View vNoNetwork;
    @BindView(R.id.llNoPlaces) protected View vNoPlaces;
    @BindView(R.id.llNoPermission) protected View vNoPermission;
    @BindView(R.id.rvCinemas) protected RecyclerView rvCinemas;

    @InjectPresenter
    CinemaListPresenter presenter;
    private CinemaRecyclerAdapter adapter;

    @ProvidePresenter
    CinemaListPresenter provideCinemaListPresenter() {
        return new CinemaListPresenter(getArguments().getBoolean(FAVORITE_ONLY_EXTRA));
    }

    public static CinemaListFragment newInstance(boolean favoriteOnly) {
        CinemaListFragment fragment = new CinemaListFragment();
        final Bundle args = new Bundle();
        args.putBoolean(FAVORITE_ONLY_EXTRA, favoriteOnly);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View contentView = super.onCreateView(inflater, container, savedInstanceState);
        refreshLayout.setOnRefreshListener(() -> ((MainActivity) getActivity()).onRefresh());
        setupCinemaRecyclerView();

        return contentView;
    }

    @Override
    public void onDataLoaded() {
        presenter.onDataLoaded();
    }

    @Override
    public void showRefreshing(boolean refresh) {
        refreshLayout.setRefreshing(refresh);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu_cinema_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_opennow) {
            item.setChecked(!item.isChecked());
            presenter.onOpenNowClicked(item.isChecked());
            return true;
        } else if (item.getItemId() == R.id.action_sort_distance) {
            presenter.onSortDistanceClicked();
            return true;
        } else if (item.getItemId() == R.id.action_sort_rating) {
            presenter.onSortRatingClicked();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showPermissionDenied() {
        vNoPermission.setVisibility(View.VISIBLE);
    }

    private void setupCinemaRecyclerView() {
        adapter = new CinemaRecyclerAdapter(null, null, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvCinemas.setLayoutManager(layoutManager);
        rvCinemas.setAdapter(adapter);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_cinema_list;
    }

    @Override
    public void showNoNetwork() {
        vNoNetwork.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNoCinemas() {
        vNoPlaces.setVisibility(View.VISIBLE);
    }

    @Override
    public void showCinemas(List<Cinema> cinemas) {
        adapter.changeData(cinemas);
        vNoPlaces.setVisibility(View.GONE);
    }

    @Override
    public void navigateToSettingsScreen() {
        startActivity(SettingsActivity.getStartIntent(getContext()));
    }

    @Override
    public void onItemClicked(int position) {
        presenter.onCinemaClicked(position);
    }



    @Override
    public void openCinemaDetails(String placeId) {
        startActivity(CinemaActivity.getStartIntent(getContext(), placeId));
    }

    @Override
    public void changeLocation(Location location) {
        adapter.changeLocation(location);
    }


}