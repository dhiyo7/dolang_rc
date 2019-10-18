package com.example.dolang.Adapter;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
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

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

public class NearmeAdapter extends RecyclerView.Adapter<NearmeAdapter.ViewHolder> {
    private List<Tour> tours;
    private Context context;
    private Location myLocation;

    public NearmeAdapter(List<Tour> tours, Context context, Location myLocation) {
        this.tours = tours;
        this.context = context;
        this.myLocation = myLocation;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_nearme, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(tours.get(i), context, myLocation);
    }

    @Override
    public int getItemCount() { return tours.size(); }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView title, distance;
        ImageView ivNear;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tv_nearme_title);
            distance = itemView.findViewById(R.id.tv_nearme_distance);
            ivNear = itemView.findViewById(R.id.ivNear);
        }

        void bind(final Tour tour, final Context context, Location l){
            DecimalFormat df2 = new DecimalFormat("#.#");
            df2.setRoundingMode(RoundingMode.UP);
            title.setText(tour.getName());
            Location tourLocation = new Location(tour.getName());
            tourLocation.setLatitude(Double.parseDouble(tour.getLatitude()));
            tourLocation.setLongitude(Double.parseDouble(tour.getLongitude()));
            distance.setText(df2.format((l.distanceTo(tourLocation) / 1000)) + "km");
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("ID", tour.getId());
                    context.startActivity(intent);
                }
            });


            Glide.with(context).load(ApiClient.ENDPOINT+"images/"+tour.getImage()).into(ivNear);

        }
    }
}
