package com.example.dolang.Panorama;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageLoaderTask extends AsyncTask<AssetManager, Void, Bitmap> {
    private static final String TAG = "ImageLoaderTask";
    private final String assetName;
    private final WeakReference<VrPanoramaView> viewReference;
    private final VrPanoramaView.Options viewOptions;
    private static WeakReference<Bitmap> lastBitmap = new WeakReference<>(null);
    private static String lastName;
    @Override
    protected Bitmap doInBackground(AssetManager... params) {
        AssetManager assetManager = params[0];
        if (assetName.equals(lastName) && lastBitmap.get() != null) {
            return lastBitmap.get();
        }



        try {
            URL url = new URL(assetName);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            //return myBitmap;


            //Bitmap b = BitmapFactory.decodeStream(istr);

            lastBitmap = new WeakReference<>(myBitmap);
            lastName = assetName;
            return myBitmap;
        } catch (IOException e) {
            Log.e(TAG, "Could not decode default bitmap: " + e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        final VrPanoramaView vw = viewReference.get();
        if (vw != null && bitmap != null) {
            vw.loadImageFromBitmap(bitmap, viewOptions);
        }
    }


    public ImageLoaderTask(VrPanoramaView view, VrPanoramaView.Options viewOptions, String assetName) {
        viewReference = new WeakReference<>(view);
        this.viewOptions = viewOptions;
        this.assetName = assetName;
    }
}