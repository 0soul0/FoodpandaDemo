package com.sideproject.foodpandafake.repo

import retrofit2.Response
import retrofit2.http.GET

interface FoodPanda {
    @GET("city/taipei-city")
    suspend fun getStringOfWebContent():Response<String>

}