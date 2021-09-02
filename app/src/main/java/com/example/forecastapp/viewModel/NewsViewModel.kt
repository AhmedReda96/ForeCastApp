package com.example.forecastapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.forecastapp.db.NewsEntity
import com.example.forecastapp.model.NewsList
import com.example.forecastapp.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject
import androidx.lifecycle.ViewModel as ViewModel1

@HiltViewModel
class NewsViewModel @Inject constructor(private val repository: Repository) : ViewModel1() {
    private val TAG = "NewsViewModel"
    private var newsList: LiveData<List<NewsEntity>>? = null
    public var MLD: MutableLiveData<String>? = MutableLiveData()
    private lateinit var roomList: List<NewsEntity>

    val scope = CoroutineScope(
        Job() + Dispatchers.Main
    )


    fun getNewsList(): LiveData<List<NewsEntity>>? {
        return newsList
    }

    public fun getNewsData(query: String) {

        scope.launch {
            try {
                val response = repository.getNewsData(query)

                if (response?.isSuccessful!!) {
                    Log.d(
                        TAG,
                        "logTag getNews: response isSuccessful : ${response.body()?.news?.size}"
                    )
                    response.body()?.let { insertIntoRoom(query, it.news) }

                } else {
                    Log.d(
                        TAG,
                        "logTag getNews: response isNotSuccessful ${
                            response.message().toString()
                        }"
                    )
                }

            } catch (e: Exception) {
                Log.d(
                    TAG,
                    "logTag getNews: ${e.message}"
                )
                MLD?.value = "noData"

            }
        }.invokeOnCompletion {
            scope.cancel()

        }


    }

    private fun insertIntoRoom(query: String, list: List<NewsList>) {
        var newsEntity: NewsEntity? = null

        for ((index, value) in list.withIndex()!!) {
            try {
                newsEntity = NewsEntity(
                    value.dt,
                    "Rate is " + value.clouds.all.toString(),
                    "speed=" + value.wind.speed.toString() + " deg=" + value.wind.deg.toString() + " gust=" + value.wind.gust.toString(),
                    "Rate is " + value.rain.h.toString(),
                    value.dtTxt,
                    value.weather[0].description
                )


            } catch (e: java.lang.Exception) {
                newsEntity = NewsEntity(
                    value.dt,
                    query,
                    "Rate is " + value.clouds.all.toString(),
                    "speed=" + value.wind.speed.toString() + " deg=" + value.wind.deg.toString() + " gust=" + value.wind.gust.toString(),
                    null,
                    value.dtTxt,
                    value.weather[0].description
                )

            }
            newsEntity?.let { it1 ->
                roomList = listOf(it1)
                repository.insertNewsToRoom(roomList)

            }

        }
    }

    fun getNewsDataFromRoom(query: String): LiveData<List<NewsEntity>>? {
        repository.getNewsFromRoom(query).let {
            newsList = it
            Log.d(TAG, "logTag getNewsDataFromRoom : ")

        }
        return newsList

    }


    fun cityIsExist(city: String): Boolean {
        repository.cityIsExist(city).let { return it }

    }


}