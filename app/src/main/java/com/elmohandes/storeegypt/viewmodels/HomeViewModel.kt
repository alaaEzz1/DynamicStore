package com.elmohandes.storeegypt.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elmohandes.storeegypt.models.CollectionsModel
import com.elmohandes.storeegypt.models.CouponsModel
import com.elmohandes.storeegypt.models.ProductModel
import com.elmohandes.storeegypt.repository.CollectionsRepo
import com.elmohandes.storeegypt.repository.CouponRepo
import com.elmohandes.storeegypt.repository.ProductsRepo
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    //general view model for home screen
    private val collectionsRepo = CollectionsRepo()
    private val productsRepo = ProductsRepo()
    private val couponRepo = CouponRepo()
    // LiveData to hold the list of Collections
    //you wil insert from MutableLiveData and get from LiveData
    private val _collections = MutableLiveData<List<CollectionsModel>>()
    val collections : LiveData<List<CollectionsModel>> get() = _collections

    private val _bestSeller = MutableLiveData<List<ProductModel>>()
    val bestSeller : LiveData<List<ProductModel>> get() = _bestSeller

    private val _coupons = MutableLiveData<List<CouponsModel>>()
    val coupons: LiveData<List<CouponsModel>> get() = _coupons

    fun loadCollections() = viewModelScope.launch {
        val list = collectionsRepo.getCollections()
        _collections.value = list
    }

    fun loadBestSellerProducts() = viewModelScope.launch {
        _bestSeller.value = productsRepo.getBestSellerProducts()
    }

    fun loadCoupons() = viewModelScope.launch {
        _coupons.value = couponRepo.getCoupons()
    }

}