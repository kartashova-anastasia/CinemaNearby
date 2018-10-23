package best.anastasia.cinemanearby.fragments;

import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import best.anastasia.cinemanearby.CinemaApp;
import best.anastasia.cinemanearby.R;
import best.anastasia.cinemanearby.concepts.Cinema;
import best.anastasia.cinemanearby.utils.UiUtils;
import best.anastasia.cinemanearby.utils.Utils;
import best.anastasia.cinemanearby.fragments.CinemaViewHolder;

public class CinemaRecyclerAdapter extends RecyclerView.Adapter<CinemaViewHolder> {
    private OnItemClickListener listener;
    private List<Cinema> cinemaList;
    private Location curLocation;
    @Inject
    protected Context context;

    public CinemaRecyclerAdapter(@Nullable List<Cinema> cinemaList, @Nullable Location location,
                                 OnItemClickListener listener) {
        this.cinemaList = cinemaList == null ? new ArrayList<>() : cinemaList;
        this.curLocation = location;
        this.listener = listener;
       // CinemaApp.getComponent().inject(this);
    }

    @Override
    public CinemaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_cinema, parent, false);
        return new CinemaViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(CinemaViewHolder holder, int position) {
        final Cinema cinema = getItem(position);
        holder.tvName.setText(cinema.getName());
        holder.tvRating.setText(context.getString(R.string.rating_format, cinema.getRating()));
        holder.tvAddress.setText(cinema.getAddress());
        final String workTime = cinema.getWorkTime();

        holder.tvDistance.setText(context.getString(R.string.distance_format,
                Utils.distanceBetweenPoints(cinema.getLocation(), curLocation)));
       /* holder.ivFavorite.setImageResource(cinema.isFavorite() ?
                R.drawable.ic_heart_small : R.drawable.ic_heart_small_outline);*/
        UiUtils.setVisibility(holder.vLocal, cinema.getScope().equals("APP"));
    }

    @Override
    public int getItemCount() {
        return cinemaList.size();
    }

    @NonNull
    public Cinema getItem(int position) {
        return cinemaList.get(position);
    }

    public void changeData(@NonNull List<Cinema> cinemaList) {
        this.cinemaList = cinemaList;
        notifyDataSetChanged();
    }

    public void changeLocation(@NonNull Location location) {
        this.curLocation = location;
        notifyDataSetChanged();
    }
}