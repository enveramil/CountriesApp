package com.enveramil.listedcountriesinformation.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enveramil.listedcountriesinformation.model.Model
import com.enveramil.listedcountriesinformation.service.CountryAPIService
import com.enveramil.listedcountriesinformation.service.CountryDao
import com.enveramil.listedcountriesinformation.service.CountryDatabase
import com.enveramil.listedcountriesinformation.util.CountrySharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class CountryListedViewModel(application: Application) : BaseViewModel(application) {
    // internetten veri indirdikçe disposable içerisine atılmaktadır.
    private val countryAPIService = CountryAPIService()
    private val disposable = CompositeDisposable()
    private var countrySharedPreferences = CountrySharedPreferences(getApplication())
    private var refreshTime = 10 * 60 * 1000 * 1000 * 1000L


    val countries = MutableLiveData<List<Model>>()
    val errorMessage = MutableLiveData<Boolean>()
    val loadingCountry = MutableLiveData<Boolean>()

    fun refreshData(){

        val updateTime = countrySharedPreferences.getTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime){
            getDataFromSQLite()
        }else{
            getDataFromAPI()
        }


    }
    fun refreshDataFromAPI(){
        getDataFromAPI()
    }

    private fun getDataFromSQLite(){
        // I have Dao
        // I should use coroutine
        loadingCountry.value = true
        launch {
            val countries = CountryDatabase(getApplication()).countryDao().getAllCountries()
            showCountries(countries)
            Toast.makeText(getApplication(),"Data from SQLite",Toast.LENGTH_LONG).show()
        }
    }
    private fun getDataFromAPI(){
        loadingCountry.value = true
        disposable.add(
            countryAPIService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Model>>(){
                    override fun onSuccess(t: List<Model>) {
                        saveSQLite(t)
                        Toast.makeText(getApplication(),"Data from API",Toast.LENGTH_LONG).show()
                    }

                    override fun onError(e: Throwable) {
                        loadingCountry.value = false
                        errorMessage.value = true
                        e.printStackTrace()
                    }
                })
        )
    }
    private fun showCountries(countryList : List<Model>){
        countries.value = countryList
        errorMessage.value = false
        loadingCountry.value = false
    }

    private fun saveSQLite(list : List<Model>){

        // To apply coroutines transactions with (launch)

        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            dao.deleteAllCountries()
            var listLong = dao.insertAll(*list.toTypedArray())
            var i = 0
            while (i < list.size){
                list[i].uuid = listLong[i].toInt()
                i = i + 1
            }
            showCountries(list)
        }

        // get saved time in sqlite
        countrySharedPreferences.saveTime(System.nanoTime())

    }

    override fun onCleared() {
        // use memory efficiently
        super.onCleared()
        disposable.clear()
    }

}