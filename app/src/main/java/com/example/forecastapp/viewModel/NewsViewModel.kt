package com.example.forecastapp.viewModel

import android.app.DownloadManager
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.forecastapp.model.NewsList
import com.example.forecastapp.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject
import androidx.lifecycle.ViewModel as ViewModel1

@HiltViewModel
class NewsViewModel : ViewModel1 {
    private val TAG = "NewsViewModel"
    private lateinit var repository: Repository
    private val newsList: MutableLiveData<List<NewsList>> = MutableLiveData()
    val scope = CoroutineScope(
        Job() + Dispatchers.Main
    )
    @Inject
    constructor(repository: Repository) {
        this.repository = repository

    }


    fun getNewsList(): MutableLiveData<List<NewsList>> {
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

                    newsList.value= response.body()?.news
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
            }
        }.invokeOnCompletion {
            scope.cancel()

        }


    }
}