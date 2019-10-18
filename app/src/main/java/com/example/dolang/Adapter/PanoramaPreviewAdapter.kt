package com.example.dolang.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.dolang.Model.Panorama
import com.example.dolang.Network.ApiClient
import com.example.dolang.Panorama.Foto
import com.example.dolang.R
import kotlinx.android.synthetic.main.list_item_panorama.view.*

class PanoramaPreviewAdapter(private var panos : List<Panorama>, private var context: Context) : RecyclerView.Adapter<PanoramaPreviewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_panorama, parent, false))
    }

    override fun getItemCount() = panos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(panos[position], context)

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun bind(pano : Panorama, context: Context){
            Glide.with(context).load(ApiClient.ENDPOINT+"images/"+pano.path).placeholder(R.drawable.loading_pic)
                    .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)).into(itemView.image_preview)
            itemView.setOnClickListener {
                context.startActivity(Intent(context, Foto::class.java).apply {
                    putExtra("URL", ApiClient.ENDPOINT+"images/"+pano.path)
                })
            }
        }
    }
}