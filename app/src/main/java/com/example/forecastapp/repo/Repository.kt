package com.example.forecastapp.repo

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.forecastapp.api.Service
import com.example.forecastapp.db.NewsDao
import com.example.forecastapp.db.NewsEntity
import com.example.forecastapp.model.NewsList
import com.example.forecastapp.model.NewsResponse
import kotlinx.coroutines.*
import retrofit2.Response
import javax.inject.Inject

class Repository
@Inject constructor(private val service: Service,private val newsDao: NewsDao){
    private val TAG = "Repository"

    val scope = CoroutineScope(
        Job() + Dispatchers.Main
    )

    val appid: String = "b4c2f09608a1a4454d14f4c677c465a7"




    suspend fun getNewsData(query: String): Response<NewsResponse?>? {
        return service.getForecast(query, appid)

    }

    fun insertNewsToRoom(newsEntityList: List<NewsEntity>?) {
        scope.launch {
            newsDao.insertNewsList(newsEntityList).let {
            }
        }.invokeOnCompletion {
            Log.d(
                TAG,
                "logTag Repo:insertNewsToRoom isNotSuccessful "
            )
            scope.cancel()

        }
    }

    fun getNewsFromRoom(query: String): LiveData<List<NewsEntity>>? {
        return newsDao.getNews(query)
        Log.d(TAG, "logTag Repo:getNewsFromRoom ")
    }

    fun isIdIsExist(query: Int): Boolean {
        return newsDao.isIdIsExist(query)
        Log.d(TAG, "logTag Repo:isIdIsExist ")
    }

    fun cityIsExist(query: String): Boolean {
        return newsDao.isCityIsExist(query)
        Log.d(TAG, "logTag Repo:isCityIsExist ")
    }


}