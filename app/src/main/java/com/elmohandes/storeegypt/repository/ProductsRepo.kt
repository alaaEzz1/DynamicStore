package com.elmohandes.storeegypt.repository

import com.elmohandes.storeegypt.models.ProductModel

class ProductsRepo {

    // Dummy product data for illustration
    private val products = listOf(
        ProductModel("1", "Product 1",
            "Description of Product 1",
            listOf("https://i.pinimg.com/564x/e9/51/3e/e9513e701feda81934cdc45dd9332f05.jpg"),
            29.99, 4.5, true),
        ProductModel("2", "Product 2",
            "Description of Product 2",
            listOf("https://i.pinimg.com/564x/fd/c6/17/fdc61770af4730410b59a22b256125e1.jpg"),
            19.99, 3.8, false),
        ProductModel("3", "Product 3",
            "Description of Product 3",
            listOf("https://i.pinimg.com/564x/1a/cf/58/1acf581959de48a64613a9efefa9129b.jpg"),
            49.99, 4.2, true),
        ProductModel("4", "Product 4",
            "Description of Product 4",
            listOf("https://i.pinimg.com/564x/cf/2b/4c/cf2b4ce024e2a2079b021a8607fda4b3.jpg"),
            39.99, 4.7, true)
    )

    fun getProducts() : List<ProductModel>{
        return products
    }

    //filter is best seller from products
    fun getBestSellerProducts() : List<ProductModel>{
        return products.filter { it.isBestSeller }
    }

}