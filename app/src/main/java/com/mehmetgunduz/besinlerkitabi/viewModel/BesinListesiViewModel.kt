package com.mehmetgunduz.besinlerkitabi.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mehmetgunduz.besinlerkitabi.model.Besin
import com.mehmetgunduz.besinlerkitabi.service.BesinAPIServis
import com.mehmetgunduz.besinlerkitabi.service.BesinDatabase
import com.mehmetgunduz.besinlerkitabi.util.OzelSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class BesinListesiViewModel(application: Application) : BaseViewModel(application) {
    val besinler = MutableLiveData<List<Besin>>()
    val besinHataMesaj = MutableLiveData<Boolean>()
    val besinYükleniyor = MutableLiveData<Boolean>()

    private val besinAPIServis = BesinAPIServis()
    private val disposable = CompositeDisposable()
    private val ozelSharedPreferences = OzelSharedPreferences(getApplication())

    fun refreshData(){
        verileriInternettenAl()


    }

    private fun verileriInternettenAl(){
        besinYükleniyor.value = true


        disposable.add(
            besinAPIServis.getData()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())//soyut sınıflarda object kullanılır aşağıdaki gibi
            .subscribeWith(object : DisposableSingleObserver<List<Besin>>(){
                override fun onSuccess(t: List<Besin>) {
                    //Başarılı olursa
                    sqliteSakla(t)
                }

                override fun onError(e: Throwable) {
                    //Hata alırsak
                    besinHataMesaj.value = true
                    besinYükleniyor.value = false
                    e.printStackTrace()
                }

            })
        )
    }

    private fun besinleriGoster(besinlerListesi : List<Besin>){

        besinler.value = besinlerListesi
        besinHataMesaj.value = false
        besinYükleniyor.value = false
    }
    private fun sqliteSakla(besinListesi : List<Besin>){

        launch {

            val dao = BesinDatabase(getApplication()).besinDao()
            dao.deleteAllBesin()
            val uuidListesi = dao.insertAll(*besinListesi.toTypedArray())

            var i=0
            while (i< besinListesi.size){
                besinListesi[i].uuid = uuidListesi[i].toInt()
                i++
            }

            besinleriGoster(besinListesi)
        }

        ozelSharedPreferences.zamaniKaydet(System.nanoTime())


    }


}