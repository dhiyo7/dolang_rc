package com.example.dolang.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import  android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.dolang.Converter.BaseListResponse;
import com.example.dolang.MenuFragment.Home;
import com.example.dolang.MenuFragment.Kategori;
import com.example.dolang.Model.ModelKategori;
import com.example.dolang.Network.TourInterface;
import com.example.dolang.R;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.List;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MaterialSearchBar.OnSearchActionListener {

    MaterialSearchBar searchBar;
    private DrawerLayout drawer;

    private SharedPreferences setting;
    private RecyclerView rv_kategori;
    private RecyclerView.LayoutManager layoutManager;
    private List<ModelKategori> modelKategoriList;
    private RecyclerView.Adapter adapter;
    private TourInterface tourInterface;
    private Call<BaseListResponse<ModelKategori>> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setting = getSharedPreferences("USER", MODE_PRIVATE);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        searchBar = findViewById(R.id.searchBar);
        searchBar.setOnSearchActionListener(this);
//        searchBar.inflateMenu(R.menu.main);


        if (savedInstanceState == null) {
            Fragment fragment = null;
            Class fragmentClass = null;
            fragmentClass = Home.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container_fragment, fragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView1 = (NavigationView) findViewById(R.id.nav_view);
        navigationView1.setNavigationItemSelectedListener(this);

        Menu menu = navigationView.getMenu();

        if (isNotLoggedIn()){
            MenuItem nav_keluar = menu.findItem(R.id.nav_keluar);
            nav_keluar.setTitle("Masuk");
            nav_keluar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    Intent a  = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(a);
                    onResume();
                    finish();
                    return false;
                }
            });
        } else {
            final MenuItem nav_keluar = menu.findItem(R.id.nav_keluar);
            nav_keluar.setTitle("Keluar");
            nav_keluar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    SharedPreferences.Editor editor = setting.edit();
                    editor.clear();
                    editor.commit();
                    nav_keluar.setTitle("Masuk");
                    return false;
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
////        getMenuInflater().inflate(R.menu.main, menu);
////        final MenuItem item = menu.findItem(R.id.seacrh);
////        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
////        searchView.setQueryHint("Cari Nama Mahasiswa");
////        searchView.setIconified(false);
////        searchView.setOnQueryTextListener((SearchView.OnQueryTextListener) this);
//        return true;
//    }
//
//
//
//    private void loadData() {
//        call.enqueue(new Callback<BaseListResponse<ModelKategori>>() {
//            @Override
//            public void onResponse(Call<BaseListResponse<ModelKategori>> call, Response<BaseListResponse<ModelKategori>> response) {
//                if(response.isSuccessful()){
//                    BaseListResponse bs = response.body();
//                    if(bs.getStatus()){
//                        modelKategoriList = bs.getData();
//                        adapter = new AdapterKategori(MainActivity.this, modelKategoriList);
//                        rv_kategori.setAdapter(adapter);
//                    }
//                }else{
//                    Toast.makeText(MainActivity.this, "Cannot fetch data from server", Toast.LENGTH_SHORT).show();
//                }
////                prograss.setVisibility(View.INVISIBLE);
//            }
//
//            @Override
//            public void onFailure(Call<BaseListResponse<ModelKategori>> call, Throwable t) {
////                prograss.setVisibility(View.INVISIBLE);
//                Toast.makeText(MainActivity.this, "Error : "+ t.toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void search(String query) {
//        tourInterface.search(query).enqueue(new Callback<BaseListResponse<ModelKategori>>() {
//            @Override
//            public void onResponse(Call<BaseListResponse<ModelKategori>> call, Response<BaseListResponse<ModelKategori>> response) {
//                if (response.isSuccessful()){
//                    BaseListResponse body = response.body();
//                    if (body != null && body.getStatus()){
//                        modelKategoriList = body.getData();
//                        adapter = new AdapterKategori(MainActivity.this, modelKategoriList);
//                        rv_kategori.setAdapter(adapter);
//                    }else {
//                        Toast.makeText(MainActivity.this, "mbuh", Toast.LENGTH_SHORT).show();
//                    }
//                }else {
//                    Toast.makeText(MainActivity.this, "ora ngerti maning", Toast.LENGTH_SHORT).show();
//                    Log.d("KUWUK " , response.message());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<BaseListResponse<ModelKategori>> call, Throwable t) {
//                Toast.makeText(MainActivity.this, "iye lah mbuh", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
////        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
////        if (id == R.id.seacrh) {
////            return true;
////        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();
        Fragment fragment = null;
        Class fragmentClass = null;

        if (id == R.id.nav_home) {
//            fragmentClass = Home.class;
            fragment = new Home();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container_fragment, fragment);
            ft.commit();
        } else if (id == R.id.nav_kategori) {
//            fragmentClass = Kategori.class;

            fragment = new Kategori();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container_fragment, fragment);
            ft.commit();
        } else if (id == R.id.nav_profile) {
//            fragmentClass = Profile.class;
            Intent a = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(a);

//            fragment = new Profile();
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.container_fragment, fragment);
//            ft.commit();
        } else if (id == R.id.nav_keluar) {
            onResume();

        } else if (id == R.id.nav_about) {
//            fragmentClass = Tentang.class;

            Intent a = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(a);

//            fragment = new Tentang();
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.container_fragment, fragment);
//            ft.commit();
        }
//        try {
//            fragment = (Fragment) fragmentClass.newInstance();
//
//        } catch (Exception e){
//            e.printStackTrace();
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private boolean isNotLoggedIn() {
        String token = setting.getString("TOKEN", "UNDIFINED");
        return token == null || token.equals("UNDIFINED");
    }

    @Override
    public void onSearchStateChanged(boolean enabled) { }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        if(!text.toString().isEmpty()){
            Intent i = new Intent(MainActivity.this, SearchResultActivity.class);
            i.putExtra("QUERY", text.toString());
            startActivity(i);
        }
    }

    @Override
    public void onButtonClicked(int buttonCode) {

        switch (buttonCode) {
            case MaterialSearchBar.BUTTON_NAVIGATION:
                drawer.openDrawer(Gravity.LEFT);
                break;
            case MaterialSearchBar.BUTTON_SPEECH:
                break;
            case MaterialSearchBar.BUTTON_BACK:
                searchBar.disableSearch();
                break;
        }


    }
}
