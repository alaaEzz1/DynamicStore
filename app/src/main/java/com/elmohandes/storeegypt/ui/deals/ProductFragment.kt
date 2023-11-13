package com.elmohandes.storeegypt.ui.deals

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elmohandes.storeegypt.R
import com.elmohandes.storeegypt.adapters.ProductCollectionsAdapter
import com.elmohandes.storeegypt.adapters.ProductsAllAdapter
import com.elmohandes.storeegypt.adapters.listeners.HomeProductListener
import com.elmohandes.storeegypt.databinding.FragmentHomeBinding
import com.elmohandes.storeegypt.databinding.FragmentProductBinding
import com.elmohandes.storeegypt.models.CollectionsModel
import com.elmohandes.storeegypt.models.ProductModel
import com.elmohandes.storeegypt.viewmodels.HomeViewModel
import com.elmohandes.storeegypt.viewmodels.ProductsViewModel


class ProductFragment : Fragment(), HomeProductListener {
    private lateinit var binding: FragmentProductBinding
    private lateinit var homeViewModel : HomeViewModel
    private lateinit var productsViewModel: ProductsViewModel
    private lateinit var allAdapter: ProductsAllAdapter
    private lateinit var collectionsAdapter: ProductCollectionsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_product, container, false)

        binding = FragmentProductBinding.bind(view)

        //adapters
        collectionsAdapter = ProductCollectionsAdapter()
        allAdapter = ProductsAllAdapter(this)

        //viewModels
        homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        productsViewModel = ViewModelProvider(requireActivity())[ProductsViewModel::class.java]

        binding.productCollectionRv.apply {
            adapter = collectionsAdapter
            layoutManager = LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
        }
        getCollectionsFromViewModel()

        binding.productAllProductsRv.apply {
            adapter = allAdapter
            layoutManager = GridLayoutManager(requireContext(),2)
        }
        getAllProductsFromViewModel()

        return view
    }

    private fun getAllProductsFromViewModel() {
        productsViewModel.products.observe(requireActivity()){
            allAdapter.submitList(it)
        }
        productsViewModel.loadProducts()
    }

    private fun getCollectionsFromViewModel() {
        homeViewModel.collections.observe(requireActivity()){
            collectionsAdapter.submitList(it)
        }
        homeViewModel.loadCollections()
    }

    override fun onProductClicked(productModel: ProductModel) {
        val bundle = bundleOf(
            "product_title" to  productModel.name,
            "product_desc" to  productModel.description,
            "product_price" to  productModel.price,
            "product_image" to  ArrayList(productModel.imageUrl),
            "product_rate" to  productModel.rate,
        )
        Navigation.findNavController(requireView()).navigate(R.id.productDetailsFragment,bundle)
    }
}