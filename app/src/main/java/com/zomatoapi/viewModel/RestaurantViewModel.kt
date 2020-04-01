package com.zomatoapi.viewModel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.zomatoapi.models.Root
import com.zomatoapi.repositories.Repository
import kotlinx.coroutines.launch

class RestaurantViewModel(application: Application) : AndroidViewModel(application) {
    var repository:Repository = Repository(application)
    lateinit var list:List<Any?>

    fun getDataFromServer(query:String)= liveData {
        list =repository.sendDataToRetrofit(query)
        emit(list)
    }


    fun getCountOfResults():Int = repository.getCount()




}