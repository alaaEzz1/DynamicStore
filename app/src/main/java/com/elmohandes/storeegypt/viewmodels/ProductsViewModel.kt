package com.elmohandes.storeegypt.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elmohandes.storeegypt.models.CouponsModel
import com.elmohandes.storeegypt.models.ProductModel
import com.elmohandes.storeegypt.repository.CouponRepo
import com.elmohandes.storeegypt.repository.ProductsRepo
import kotlinx.coroutines.launch

class ProductsViewModel :ViewModel() {
    private val productsRepo = ProductsRepo()
    private val couponRepo = CouponRepo()

    //product get and insert data
    private val _products = MutableLiveData<List<ProductModel>>()
    val products : LiveData<List<ProductModel>> get() = _products

    //coupons get and insert data
    private val _coupons = MutableLiveData<List<CouponsModel>>()
    val coupons : LiveData<List<CouponsModel>> get() = _coupons

    fun loadProducts() = viewModelScope.launch {
        _products.value = productsRepo.getProducts()
    }

    fun loadCoupon() = viewModelScope.launch {
        _coupons.value = couponRepo.getCoupons()
    }
}