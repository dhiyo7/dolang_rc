package com.example.dolang.Panorama;

import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.example.dolang.R;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;

public class Foto extends AppCompatActivity {

    private VrPanoramaView panoWidgetView;
    private ImageLoaderTask backgroundImageLoaderTask;
    private ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto);
        setTitle("Wippas");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().hide();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        loading = findViewById(R.id.loading);
        panoWidgetView = findViewById(R.id.pano_view);
        loadPanoImage();
    }

    private synchronized void loadPanoImage() {
        loading.setVisibility(View.VISIBLE);
        ImageLoaderTask task = backgroundImageLoaderTask;
        if (task != null && !task.isCancelled()) {
            // Cancel any task from a previous loading.
            task.cancel(true);
        }

        // pass in the name of the image to load from assets.
        VrPanoramaView.Options viewOptions = new VrPanoramaView.Options();
        viewOptions.inputType = VrPanoramaView.Options.TYPE_MONO;

        // use the name of the image in the assets/ directory.

        String panoImageName = "Wippas.jpg";
        if(getUriImage() != null){
            // create the task passing the widget view and call execute to start.
            task = new ImageLoaderTask(panoWidgetView, viewOptions, getUriImage());
            task.execute(this.getAssets());
            backgroundImageLoaderTask = task;
        }
        loading.setVisibility(View.GONE);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    private String getUriImage() {
        return getIntent().getStringExtra("URL");
    }

}
