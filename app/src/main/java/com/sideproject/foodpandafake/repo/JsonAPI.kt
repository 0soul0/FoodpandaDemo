package com.sideproject.foodpandafake.repo

import com.sideproject.foodpandafake.model.Posts
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

//no use
interface JsonAPI {

    @GET("posts/{id}")
    suspend fun getPostById(@Path("id") id: Int): Response<Posts>?

}