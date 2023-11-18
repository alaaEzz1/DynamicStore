package com.elmohandes.storeegypt.models

data class ProductModel(
    val id: String,
    val name: String,
    val category: String,
    val description: String,
    val productLink:String,
    val imageUrl: List<String>,
    val price: Double,
    val rate: Double,
    val isBestSeller: Boolean
)
