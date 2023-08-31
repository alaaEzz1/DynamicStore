package com.elmohandes.storeegypt.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.elmohandes.storeegypt.R
import com.elmohandes.storeegypt.models.HomeSliderModel

class HomeSliderAdapter(val images:List<HomeSliderModel>) :
    RecyclerView.Adapter<HomeSliderAdapter.HomeSliderVH>() {

    inner class HomeSliderVH(itemView: View) : ViewHolder(itemView){
        val img = itemView.findViewById<ImageView>(R.id.home_slider_img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeSliderVH {
        return HomeSliderVH(LayoutInflater.from(parent.context).inflate(
            R.layout.home_slider_item,parent,false
        ))
    }

    override fun onBindViewHolder(holder: HomeSliderVH, position: Int) {
        Glide.with(holder.itemView.context).load(images[position].imageUrl)
            .into(holder.img)
    }

    override fun getItemCount(): Int {
        return images.size
    }
}