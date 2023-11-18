package com.elmohandes.storeegypt.database

import android.app.Activity
import android.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import com.elmohandes.storeegypt.R
import com.elmohandes.storeegypt.databinding.AdminAddCollectionsBinding
import com.elmohandes.storeegypt.databinding.AdminAddProductsBinding
import com.elmohandes.storeegypt.models.CollectionsModel
import com.elmohandes.storeegypt.models.ProductModel
import com.elmohandes.storeegypt.utils.CustomProgressDialog
import com.google.firebase.firestore.FirebaseFirestore

class InsertAdminDatabase(private val activity: Activity,private val firestore: FirebaseFirestore) {
    private lateinit var dialog: AlertDialog
    private val customProgressDialog = CustomProgressDialog(activity)

    fun addCollectionsToDatabase(){
        val builder = AlertDialog.Builder(activity)
        val view = activity.layoutInflater.inflate(
            R.layout.admin_add_collections,null
        )
        val binding = AdminAddCollectionsBinding.bind(view)

        binding.adminCollectionAdd.setOnClickListener {
            customProgressDialog.startLoading()
            val id = binding.adminCollectionId.text.toString()
            val collection = binding.adminCollectionName.text.toString()
            val url = binding.adminCollectionUrl.text.toString()
            val collections = CollectionsModel(id,collection, url)
            Log.d("id",id)
            Log.d("collection",collection)
            Log.d("url",url)

            firestore.collection("collections").add(collections)
                .addOnSuccessListener {
                    //DocumentSnapshot added with ID: it.id
                    val collectionId = it.id
                    //update the ID field in the added category
                    firestore.collection("collections").document(collectionId)
                        .update("id",collectionId).addOnSuccessListener {
                            Toast.makeText(activity, "Category Added Successfully", Toast.LENGTH_SHORT).show()
                            customProgressDialog.dismissDialog()
                        }.addOnFailureListener {
                            Toast.makeText(activity, "failed", Toast.LENGTH_SHORT).show()
                            customProgressDialog.dismissDialog()
                        }
                }
        }

        builder.setView(binding.root)
        dialog = builder.create()
        dialog.show()
    }

    fun addProductsToDatabase(){
        val builder = AlertDialog.Builder(activity)
        val view = LayoutInflater.from(activity).inflate(R.layout.admin_add_products, null)
        val binding = AdminAddProductsBinding.bind(view)

        binding.adminProductAdd.setOnClickListener {
            prepareData(binding)
        }

        builder.setView(binding.root)
        dialog = builder.create()
        dialog.show()
    }

    private fun prepareData(binding: AdminAddProductsBinding) {
        customProgressDialog.startLoading()
        val id = binding.adminProductId.text.toString()
        val title = binding.adminProductName.text.toString()
        val category = binding.adminProductCategory.text.toString()
        val desc = binding.adminProductDesc.text.toString()
        val link = binding.adminProductLink.text.toString()
        val price = binding.adminProductPrice.text.toString().toDouble()
        val url = binding.adminProductUrl.text.toString()
        val url2 = binding.adminProductUrl2.text.toString()
        val url3 = binding.adminProductUrl3.text.toString()
        val url4 = binding.adminProductUrl4.text.toString()
        val url5 = binding.adminProductUrl5.text.toString()

        var urlList = listOf<String>()
        if (url != ""){
           urlList = listOf(url)
            if (url2 != ""){
                urlList = listOf(url, url2)
                if (url3 != ""){
                    urlList = listOf(url,url2,url3)
                    if (url4 != ""){
                        urlList = listOf(url,url2,url3,url4)
                        if (url5 != ""){
                            urlList = listOf(url,url2,url3,url4,url5)
                        }
                    }
                }
            }
        }

        val productModel = ProductModel(id,title,category,desc,link,urlList,price,5.0,true)
        firestore.collection("products").add(productModel)
            .addOnSuccessListener {
                val productId = it.id
                firestore.collection("products").document(productId)
                    .update("id",productId).addOnSuccessListener {
                        Toast.makeText(activity, "Product Added Successfully", Toast.LENGTH_SHORT).show()
                        customProgressDialog.dismissDialog()
                    }.addOnFailureListener {
                        Toast.makeText(activity, "error", Toast.LENGTH_SHORT).show()
                        customProgressDialog.dismissDialog()
                    }
            }

    }
}