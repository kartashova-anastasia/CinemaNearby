package best.anastasia.cinemanearby.fragments;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import best.anastasia.cinemanearby.fragments.BaseFragment;

public class MainViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragmentsList = new ArrayList<>();
    private List<String> titlesList = new ArrayList<>();

    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    @Nullable
    public Fragment getItem(int position) {
        return position >= fragmentsList.size() ? null : fragmentsList.get(position);
    }

    public String getTitle(int position) {
        return titlesList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentsList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titlesList.get(position);
    }

    public void addFragment(Fragment fragment, String title) {
        fragmentsList.add(fragment);
        titlesList.add(title);
    }

    public void showCinemaList() {
        ((BaseFragment) fragmentsList.get(0)).onDataLoaded();
        ((BaseFragment) fragmentsList.get(1)).onDataLoaded();
    }

    public void showRefreshing(boolean refresh) {
        ((BaseFragment) fragmentsList.get(0)).showRefreshing(refresh);
        ((BaseFragment) fragmentsList.get(1)).showRefreshing(refresh);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}