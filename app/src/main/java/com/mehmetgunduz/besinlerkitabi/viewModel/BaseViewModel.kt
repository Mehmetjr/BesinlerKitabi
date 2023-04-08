package com.mehmetgunduz.besinlerkitabi.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseViewModel(application: Application) : AndroidViewModel(application),CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext
    //arkaplanda ne yapılırsa yapılsan main e geri dönecek
        get() = job + Dispatchers.Main

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}