package com.elmohandes.storeegypt.models

data class UserModel(
    val id:String,
    val fullName: String,
    val email: String,
    val age:Int,
    val address: String,
    val mobilePhone: String,
    val userImage: String,
    val password: String,
)
