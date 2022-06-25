package com.enveramil.listedcountriesinformation.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enveramil.listedcountriesinformation.model.Model
import com.enveramil.listedcountriesinformation.service.CountryDatabase
import kotlinx.coroutines.launch

class CountryDetailsViewModel(application: Application) : BaseViewModel(application) {
    // sadece seçilen ülkeyi almak yeterli olacaktır.

    val selectedCountryLiveData = MutableLiveData<Model>()

    fun getDataFromRoom(uuid : Int){
        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            val selectedCountry = dao.getCountry(uuid)
            selectedCountryLiveData.value = selectedCountry

        }
    }
}