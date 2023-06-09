package com.mehmetgunduz.besinlerkitabi.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mehmetgunduz.besinlerkitabi.model.Besin


@Database(entities = arrayOf(Besin::class),version = 1)
abstract class BesinDatabase : RoomDatabase()  {

    abstract fun besinDao(): BesinDAO

    companion object{
        @Volatile private var instance : BesinDatabase? = null

        private val lock = Any()
        operator fun invoke(context:Context) = instance ?: synchronized(lock){
            instance ?: DatabaseOlustur(context).also {
                instance = it
            }
        }


    private fun DatabaseOlustur(context:Context) =
        Room.databaseBuilder(context.applicationContext,
                BesinDatabase::class.java,"besindatabase").build()
    }
}
