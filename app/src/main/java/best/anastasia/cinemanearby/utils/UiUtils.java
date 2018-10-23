package best.anastasia.cinemanearby.utils;

import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class UiUtils {
    public static void setToolbarTitle(AppCompatActivity activity, String title) {
        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setTitle(title);
        }
    }

    public static void setToolbarTitle(AppCompatActivity activity, @StringRes int title) {
        setToolbarTitle(activity, activity.getString(title));
    }

    public static void setToolbarHomeEnabled(AppCompatActivity activity, boolean homeEnabled) {
        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(homeEnabled);
        }
    }

    public static void setVisibility(View view, boolean visible) {
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }
}
