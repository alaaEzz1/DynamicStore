package com.elmohandes.storeegypt.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.elmohandes.storeegypt.R
import com.elmohandes.storeegypt.databinding.ProductCollectionsItemBinding
import com.elmohandes.storeegypt.models.CollectionsModel

class ProductCollectionsAdapter: ListAdapter<CollectionsModel
        ,ProductCollectionsAdapter.ProductCollectionsVH>(ProductCollectionsDiffCallback()) {
    inner class ProductCollectionsVH(itemView: View) : ViewHolder(itemView) {
        val binding = ProductCollectionsItemBinding.bind(itemView)
    }

    class ProductCollectionsDiffCallback : DiffUtil.ItemCallback<CollectionsModel>(){
        override fun areItemsTheSame(
            oldItem: CollectionsModel,
            newItem: CollectionsModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CollectionsModel,
            newItem: CollectionsModel
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductCollectionsVH {
        return ProductCollectionsVH(LayoutInflater.from(parent.context).inflate(
            R.layout.product_collections_item,parent,false
        ))
    }

    override fun onBindViewHolder(holder: ProductCollectionsVH, position: Int) {
        val collections = getItem(position)
        holder.binding.productCollectionTitle.text = collections.name
        Glide.with(holder.itemView.context).load(collections.imageUrl).into(holder.binding.productCollectionImg)
    }
}