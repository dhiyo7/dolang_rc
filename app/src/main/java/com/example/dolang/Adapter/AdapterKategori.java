package com.example.dolang.Adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.dolang.Activity.DetailActivity;
import com.example.dolang.Model.Tour;
import com.example.dolang.Network.ApiClient;
import com.example.dolang.R;
import java.util.List;

public class AdapterKategori extends RecyclerView.Adapter<AdapterKategori.ViewHolder>{
    private Context context;
    private List<Tour> modelKategoriList;

    public AdapterKategori(Context context, List<Tour> modelKategoriList) {
        this.context = context;
        this.modelKategoriList = modelKategoriList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_kategori, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int pos) {
        holder.binding(modelKategoriList.get(pos), context);
    }

    @Override
    public int getItemCount() {
        return modelKategoriList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView image;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            image = itemView.findViewById(R.id.iv_wipas);
        }

        void binding(final Tour kategori, final Context context){
            title.setText(kategori.getName());
            Glide.with(context).load(ApiClient.ENDPOINT+"images/"+kategori.getImage()).into(image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("ID", kategori.getId());
                    context.startActivity(intent);
                }
            });
        }
    }
}