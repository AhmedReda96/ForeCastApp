package com.example.forecastapp.repo

import android.app.Application
import androidx.room.Query
import com.example.forecastapp.api.Service
import com.example.forecastapp.model.NewsResponse
import retrofit2.Response
import javax.inject.Inject

class Repository {
    private lateinit var service: Service
    val appid: String = "b4c2f09608a1a4454d14f4c677c465a7"

    @Inject
    constructor(service: Service) {
        this.service = service
    }


    suspend fun getNewsData(query: String): Response<NewsResponse?>? {
        return service.getForecast(query, appid)

    }


}