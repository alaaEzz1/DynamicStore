package com.elmohandes.storeegypt.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elmohandes.storeegypt.R
import com.elmohandes.storeegypt.adapters.BestSellerAdapter
import com.elmohandes.storeegypt.adapters.HomeCollectionsAdapter
import com.elmohandes.storeegypt.adapters.HomeCouponAdapter
import com.elmohandes.storeegypt.adapters.HomeSliderAdapter
import com.elmohandes.storeegypt.adapters.listeners.HomeProductListener
import com.elmohandes.storeegypt.databinding.FragmentHomeBinding
import com.elmohandes.storeegypt.models.HomeSliderModel
import com.elmohandes.storeegypt.models.ProductModel
import com.elmohandes.storeegypt.utils.AdMobAdsManager
import com.elmohandes.storeegypt.viewmodels.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment(), HomeProductListener {
    // TODO: Rename and change types of parameters
    /*
    1- linear search and logo == true -> but still app has no logo
    2- ads slider == true -> the image must be changed or get it from user interface
    3- category rv == true
    4- best sell products == true
    5- banner ad == true
    6- best coupons == true
    7- Promotions and Discounts: == false and leave it to last because it depends on other models
     */


    private lateinit var binding: FragmentHomeBinding
    private lateinit var images : List<HomeSliderModel>
    private lateinit var sliderAdapter: HomeSliderAdapter
    private lateinit var sliderTimer: Timer
    private lateinit var collectionsAdapter: HomeCollectionsAdapter
    private lateinit var viewModel: HomeViewModel
    private lateinit var bestSellerAdapter: BestSellerAdapter
    private lateinit var couponAdapter: HomeCouponAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        binding = FragmentHomeBinding.bind(view)

        //initiate view model
        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]

        sliderTimer = Timer()

        //lists
        images = listOf(
            HomeSliderModel(
                1,
                "https://i.pinimg.com/564x/fc/bd/d5/fcbdd5f7ebfe2b66e0cc55c1d55f99f3.jpg",
            ),
            HomeSliderModel(
                2,
                "https://i.pinimg.com/564x/94/98/20/9498203b828a2cdf2cc718a6533e3374.jpg",
            ),
            HomeSliderModel(
                3,
                "https://i.pinimg.com/736x/b1/d5/ab/b1d5abdec83cab1898ff74ce1480551e.jpg",
            ),
            HomeSliderModel(
                4,
                "https://i.pinimg.com/564x/40/c0/15/40c0155583f27cc037b8ca6e55e39783.jpg",
            ),
            HomeSliderModel(
                5,
                "https://i.pinimg.com/564x/59/59/a4/5959a42066e67d793eb7ed0463cf1db3.jpg",
            )
        )

        //adapters
        sliderAdapter = HomeSliderAdapter(images)
        collectionsAdapter = HomeCollectionsAdapter()
        bestSellerAdapter = BestSellerAdapter(this)
        couponAdapter = HomeCouponAdapter()

        //inflate slider
        binding.homeSliderTopBanner.adapter = sliderAdapter
        setupAutoImageSlider()

        //inflate category
        binding.homeCollectionsRv.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                RecyclerView.HORIZONTAL,false)
            adapter = collectionsAdapter
        }
        getCollectionsFromViewModel()

        //inflate best sell products
        binding.homeBestSellerRv.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                RecyclerView.HORIZONTAL,false)
            adapter = bestSellerAdapter
        }
        getBestSellerFromViewModel()

        //inflate admob banner
        val uId = "ca-app-pub-3940256099942544/6300978111"
        AdMobAdsManager.setupBannerAd(requireContext(), uId, binding.homeBannerAdmob)

        //inflate best coupons
        binding.homeBestCouponsRv.apply {
            layoutManager = LinearLayoutManager(requireContext()
                ,RecyclerView.HORIZONTAL,false)
            adapter = couponAdapter
        }
        getCouponsFromViewModel()

        return view
    }

    private fun getCouponsFromViewModel() {
        viewModel.coupons.observe(requireActivity()){
            couponAdapter.submitList(it)
        }
        viewModel.loadCoupons()
    }

    private fun getBestSellerFromViewModel() {
        viewModel.bestSeller.observe(requireActivity()){
            bestSellerAdapter.submitList(it)
        }
        viewModel.loadBestSellerProducts()
    }

    private fun getCollectionsFromViewModel() {
        //observe collections view model or add data to adapter
        viewModel.collections.observe(requireActivity()) {
            collectionsAdapter.submitList(it)
        }

        //get data from view model
        viewModel.loadCollections()
    }

    private fun setupAutoImageSlider() {
        val handler = Handler(Looper.getMainLooper())
        val update = Runnable {
            val currentItem = binding.homeSliderTopBanner.currentItem
            val nextItem = if(currentItem==sliderAdapter.itemCount - 1) 0 else currentItem + 1
            binding.homeSliderTopBanner.setCurrentItem(nextItem,true)
        }

        sliderTimer.schedule(object : TimerTask(){
            override fun run() {
                handler.post(update)
            }

        },500,5000)
    }

    override fun onProductClicked(productModel: ProductModel) {
        val bundle = bundleOf(
            "home_product_title" to  productModel.name,
            "home_product_desc" to  productModel.description,
            "home_product_price" to  productModel.price,
            "home_product_image" to  ArrayList(productModel.imageUrl),
            "home_product_rate" to  productModel.rate,
        )
        Navigation.findNavController(requireView()).navigate(R.id.productDetailsFragment,bundle)
    }
}