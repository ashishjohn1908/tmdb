package com.app.tmdb.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.ImageView;

import com.app.tmdb.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class Utils {

    public static void setImage(final String imageUrl, ImageView imageView) {
        imageView.post(() -> Picasso.get()
                .load(imageUrl)
                .placeholder(R.mipmap.ic_image_placeholder)
                .error(R.mipmap.ic_error_image_placeholder)
                .noFade()
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                }));
    }

    public static boolean checkNetwork(Context context) {
        if (context != null) {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null)
                    for (int i = 0; i < info.length; i++)
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
            }
        }
        return false;
    }
}