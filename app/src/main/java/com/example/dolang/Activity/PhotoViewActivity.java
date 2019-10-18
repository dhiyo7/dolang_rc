package com.example.dolang.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.dolang.Network.ApiClient;
import com.example.dolang.R;

public class PhotoViewActivity extends AppCompatActivity {

    private com.github.chrisbanes.photoview.PhotoView photo_View;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);

        photo_View = findViewById(R.id.photo_view);
        getSupportActionBar().hide();
        try {
            Glide.with(getApplicationContext()).load(ApiClient.ENDPOINT+"images/"+getIntent().getStringExtra("Gambar")).into(photo_View);
        }catch (Exception e){
            System.err.println("Error" + e.getMessage());
            Toast.makeText(this, "Gambar gagal dimuat", Toast.LENGTH_SHORT).show();
        }
    }

}
