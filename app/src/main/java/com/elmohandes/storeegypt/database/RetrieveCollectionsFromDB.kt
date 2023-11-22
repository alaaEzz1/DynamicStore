package com.elmohandes.storeegypt.database

import com.elmohandes.storeegypt.models.CollectionsModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.tasks.await

class RetrieveCollectionsFromDB() {

    private val firestore = FirebaseFirestore.getInstance().collection("collections")

    suspend fun getCollections() : List<CollectionsModel> = CoroutineScope(Dispatchers.IO).async{
        val collections = mutableListOf<CollectionsModel>()
        val snapshot = firestore.get().await()
        snapshot.forEach {
            val collectionId = it.data.getValue("id").toString()
            val collectionName = it.data.getValue("name").toString()
            val collectionImageUrl = it.data.getValue("imageUrl").toString()
            val collectionsModel = CollectionsModel(collectionId,collectionName,collectionImageUrl)
            collections.add(collectionsModel)
        }
        return@async collections
    }.await()

}