package com.example.dolang.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dolang.Model.ModelKomentar;
import com.example.dolang.R;

import java.util.List;

public class AdapterKomentar extends RecyclerView.Adapter<AdapterKomentar.ViewHolder> {

    private List<ModelKomentar> comments;
    private Context context;

    public AdapterKomentar(List<ModelKomentar> comments, Context context) {
        this.comments = comments;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_comment, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(comments.get(i), context);
    }

    @Override
    public int getItemCount() { return comments.size(); }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView _name, _message;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            _name = itemView.findViewById(R.id.tv_name);
            _message = itemView.findViewById(R.id.tv_message);
        }

        void bind(ModelKomentar komentar, final Context context){
            _name.setText(komentar.getName());
            _message.setText(komentar.getMessages());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Tapped", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
