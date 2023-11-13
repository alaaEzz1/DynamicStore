package com.elmohandes.storeegypt.repository

import com.elmohandes.storeegypt.models.CouponsModel

class CouponRepo {

    private val coupons = listOf<CouponsModel>(
        CouponsModel(
            1,"SuperMart","SAVE10"
            ,"https://i.pinimg.com/564x/a7/13/e0/a713e06f9d12c6cc027ae1a521e8c1ec.jpg",
            "United States, Germany, Canada",
            true,
        ),
        CouponsModel(
            2,"FashionWorld","FASHION20"
            ,"https://i.pinimg.com/736x/29/2a/92/292a9233aefaa8efecf529a25ce5a580.jpg",
            "Brazil, Russia, UAE, Oman",
            true,
        ),
        CouponsModel(
            3,"ElectroDeal","ELECTRO50"
            ,"https://i.pinimg.com/736x/6a/94/9c/6a949c7dc29c40bb6b0e5de7769293cb.jpg",
            "Italia, Egypt, Algeria",
            true,
        ),
        CouponsModel(
            4,"FoodDelight","DEALS15"
            ,"https://i.pinimg.com/736x/3b/4c/cd/3b4ccda8365d972621181f73f1295c70.jpg",
            "Saudia Arabia, UAE, Qatar",
            true,
        ),
        CouponsModel(
            5,"OutdoorGear","OUTDOOR10"
            ,"https://i.pinimg.com/736x/6a/94/9c/6a949c7dc29c40bb6b0e5de7769293cb.jpg",
            "United States, Turkish, Canada",
            true,
        ),
    )

    fun getCoupons(): List<CouponsModel>{
        return coupons
    }

}