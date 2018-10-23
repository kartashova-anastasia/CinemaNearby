package best.anastasia.cinemanearby.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import best.anastasia.cinemanearby.R;
import best.anastasia.cinemanearby.retrofit.GooglePlacesService;


public class PhotoFragment extends Fragment implements Callback {
    private static final String PHOTO_REFERENCE_EXTRA = "photo_reference_extra";

    private String photoReference;

    @BindView(R.id.pbLoading)
    protected View pbLoading;
    @BindView(R.id.tvNoPhotos)
    protected View tvNoPhotos;
    @BindView(R.id.ivPhoto)
    protected ImageView ivPhoto;

    public static PhotoFragment newInstance(String photoReference) {
        final Bundle args = new Bundle();
        if (photoReference != null) {
            args.putString(PHOTO_REFERENCE_EXTRA, photoReference);
        }
        final PhotoFragment fragment = new PhotoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(PHOTO_REFERENCE_EXTRA)) {
            photoReference = getArguments().getString(PHOTO_REFERENCE_EXTRA);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (photoReference != null) {
            tvNoPhotos.setVisibility(View.GONE);
            pbLoading.setVisibility(View.VISIBLE);
            Picasso.Builder builder = new Picasso.Builder(getContext());
            builder.listener((picasso, uri, exception) -> exception.printStackTrace());
            builder.build().load(getPhotoUrl(photoReference)).into(ivPhoto, this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View contentView = inflater.inflate(R.layout.fragment_photo, container, false);
        ButterKnife.bind(this, contentView);
        return contentView;
    }

    private String getPhotoUrl(String reference) {
        final String apiKey = getContext().getString(R.string.google_places_key);
        return GooglePlacesService.BASE_URL +
                "maps/api/place/photo?key=" + apiKey + "&" +
                "photoreference=" + reference + "&" + "maxwidth=600";
    }

    @Override
    public void onSuccess() {
        ivPhoto.setVisibility(View.VISIBLE);
        pbLoading.setVisibility(View.GONE);
    }

    @Override
    public void onError() {
        tvNoPhotos.setVisibility(View.VISIBLE);
        pbLoading.setVisibility(View.GONE);
    }
}