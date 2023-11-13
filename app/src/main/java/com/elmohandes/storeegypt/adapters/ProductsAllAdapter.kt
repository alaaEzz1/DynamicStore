package com.elmohandes.storeegypt.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.elmohandes.storeegypt.R
import com.elmohandes.storeegypt.adapters.listeners.HomeProductListener
import com.elmohandes.storeegypt.databinding.AllProductsItemBinding
import com.elmohandes.storeegypt.models.ProductModel

class ProductsAllAdapter(private val listener: HomeProductListener) :
    ListAdapter<ProductModel,ProductsAllAdapter.ProductsAllVH>(ProductsAllDiffCallback()) {
    inner class ProductsAllVH(itemView: View) : ViewHolder(itemView){
       val binding = AllProductsItemBinding.bind(itemView)
    }
    class ProductsAllDiffCallback : DiffUtil.ItemCallback<ProductModel>(){
        override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsAllVH {
        return ProductsAllVH(LayoutInflater.from(parent.context).inflate(
            R.layout.all_products_item,parent,false
        ))
    }

    override fun onBindViewHolder(holder: ProductsAllVH, position: Int) {
        val products = getItem(position)
        holder.binding.allProductTxt.text = products.name
        holder.binding.allProductPrice.text = "${products.price} AED"
        Glide.with(holder.itemView.context).load(products.imageUrl[0]).into(holder.binding.allProductImg)

        holder.itemView.setOnClickListener {
            listener.onProductClicked(products)
        }
    }
}