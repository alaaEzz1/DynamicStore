package com.elmohandes.storeegypt.repository

import com.elmohandes.storeegypt.models.CollectionsModel

class CollectionsRepo {
    //firebase and room collection implementation
    //also we can add caching and other topics here
    suspend fun getCollections() : List<CollectionsModel>{
        return getNewCollections()
    }

    private fun getNewCollections(): List<CollectionsModel> {
        // Implement the logic to fetch categories from Firebase or Room here
        return listOf(
            CollectionsModel(
                "1","All",
                "https://i.pinimg.com/736x/ee/b8/8e/eeb88ea1315f67be113f3b2b4c7c37a5.jpg"
            ),
            CollectionsModel(
                "2","Shoes",
                "https://i.pinimg.com/564x/16/b6/17/16b6170c173b668d95950678da65f5c7.jpg"
            ),
            CollectionsModel(
                "3","Men Cloths",
                "https://i.pinimg.com/564x/88/07/c2/8807c2aa5f999388b648f2971ec65907.jpg"
            ),
            CollectionsModel(
                "4","Perfumes",
                "https://i.pinimg.com/564x/6e/f0/fb/6ef0fbf4b720940d18683bbd577161d1.jpg"
            ),
            CollectionsModel(
                "5","Accessories",
                "https://i.pinimg.com/564x/57/eb/be/57ebbed4d153721842d20c53c02005e7.jpg"
            ),
            CollectionsModel(
                "6","Sports",
                "https://i.pinimg.com/564x/c3/ad/06/c3ad065e0b14a40a6d88aab9cbdd107a.jpg"
            ),
            CollectionsModel(
                "7","Electronics",
                "https://i.pinimg.com/564x/b0/c6/fa/b0c6fad7554f54eae54a329bcb475950.jpg"
            ),
        )

    }

}