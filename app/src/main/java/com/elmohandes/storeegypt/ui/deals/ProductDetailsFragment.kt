package com.elmohandes.storeegypt.ui.deals

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
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
        val homeProductRate = arguments?.getDouble("home_product_rate").toString()
        val homeProductLink = arguments?.getString("home_product_link")
        val productTitle = arguments?.getString("product_title")
        val productDesc = arguments?.getString("product_desc")
        val productPrice = arguments?.getDouble("product_price")
        val productImage = arguments?.getStringArrayList("product_image")
        val productRate = arguments?.getDouble("product_rate").toString()
        val productLink = arguments?.getString("product_link")

        getProductData(productTitle,productDesc,productPrice,productRate,productImage,productLink)
        getHomeData(homeProductTitle,homeProductDesc,homeProductPrice,homeProductRate,homeProductImage,homeProductLink)

        return view
    }

    private fun getHomeData(
        homeProductTitle: String?, homeProductDesc: String?, homeProductPrice: Double?,
        homeProductRate: String?, homeProductImage: ArrayList<String>?, homeProductLink: String?
    ) {
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
        if (homeProductLink!= null){
            binding.productDetailsAddToCart.setOnClickListener {
                openLinkInBrowser(homeProductLink)
            }
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
        productRate: String?, productImage: ArrayList<String>?, productLink: String?
    ) {

        if (productDesc!= null){
            binding.productDetailsDesc.text = productDesc
        }
        if (productTitle!= null){
            binding.productDetailsTitle.text = productTitle
        }
        if (productPrice!= null){
            binding.productDetailsPrice.text = "$productPrice AED"
        }

        if (productLink != null){
            binding.productDetailsAddToCart.setOnClickListener {
                openLinkInBrowser(productLink)
            }
        }

        if (productImage != null){
            sliderAdapter = ProductSliderAdapter(productImage)
            binding.productDetailsPager.adapter = sliderAdapter
            setupSliderTimer()
        }
    }

    // Function to open a link in the browser
    private fun openLinkInBrowser(link: String) {
        // Parse the link into a Uri
        val uri = Uri.parse(link)

        // Create an intent with the ACTION_VIEW action
        val intent = Intent(Intent.ACTION_VIEW, uri)

        // Check if there are activities that can handle this intent
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            // Start the intent, and the user will be prompted to choose a browser
            try {
                Log.d("website-link",link)
                val chooser = Intent.createChooser(intent, "Open with...")
                requireActivity().startActivity(chooser)
            }catch (ex: Exception){
                Log.d("website-url", ex.message.toString())
            }
        } else {
            // Handle the case where there is no browser installed
            Toast.makeText(
                requireContext(), "No browser installed. Please install a browser to open the link.",
                Toast.LENGTH_LONG).show()
            // or use a WebView within your app
        }
    }

}