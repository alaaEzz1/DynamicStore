package com.elmohandes.storeegypt.repository

import com.elmohandes.storeegypt.models.CouponsModel

class CouponRepo {

    private val coupons = listOf<CouponsModel>(
        CouponsModel(
            1,"SuperMart","SAVE10"
            ,"",
            "United States, Germany, Canada",
            true,
            "2023-08-01",
            "2023-08-30"
        ),
        CouponsModel(
            2,"FashionWorld","FASHION20"
            ,"",
            "Brazil, Russia, UAE, Oman",
            true,
            "2023-07-25",
            "2023-09-30"
        ),
        CouponsModel(
            3,"ElectroDeal","ELECTRO50"
            ,"",
            "Italia, Egypt, Algeria",
            true,
            "2023-08-01",
            "2023-08-30"
        ),
        CouponsModel(
            4,"FoodDelight","DEALS15"
            ,"",
            "Saudia Arabia, UAE, Qatar",
            true,
            "2023-08-15",
            "2023-08-30"
        ),
        CouponsModel(
            5,"OutdoorGear","OUTDOOR10"
            ,"",
            "United States, Turkish, Canada",
            true,
            "2023-08-01",
            "2023-08-15"
        ),
    )

    fun getCoupons(): List<CouponsModel>{
        return coupons
    }

}