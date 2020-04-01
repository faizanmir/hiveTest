package com.zomatoapi.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zomatoapi.R
import com.zomatoapi.adapters.Adapter
import com.zomatoapi.models.Restaurant_
import com.zomatoapi.retrofit.Api
import com.zomatoapi.viewModel.RestaurantViewModel
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.HashMap


class BaseActivity :AppCompatActivity() , android.widget.SearchView.OnQueryTextListener/*Callback<Root>*/ {
    companion object{
        var TAG =this.javaClass.canonicalName
    }

     var cuisineRestaurantMap:HashMap<String,ArrayList<Restaurant_>> = HashMap()
    lateinit var cuisineList :ArrayList<String>
    lateinit var recyclerViewInstance: RecyclerView
    lateinit var api: Api
    lateinit var adapter: Adapter
    lateinit var  viewModel:RestaurantViewModel



    val finalList:MutableList<Any> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()







    }

    private fun initialize():Unit{
        cuisineRestaurantMap = HashMap()
        cuisineList = ArrayList()
        recyclerViewInstance = recyclerView
        searchBar.isSubmitButtonEnabled = true
        searchBar.setOnQueryTextListener(this)
        val retrofit:Retrofit = Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        api = retrofit.create(Api::class.java)
        adapter = Adapter(finalList)
        recyclerViewInstance.adapter = adapter
        viewModel= ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(RestaurantViewModel::class.java)
        recyclerViewInstance.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        overlay.visibility =  View.INVISIBLE
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        overlay.visibility = View.VISIBLE
        searchBar.clearFocus()
        viewModel.getDataFromServer(query!!).observe(this, androidx.lifecycle.Observer {
            overlay.visibility = View.INVISIBLE
            totalCount.text   ="Results found : ${viewModel.getCountOfResults().toString()}"
            adapter.listToInflate(it)
        })
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

}




