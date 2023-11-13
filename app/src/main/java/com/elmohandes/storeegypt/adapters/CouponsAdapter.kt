package com.elmohandes.storeegypt.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.elmohandes.storeegypt.R
import com.elmohandes.storeegypt.databinding.CouponItemBinding
import com.elmohandes.storeegypt.models.CouponsModel

class CouponsAdapter : ListAdapter<CouponsModel,CouponsAdapter.CouponsVH>(CouponsDiffCallback()) {
    inner class CouponsVH(itemView: View) : ViewHolder(itemView){
        val binding = CouponItemBinding.bind(itemView)
    }

    class CouponsDiffCallback : DiffUtil.ItemCallback<CouponsModel>(){
        override fun areItemsTheSame(oldItem: CouponsModel, newItem: CouponsModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CouponsModel, newItem: CouponsModel): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CouponsVH {
        return CouponsVH(LayoutInflater.from(parent.context).inflate(
            R.layout.coupon_item,parent,false
        ))
    }

    override fun onBindViewHolder(holder: CouponsVH, position: Int) {
        val coupons = getItem(position)
        Glide.with(holder.itemView.context).load(coupons.image).into(holder.binding.couponImg)
        holder.binding.couponStoreName.text = coupons.storeName
        holder.binding.couponCode.text = coupons.couponCode
        holder.binding.couponCountries.text = coupons.countries
    }
}