package com.mehmetgunduz.besinlerkitabi.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mehmetgunduz.besinlerkitabi.model.Besin

class BesinDetayViewModel : ViewModel() {

    //seçilen besin gözükecek ondan dizi almadık
    val besinLiveData = MutableLiveData<Besin>()

    fun roomVerisiniAl(){

        val muz = Besin("Muz","20","10","5","6","www.test.com")
        besinLiveData.value = muz
    }

}