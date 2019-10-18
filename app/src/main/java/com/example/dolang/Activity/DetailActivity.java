package com.example.dolang.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;

import com.example.dolang.Model.Tour;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Parcelable;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dolang.Converter.BaseResponse;
import com.example.dolang.Model.ModelKomentar;
import com.example.dolang.Network.ApiClient;
import com.example.dolang.Network.TourInterface;
import com.example.dolang.Panorama.Foto;
import com.example.dolang.R;
import com.uncopt.android.widget.text.justify.JustifiedTextView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.text.Html.fromHtml;

public class DetailActivity extends AppCompatActivity {

    private TextView address, category, region, price, operasional;
    private ImageView iv_detail_gambar;
    private JustifiedTextView description;
    private TourInterface api;
    private LinearLayout pano, route;
    private ArrayList<ModelKomentar> comments = new ArrayList<>();
    private Button btn_lihat_komentar, btnPano;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private String tittle = " ";
    private RelativeLayout btmSheetDetail;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pano = findViewById(R.id.pano);
        route = findViewById(R.id.route);
        initComponents();
        fetchData();
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    private Spanned fromHtml(String s){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(s, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(s);
        }

    }

    private void initComponents(){
        address = findViewById(R.id.tv_alamat);
        category = findViewById(R.id.tv_kategori);
//        region = findViewById(R.id.tv_wilayah);
        price = findViewById(R.id.tv_htm);
        btn_lihat_komentar = findViewById(R.id.btn_lihat_komentar);
        iv_detail_gambar = findViewById(R.id.iv_detail_gambar);
        description = findViewById(R.id.tv_deskrip);
        api = ApiClient.getApiInterfaceService();
        collapsingToolbarLayout = findViewById(R.id.toolbar_layout);
//        btnPano = findViewById(R.id.btnPano);
//        btnPano.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent a = new Intent(DetailActivity.this, PanoDinamisActivity.class);
//                startActivity(a);
//            }
//        });

    }

    private String getId(){ return String.valueOf(getIntent().getIntExtra("ID", 0));}

    private void fetchData(){
        Call<BaseResponse<Tour>> request = api.showById(getId());
        request.enqueue(new Callback<BaseResponse<Tour>>() {
            @Override
            public void onResponse(Call<BaseResponse<Tour>> call, Response<BaseResponse<Tour>> response) {
                if (response.isSuccessful()){
                    btn_lihat_komentar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(DetailActivity.this, CommentAcitivity.class);
                            i.putParcelableArrayListExtra("COMMENTS", comments);
                            i.putExtra("TOUR_ID", getId());
                            startActivity(i);
                        }
                    });
                    BaseResponse body = response.body();
                    if(body.getStatus()){
                        fillAll((Tour) body.getData());
                    }
                }else{
                    Toast.makeText(DetailActivity.this, "Cannot get data from server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Tour>> call, Throwable t) {
                Toast.makeText(DetailActivity.this, "Something went wrong with error code "+ t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fillAll(final Tour kategori){
        category.setText(kategori.getCategory());
        address.setText(kategori.getAddress());
//        region.setText(kategori.getRegion());
        price.setText(kategori.getPrice());
        comments = (ArrayList<ModelKomentar>) kategori.getComment();
//        collapsingToolbarLayout.setTitle(kategori.getTitle());
        Glide.with(getApplicationContext()).load(ApiClient.ENDPOINT+"images/"+kategori.getImage()).into(iv_detail_gambar);
        iv_detail_gambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, PhotoViewActivity.class);
                intent.putExtra("Gambar",kategori.getImage());
                startActivity(intent);
            }
        });
        description.setText(fromHtml(kategori.getDescription()));
        collapsingToolbarLayout.setTitle(kategori.getName());
        tittle = kategori.getName();

        pano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
                Sensor gyro = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
                if(gyro == null){
                    AlertDialog.Builder b = new AlertDialog.Builder(DetailActivity.this);
                    b.setMessage("Aplikasi ini memerlukan Gyroscope dan nampaknya perangkat anda tidak mendukung");
                    b.setPositiveButton("Mengerti", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent it = new Intent(DetailActivity.this, PanoramaActivity.class);
                            it.putParcelableArrayListExtra("PANORAMAS", (ArrayList<? extends Parcelable>) kategori.getPanorama());
                            startActivity(it);
                        }
                    });
                    AlertDialog a = b.create();
                    a.show();
                    return;
                }

                Intent it = new Intent(DetailActivity.this, PanoramaActivity.class);
                it.putParcelableArrayListExtra("PANORAMAS", (ArrayList<? extends Parcelable>) kategori.getPanorama());
                startActivity(it);
            }
        });

        route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailActivity.this, "Route", Toast.LENGTH_LONG).show();
            }
        });
    }

}
