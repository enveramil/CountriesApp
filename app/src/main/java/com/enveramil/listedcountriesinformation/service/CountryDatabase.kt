package com.enveramil.listedcountriesinformation.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.enveramil.listedcountriesinformation.model.Model


@Database(entities = arrayOf(Model::class), version = 1)
abstract class CountryDatabase : RoomDatabase() {
    abstract fun countryDao() : CountryDao

    // Singleton yapısı kullanılacaktır.

    companion object{
        @Volatile private var instance : CountryDatabase? = null
        private val lock = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(lock){
            // also (bunu yap üstüne şunu da yap)
            instance ?: createDatabase(context).also {
                instance = it
            }

        }

        private fun createDatabase(context : Context) = Room.databaseBuilder(
            context.applicationContext,CountryDatabase::class.java,"CountriesDB"

        ).build()
    }

}