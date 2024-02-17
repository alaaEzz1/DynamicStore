package com.elmohandes.storeegypt.database

import android.util.Log
import com.elmohandes.storeegypt.models.CollectionsModel
import com.elmohandes.storeegypt.models.ProductModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.tasks.await

class RetrieveProductsFromDB {
    private val firestore = FirebaseFirestore.getInstance().collection("products")

    suspend fun getProducts() : List<ProductModel> = CoroutineScope(Dispatchers.IO).async{
        val products = mutableListOf<ProductModel>()
        val images = mutableListOf<String>()
        try {
            val snapshot = firestore.get().await()
            snapshot.forEach {
                val id = it.data.getValue("id").toString()
                val name = it.data.getValue("name").toString()
                val category = it.data.getValue("category").toString()
                val description = it.data.getValue("description").toString()
                val productLink = it.data.getValue("productLink").toString()
                val imageUrl = it.data.getValue("imageUrl") as? List<String>
                Log.d("images", "${listOf(imageUrl).size}")
                val price = it.data.getValue("price").toString().toDouble()
                val rate = it.data.getValue("rate").toString().toDouble()
                val isBestSeller = it.data.getValue("bestSeller").toString().toBoolean()
                // Check for null and handle it accordingly
                if (imageUrl != null) {
                    // Now you can use imageUrlList, which is a List<String>
                    // If you need to add all URLs to your list
                    images.forEach { imageUrls ->
                        images.add(imageUrls)
                    }
                }

                val productModel = ProductModel(id,name,category,description,productLink,
                    imageUrl!!,price,rate,isBestSeller)
                products.add(productModel)

                Log.d("products","${products.size}")
            }
        }catch (ex: Exception){
            Log.e("products-list-error",ex.message.toString())
        }
        return@async products
    }.await()
}