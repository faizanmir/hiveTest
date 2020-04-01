package com.zomatoapi.retrofit

import com.zomatoapi.models.Root
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface Api {
    companion object{
        var BASE_URL :String = "https://developers.zomato.com/api/v2.1/"
    }
    @Headers("user-key:130028ef789808b924829d0d70fb6ae7")
    @GET("search")
    suspend fun getData(@Query("q") item:String) : Root
}