package com.example.forecastapp.db

import androidx.annotation.Nullable
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.forecastapp.model.NewsList

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewsList(newsEntityList: List<NewsEntity>?)


    @Query("SELECT *  From newsTable WHERE city = :city")
     fun getNews(city : String): LiveData<List<NewsEntity>>?

    @Query("SELECT EXISTS(SELECT * FROM newsTable WHERE city = :city)")
    fun isCityIsExist(city : String) : Boolean


    @Query("SELECT EXISTS(SELECT * FROM newsTable WHERE dt = :id)")
    fun isIdIsExist(id : Int) : Boolean

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNewsList(newsEntityList: List<NewsEntity>)

}