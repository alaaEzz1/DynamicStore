package com.elmohandes.storeegypt.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.elmohandes.storeegypt.ui.deals.CouponsFragment
import com.elmohandes.storeegypt.ui.deals.ProductFragment

class DealsPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ProductFragment()
            1 -> CouponsFragment()
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }
}
