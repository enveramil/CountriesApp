package com.enveramil.listedcountriesinformation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enveramil.listedcountriesinformation.model.Model
import com.enveramil.listedcountriesinformation.service.CountryAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class CountryListedViewModel : ViewModel() {
    // internetten veri indirdikçe disposable içerisine atılmaktadır.


    private val countryAPIService = CountryAPIService()
    private val disposable = CompositeDisposable()



    val countries = MutableLiveData<List<Model>>()
    val errorMessage = MutableLiveData<Boolean>()
    val loadingCountry = MutableLiveData<Boolean>()

    fun refreshData(){ getDataFromAPI()}

    private fun getDataFromAPI(){
        loadingCountry.value = true
        disposable.add(
            countryAPIService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Model>>(){
                    override fun onSuccess(t: List<Model>) {
                        countries.value = t
                        errorMessage.value = false
                        loadingCountry.value = false
                    }

                    override fun onError(e: Throwable) {
                        loadingCountry.value = false
                        errorMessage.value = true
                        e.printStackTrace()
                    }

                })
        )

    }



}