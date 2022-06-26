package com.sideproject.foodpandafake.repo

import com.sideproject.foodpandafake.model.Posts
import retrofit2.Response
import java.lang.Exception

//no use
class JsonAPIRepo(private val jsonAPI: JsonAPI) {

    suspend fun get(id: Int): Response<Posts> {
        val res = jsonAPI.getPostById(id)
        if (res?.isSuccessful == true)
            return res
        else
            throw Exception()
    }
}