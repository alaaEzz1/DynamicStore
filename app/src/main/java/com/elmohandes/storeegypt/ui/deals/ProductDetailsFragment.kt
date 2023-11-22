package com.elmohandes.storeegypt.ui.deals

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.elmohandes.storeegypt.R
import com.elmohandes.storeegypt.adapters.ProductSliderAdapter
import com.elmohandes.storeegypt.databinding.FragmentProductDetailsBinding
import java.util.Timer
import java.util.TimerTask

class ProductDetailsFragment : Fragment() {

    private lateinit var binding: FragmentProductDetailsBinding
    private lateinit var sliderAdapter: ProductSliderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_product_details, container, false)
        binding = FragmentProductDetailsBinding.bind(view)

        val homeProductTitle = arguments?.getString("home_product_title")
        val homeProductDesc = arguments?.getString("home_product_desc")
        val homeProductPrice = arguments?.getDouble("home_product_price")
        val homeProductImage = arguments?.getStringArrayList("home_product_image")
        val homeProductRate = arguments?.getString("home_product_rate")
        val productTitle = arguments?.getString("product_title")
        val productDesc = arguments?.getString("product_desc")
        val productPrice = arguments?.getDouble("product_price")
        val productImage = arguments?.getStringArrayList("product_image")
        val productRate = arguments?.getString("product_rate")

        getProductData(productTitle,productDesc,productPrice,productRate,productImage)
        getHomeData(homeProductTitle,homeProductDesc,homeProductPrice,homeProductRate,homeProductImage)

        return view
    }

    private fun getHomeData(homeProductTitle: String?, homeProductDesc: String?, homeProductPrice: Double?,
                            homeProductRate: String?, homeProductImage: ArrayList<String>?) {
        //Toast.makeText(context, "${homeProductImage?.get(0)}", Toast.LENGTH_SHORT).show()

        if (homeProductPrice != null){
            binding.productDetailsPrice.text = "$homeProductPrice AED"
        }
        if (homeProductTitle!= null){
            binding.productDetailsTitle.text = homeProductTitle
        }
        if (homeProductDesc!= null){
            binding.productDetailsDesc.text = homeProductDesc
        }
        if (homeProductImage != null){
            sliderAdapter = ProductSliderAdapter(homeProductImage)
            binding.productDetailsPager.adapter = sliderAdapter
            setupSliderTimer()
        }

    }

    private fun setupSliderTimer() {
        val timer = Timer()
        val handler = Handler(Looper.getMainLooper())
        val update = Runnable {
            val currentItem = binding.productDetailsPager.currentItem
            val nextItem = if (currentItem == sliderAdapter.itemCount - 1) 0 else currentItem + 1
            binding.productDetailsPager.setCurrentItem(nextItem,true)
        }
        timer.schedule(object : TimerTask(){
            override fun run() {
                handler.post(update)
            }

        },500,5000)
    }

    private fun getProductData(
        productTitle: String?, productDesc: String?, productPrice: Double?,
        productRate: String?, productImage: ArrayList<String>?) {

        if (productDesc!= null){
            binding.productDetailsDesc.text = productDesc
        }
        if (productTitle!= null){
            binding.productDetailsTitle.text = productTitle
        }
        if (productPrice!= null){
            binding.productDetailsPrice.text = "$productPrice AED"
        }
        if (productImage != null){
            sliderAdapter = ProductSliderAdapter(productImage)
            binding.productDetailsPager.adapter = sliderAdapter
            setupSliderTimer()
        }
    }
}