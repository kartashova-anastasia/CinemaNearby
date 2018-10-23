package best.anastasia.cinemanearby.fragments;


import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;


import best.anastasia.cinemanearby.concepts.Photo;
import best.anastasia.cinemanearby.fragments.PhotoFragment;
import java.util.ArrayList;
import java.util.List;

public class PhotoViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<String> linksList = new ArrayList<>();

    public PhotoViewPagerAdapter(FragmentManager fm, List<String> linksList) {
        super(fm);
        this.linksList = linksList != null && linksList.size() > 0 ? linksList : this.linksList;
    }

    @Override
    public Fragment getItem(int position) {
        return PhotoFragment.newInstance(
                position >= linksList.size() ? null : linksList.get(position));
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return linksList.size() == 0 ? 1 : linksList.size();
    }

    public void changeData(@NonNull List<Photo> cinemaPhotos) {
        linksList = Stream.of(cinemaPhotos).map(Photo::getLink).collect(Collectors.toList());
        notifyDataSetChanged();
    }
}