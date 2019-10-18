package com.example.dolang.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.dolang.Adapter.SearchResultAdapter;
import com.example.dolang.Converter.BaseListResponse;
import com.example.dolang.Model.Tour;
import com.example.dolang.Network.ApiClient;
import com.example.dolang.Network.TourInterface;
import com.example.dolang.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TourInterface tourService = ApiClient.getApiInterfaceService();
    private Call<BaseListResponse<Tour>> request;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp_);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setTitle("Hasil pencarian "+getQuery());
        recyclerView = findViewById(R.id.rv_search_result);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        load(getQuery());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_result, menu);
        MenuItem searchView = menu.findItem(R.id.menu_search);
        if(searchView != null){
            SearchView sview = (SearchView) searchView.getActionView();
            sview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    if(!s.isEmpty()){
                        load(s);
                        getSupportActionBar().setTitle("Hasil pencarian "+s);
                    }
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    return false;
                }
            });
        }
        return super.onCreateOptionsMenu(menu);
    }





    private String getQuery() { return getIntent().getStringExtra("QUERY");}

    private void load(String query){
        request = tourService.search(query);
        request.enqueue(new Callback<BaseListResponse<Tour>>() {
            @Override
            public void onResponse(Call<BaseListResponse<Tour>> call, Response<BaseListResponse<Tour>> response) {
                if(response.isSuccessful()){
                    BaseListResponse body = response.body();
                    if(body != null && body.getStatus()){
                        List<Tour> tours = body.getData();
                        recyclerView.setAdapter(new SearchResultAdapter(tours, SearchResultActivity.this));
                    }
                }else{
                    Toast.makeText(SearchResultActivity.this, "Tidak dapat mengambil hasil dari server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseListResponse<Tour>> call, Throwable t) {
                System.err.println("Except "+t.getMessage());
                Toast.makeText(SearchResultActivity.this, "Terjadi kesalaham. Coba lagi nanti", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(request != null){request.cancel();}
    }
}
