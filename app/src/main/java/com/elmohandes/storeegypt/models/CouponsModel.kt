package com.elmohandes.storeegypt.models

data class CouponsModel(
    val id: Int,
    val storeName: String,
    val couponCode: String,
    val image: String,
    val countries: String,
    val isAvailable: Boolean,
    val dateFrom: String,
    val dateTo: String,
)