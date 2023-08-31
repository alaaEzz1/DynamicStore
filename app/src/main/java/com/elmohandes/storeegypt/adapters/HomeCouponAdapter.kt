package com.elmohandes.storeegypt.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.elmohandes.storeegypt.R
import com.elmohandes.storeegypt.databinding.HomeCouponItemBinding
import com.elmohandes.storeegypt.models.CouponsModel

class HomeCouponAdapter() : ListAdapter<CouponsModel,
        HomeCouponAdapter.HomeCouponVH>(HomeCouponDiffCallback()) {

    inner class HomeCouponVH(itemView: View) : ViewHolder(itemView){
        val binding = HomeCouponItemBinding.bind(itemView)
    }

    class HomeCouponDiffCallback : DiffUtil.ItemCallback<CouponsModel>(){
        override fun areItemsTheSame(oldItem: CouponsModel, newItem: CouponsModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CouponsModel, newItem: CouponsModel): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCouponVH {
        return HomeCouponVH(LayoutInflater.from(parent.context).inflate(
            R.layout.home_coupon_item,parent,false
        ))
    }

    override fun onBindViewHolder(holder: HomeCouponVH, position: Int) {
        val coupons = getItem(position)
        Glide.with(holder.itemView.context).load(coupons.image)
            .placeholder(R.drawable.store_egypt_logo).into(holder.binding.homeCouponStoreImage)
        holder.binding.homeCouponStoreCode.text = coupons.couponCode
        holder.binding.homeCouponStoreName.text = coupons.storeName
    }
}