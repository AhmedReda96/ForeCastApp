package com.example.forecastapp.di

import android.app.Application
import android.content.Context
import com.example.forecastapp.api.Service
import com.example.forecastapp.db.MainDataBase
import com.example.forecastapp.db.NewsDao
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "http://api.openweathermap.org/data/2.5/"
    val getClient: Service
        @Provides
        @Singleton
        get() {
            val gson = GsonBuilder()
                .setLenient()
                .create()

            //to show request details
            val httpLog = HttpLoggingInterceptor()
            httpLog.level = HttpLoggingInterceptor.Level.BODY


            val clientSetup = OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .addInterceptor(httpLog)
                .build()


            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(clientSetup)
                .build()

            return retrofit.create(Service::class.java)

        }

    @Singleton
    @Provides
    fun provideDatabase(context: Application): MainDataBase? {
        return  MainDataBase.getInstance(context)
    }

    @Provides
    @Singleton
    public fun provideDao(mainDataBase: MainDataBase) : NewsDao? {return mainDataBase.newsDao()}

}