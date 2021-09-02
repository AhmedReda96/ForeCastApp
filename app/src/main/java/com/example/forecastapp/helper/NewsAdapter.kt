package com.example.forecastapp.helper

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.forecastapp.R
import com.example.forecastapp.db.NewsEntity
import com.example.forecastapp.model.NewsList
import kotlinx.android.synthetic.main.news_item_model.view.*
import java.lang.Exception

class NewsAdapter() : RecyclerView.Adapter<ViewHolder>(), Parcelable {
    private var newsList: List<NewsEntity> = ArrayList()
    private lateinit var context: Context

    constructor(parcel: Parcel) : this() {

    }


    constructor(
        newsList: List<NewsEntity>,
        context: Context,
    ) : this() {
        this.newsList = newsList
        this.context = context

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_item_model, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: NewsEntity = newsList[position]


        holder.weatherStatus.text = model.weather
        holder.cloudsStatus.text = model.clouds
        holder.windStatus.text =model.wind
        if (model.rain==null){
            holder.rainLin.visibility=View.GONE

        }else{
            holder.rainStatus.text = model.rain
            holder.rainLin.visibility=View.VISIBLE
        }
        holder.date.text =  model.dtTxt


    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NewsAdapter> {
        override fun createFromParcel(parcel: Parcel): NewsAdapter {
            return NewsAdapter(parcel)
        }

        override fun newArray(size: Int): Array<NewsAdapter?> {
            return arrayOfNulls(size)
        }
    }


}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val weatherStatus: TextView = itemView.weatherStatus
    val windStatus: TextView = itemView.windStatus
    val cloudsStatus: TextView = itemView.cloudsStatus
    val rainStatus: TextView = itemView.rainStatus
    val date: TextView = itemView.date
    val rainLin: LinearLayout = itemView.rainLin



}

fun writeToParcel(parcel: Parcel, flags: Int) {

}

fun describeContents(): Int {
    return 0
}

object CREATOR : Parcelable.Creator<NewsAdapter> {
    override fun createFromParcel(parcel: Parcel): NewsAdapter {
        return NewsAdapter(parcel)
    }

    override fun newArray(size: Int): Array<NewsAdapter?> {
        return arrayOfNulls(size)
    }
}

