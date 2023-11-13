package com.elmohandes.storeegypt.ui.deals

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.elmohandes.storeegypt.R
import com.elmohandes.storeegypt.adapters.CouponsAdapter
import com.elmohandes.storeegypt.databinding.FragmentCouponsBinding
import com.elmohandes.storeegypt.utils.AdMobAdsManager
import com.elmohandes.storeegypt.viewmodels.ProductsViewModel


class CouponsFragment : Fragment() {

    private lateinit var binding: FragmentCouponsBinding
    private lateinit var productsViewModel: ProductsViewModel
    private lateinit var couponsAdapter: CouponsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_coupons, container, false)
        binding = FragmentCouponsBinding.bind(view)

        //viewModels
        productsViewModel = ViewModelProvider(requireActivity())[ProductsViewModel::class.java]

        //adapters
        couponsAdapter = CouponsAdapter()

        binding.couponsRv.apply {
            adapter = couponsAdapter
            layoutManager = GridLayoutManager(requireContext(),2)
        }

        //inflate admob banner
        val uId = "ca-app-pub-3940256099942544/6300978111"
        AdMobAdsManager.setupBannerAd(requireContext(), uId, binding.couponsBannerAdmob)

        loadCoupons()

        return view
    }

    private fun loadCoupons() {
        productsViewModel.coupons.observe(requireActivity()){
            couponsAdapter.submitList(it)
        }
        productsViewModel.loadCoupon()
    }
}