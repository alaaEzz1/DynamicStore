package com.elmohandes.storeegypt.adapters.listeners

import com.elmohandes.storeegypt.models.ProductModel

interface HomeProductListener {
    fun onProductClicked(productModel: ProductModel)
}