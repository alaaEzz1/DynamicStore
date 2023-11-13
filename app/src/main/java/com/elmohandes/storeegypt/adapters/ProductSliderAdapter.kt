package com.elmohandes.storeegypt.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.elmohandes.storeegypt.R
import com.elmohandes.storeegypt.databinding.ProductsSliderItemBinding

class ProductSliderAdapter(private val images: List<String>): Adapter<ProductSliderAdapter.ProductSliderVH>(){

    inner class ProductSliderVH(itemView: View) : ViewHolder(itemView){
        val binding = ProductsSliderItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductSliderVH {
        return ProductSliderVH(LayoutInflater.from(parent.context).inflate(
            R.layout.products_slider_item,parent,false
        ))
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: ProductSliderVH, position: Int) {
        val sliderImage = images[position]
        Glide.with(holder.itemView.context).load(sliderImage).into(holder.binding.productSliderImage)
    }
}