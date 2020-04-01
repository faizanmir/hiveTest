package com.zomatoapi.repositories

import android.content.Context
import com.zomatoapi.retrofit.Api
import com.zomatoapi.retrofit.RetrofitSingleton
import com.zomatoapi.models.Restaurant_
import retrofit2.Retrofit
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.set

class Repository(var context: Context)  {




     var retrofit: Retrofit = RetrofitSingleton.retrofit
    var api: Api
    var resCount =  0
    var cuisineRestaurantMap:HashMap<String,ArrayList<Restaurant_>> = HashMap()
    var cuisineList :ArrayList<String> = ArrayList()
    val finalList:MutableList<Any> = mutableListOf<Any>()
    init {
        api  = retrofit.create(Api::class.java)
    }
     suspend  fun sendDataToRetrofit(query: String):List<Any?>{
         finalList.clear()
         cuisineList.clear()
         cuisineRestaurantMap.clear()

         val root = api.getData(query)
         resCount =  root.resultsFound
         root.restaurants?.forEach {
             if(!cuisineList.contains(it.restaurant.cuisines)) {
                 cuisineList.add(it.restaurant.cuisines)
             }

         }



         cuisineList.forEach {
             cuisineRestaurantMap[it] = ArrayList()
         }




         for (cuisine in cuisineList){
             val restaurantForCuisine = ArrayList<Restaurant_>()
             for (restaurant in root.restaurants){
                 if (restaurant.restaurant.cuisines!! == cuisine){
                     restaurantForCuisine.add(restaurant.restaurant)
                 }
             }
             cuisineRestaurantMap[cuisine] = restaurantForCuisine
         }





         cuisineRestaurantMap.keys.forEach { cuisine ->
             finalList.add(cuisine)

             cuisineRestaurantMap[cuisine]?.forEach {restaurant->
                 finalList.add(restaurant)
             }
         }


         return finalList


     }


    fun getCount() = resCount




}