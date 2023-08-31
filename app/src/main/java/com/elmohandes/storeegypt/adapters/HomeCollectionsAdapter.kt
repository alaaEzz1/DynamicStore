package com.elmohandes.storeegypt.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.elmohandes.storeegypt.R
import com.elmohandes.storeegypt.databinding.HomeCollectionsItemBinding
import com.elmohandes.storeegypt.models.CollectionsModel

class HomeCollectionsAdapter :
    ListAdapter<CollectionsModel, HomeCollectionsAdapter.HomeCollectionsVH>
        (CollectionsDiffCallback()) {
    inner class HomeCollectionsVH(itemView: View) : ViewHolder(itemView){
        val binding = HomeCollectionsItemBinding.bind(itemView)
    }

    class CollectionsDiffCallback : DiffUtil.ItemCallback<CollectionsModel>(){
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCollectionsVH {
        return HomeCollectionsVH(
            LayoutInflater.from(parent.context).inflate(
                R.layout.home_collections_item,parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: HomeCollectionsVH, position: Int) {
        //TODO: ListAdapter property called getItem return model class
        val collections = getItem(position)
        holder.binding.homeCollectionName.text = collections.name
        Glide.with(holder.itemView.context).load(collections.imageUrl)
            .into(holder.binding.homeCollectionImg)
    }
}