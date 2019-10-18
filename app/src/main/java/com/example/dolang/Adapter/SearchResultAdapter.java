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

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {
    private List<Tour> tours;
    private Context context;

    public SearchResultAdapter(List<Tour> tours, Context context) {
        this.tours = tours;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_kategori, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) { viewHolder.bind(tours.get(i), context);}

    @Override
    public int getItemCount() { return tours.size(); }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView title;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.iv_wipas);
            title = itemView.findViewById(R.id.tv_title);
        }

        void bind(final Tour tour,final Context context){
            title.setText(tour.getName());
            Glide.with(context).load(ApiClient.ENDPOINT+"dolang/public/images/"+tour.getImage()).into(image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("ID", tour.getId());
                    context.startActivity(intent);
                }
            });
        }
    }
}
