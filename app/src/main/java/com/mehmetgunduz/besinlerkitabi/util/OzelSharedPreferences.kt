package com.mehmetgunduz.besinlerkitabi.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.core.content.edit

class OzelSharedPreferences {

    companion object{
        private val ZAMAN = "zaman"
        private var sharedPreferences : SharedPreferences? = null

        @Volatile private var instance : OzelSharedPreferences? = null

        private val lock = Any()

        operator fun invoke(context: Context) : OzelSharedPreferences = instance ?: synchronized(lock){
            instance ?: OzelSharedPreferencesYap(context).also{
                instance = it
            }
        }

        private fun OzelSharedPreferencesYap(context: Context):OzelSharedPreferences{
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return OzelSharedPreferences()
        }
    }

    fun zamaniKaydet(zaman:Long){
        sharedPreferences?.edit(commit = true){
            putLong(ZAMAN,zaman)
        }
    }
}