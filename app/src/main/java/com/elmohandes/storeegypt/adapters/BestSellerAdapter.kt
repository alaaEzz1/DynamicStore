package com.elmohandes.storeegypt.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.elmohandes.storeegypt.R
import com.elmohandes.storeegypt.databinding.HomeBestSellerItemBinding
import com.elmohandes.storeegypt.models.ProductModel

class BestSellerAdapter :
    ListAdapter<ProductModel,BestSellerAdapter.BestSellerVH>(BestSellerDiffCallback()) {

    class BestSellerVH(itemView: View) : ViewHolder(itemView){
        val binding = HomeBestSellerItemBinding.bind(itemView)
    }

    class BestSellerDiffCallback : DiffUtil.ItemCallback<ProductModel>(){
        override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
            return oldItem == newItem
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestSellerVH {
        return BestSellerVH(LayoutInflater.from(parent.context).inflate(
            R.layout.home_best_seller_item,parent,false
        ))
    }

    override fun onBindViewHolder(holder: BestSellerVH, position: Int) {
        val bestProduct = getItem(position)
        holder.binding.bestSellerName.text = bestProduct.name
        holder.binding.bestSellerPrice.text = "${bestProduct.price} LE"
        holder.binding.bestSellerRating.text = "(${bestProduct.rate})"
        Glide.with(holder.itemView.context).load(bestProduct.imageUrl[0])
            .into(holder.binding.bestSellerImg)
    }
}