package com.example.dolang.MenuFragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.example.dolang.Adapter.AdapterKategori;
import com.example.dolang.Converter.BaseListResponse;
import com.example.dolang.Model.Tour;
import com.example.dolang.Network.ApiClient;
import com.example.dolang.Network.TourInterface;
import com.example.dolang.R;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WisataFragment extends Fragment {

    private RecyclerView rv_kategori;
    private RecyclerView.LayoutManager layoutManager;
    private List<Tour> TourList;
    private RecyclerView.Adapter adapter;
    private TourInterface tourInterface;
    private SearchView searchView;
    ProgressBar prograss;
    RelativeLayout rl_kategori, rootErrorView, rootEmptyView;
    private Call<BaseListResponse<Tour>> call;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wisata, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tourInterface = ApiClient.getApiInterfaceService();
        prograss = view.findViewById(R.id.prograss);
        rv_kategori = view.findViewById(R.id.rv_wisata);
        layoutManager = new LinearLayoutManager(getActivity());
        rv_kategori.setLayoutManager(layoutManager);
        rv_kategori.setHasFixedSize(true);
        rootEmptyView = view.findViewById(R.id.root_empty_view);
        rootErrorView =  view.findViewById(R.id.root_error_view);
//        searchView = view.findViewById(R.id.sv);

//        searchView.setSearchableInfo();
    }





    public void fetchData(){
        call.enqueue(new Callback<BaseListResponse<Tour>>() {
            @Override
            public void onResponse(Call<BaseListResponse<Tour>> call, Response<BaseListResponse<Tour>> response) {
                if(response.isSuccessful()){
                    BaseListResponse bs = response.body();
                    if(bs.getStatus()){
                        if(bs.getData().isEmpty()){
                            rootErrorView.setVisibility(View.GONE);
                            rootEmptyView.setVisibility(View.VISIBLE);
                        }else{
                            TourList = bs.getData();
                            adapter = new AdapterKategori(getActivity(), TourList);
                            rv_kategori.setAdapter(adapter);
                            rootErrorView.setVisibility(View.GONE);
                            rootEmptyView.setVisibility(View.GONE);
                        }
                    }else{
                        Toast.makeText(getActivity(), "false", Toast.LENGTH_SHORT).show();
                        rootErrorView.setVisibility(View.GONE);
                        rootEmptyView.setVisibility(View.VISIBLE);
                    }
                }else{
                    Toast.makeText(getActivity(), "is not success "+response.code(), Toast.LENGTH_SHORT).show();
                    rootErrorView.setVisibility(View.VISIBLE);
                    rootEmptyView.setVisibility(View.GONE);
                }
                prograss.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<BaseListResponse<Tour>> call, Throwable t) {
                prograss.setVisibility(View.INVISIBLE);
                rootErrorView.setVisibility(View.VISIBLE);
                rootEmptyView.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Error : "+ t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }




    private void checkParams(){
        if(getArguments().getString("CATEGORY").equals("curug")){
            //load wisata alam buatan
            call = tourInterface.showByCategory("Curug");
            fetchData();
        }else if(getArguments().getString("CATEGORY").equals("bukit")){
            //load wisata dg kategori lain
            call = tourInterface.showByCategory("Bukit");
            fetchData();
        }else if(getArguments().getString("CATEGORY").equals("pantai")){
            //load wisata dg kategori lain
            call = tourInterface.showByCategory("Pantai");
            fetchData();
        }else if(getArguments().getString("CATEGORY").equals("taman")){
            //load wisata dg kategori lain
            call = tourInterface.showByCategory("Taman");
            fetchData();
        }else{
            //load wisata dg kategori lain
            call = tourInterface.showByCategory("Kolam Renang");
            fetchData();
        }
    }



    @Override
    public void onResume() {
        super.onResume();
        checkParams();
    }
}
