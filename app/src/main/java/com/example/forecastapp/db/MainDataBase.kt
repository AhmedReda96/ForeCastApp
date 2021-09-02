package com.example.forecastapp.db
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [NewsEntity::class], version = 1, exportSchema = false)
abstract class MainDataBase : RoomDatabase() {
    abstract fun newsDao(): NewsDao?
   companion object {
      private var instance: MainDataBase? = null
      fun getInstance(context: Context): MainDataBase? {
         if (instance == null) {
            instance = Room.databaseBuilder(
               context.applicationContext,
               MainDataBase::class.java,
               "database"
            )
               .fallbackToDestructiveMigration()
               .allowMainThreadQueries()
               .build()
         }
         return instance
      }
   }

}
