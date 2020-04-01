package com.zomatoapi.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zomatoapi.R
import com.zomatoapi.models.Restaurant_


class Adapter(var dataList: List<Any?>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object{
        var TYPE_DATA =  0
        var TYPE_HEADER = 1
    }



    fun listToInflate(dataList: List<Any?>)
    {

        this.dataList = dataList
        notifyDataSetChanged()
    }






    override fun getItemViewType(position: Int): Int {
        if (dataList[position] is String) {
            return TYPE_HEADER
        } else if (dataList[position] is Restaurant_) {
            return TYPE_DATA
        }
        return 10
    }

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var category: TextView = itemView.findViewById(R.id.category)
    }

    inner class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var resName: TextView = itemView.findViewById(R.id.restaurant)
        var averageCostForTwo = itemView.findViewById(R.id.averageCostForTwo) as TextView
        var userRating = itemView.findViewById<TextView>(R.id.userRatings)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_HEADER) {
            return CategoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_categories, parent, false)
            )
        }

        return RestaurantViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_restaurant, parent, false))


    }

    override fun getItemCount(): Int {
        return dataList.size
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == TYPE_HEADER) {
            (holder as CategoryViewHolder).category.text = dataList[position].toString()
        } else if (holder.itemViewType == TYPE_DATA) {
            (holder as RestaurantViewHolder).resName.text = (dataList[position] as Restaurant_).name
            holder.averageCostForTwo.text =  "Average cost for two: ${(dataList[position] as Restaurant_).averageCostForTwo.toString()}"
            holder.userRating.text = "User Ratings: ${(dataList[position] as Restaurant_).userRating.aggregateRating.toString()}"


        }
    }


}