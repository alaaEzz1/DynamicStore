package com.elmohandes.storeegypt.ui.deals

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.elmohandes.storeegypt.R
import com.elmohandes.storeegypt.adapters.DealsPagerAdapter
import com.elmohandes.storeegypt.databinding.FragmentDealsBinding
import com.google.android.material.tabs.TabLayoutMediator


class DealsFragment : Fragment() {

    private lateinit var binding: FragmentDealsBinding
    private lateinit var pagerAdapter: DealsPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_deals, container, false)
        binding = FragmentDealsBinding.bind(view)

        pagerAdapter = DealsPagerAdapter(requireActivity().supportFragmentManager
            ,requireActivity().lifecycle)
        binding.dealsViewPager.adapter =pagerAdapter
        TabLayoutMediator(binding.dealsTabLayout
            ,binding.dealsViewPager){ tab,position->
            tab.text = when(position){
                0 -> "Products"
                1 -> "Coupons"
                else -> ""
            }
        }.attach()


        return view
    }
}