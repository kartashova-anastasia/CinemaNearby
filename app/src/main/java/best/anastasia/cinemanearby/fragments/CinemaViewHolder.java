package best.anastasia.cinemanearby.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import best.anastasia.cinemanearby.R;

import best.anastasia.cinemanearby.fragments.OnItemClickListener;

public class CinemaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.tvDistance) public TextView tvDistance;
    @BindView(R.id.ivFavorite) public ImageView ivFavorite;
    @BindView(R.id.tvAddress) public TextView tvAddress;
    @BindView(R.id.tvRating) public TextView tvRating;
    @BindView(R.id.tvName) public TextView tvName;

    @BindView(R.id.vLocal) public View vLocal;

    private OnItemClickListener listener;

    public CinemaViewHolder(View itemView, OnItemClickListener listener) {
        super(itemView);

        ButterKnife.bind(this, itemView);
        this.listener = listener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        listener.onItemClicked(getAdapterPosition());
    }
}
