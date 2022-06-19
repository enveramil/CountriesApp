package com.enveramil.listedcountriesinformation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enveramil.listedcountriesinformation.model.Model

class CountryDetailsViewModel : ViewModel() {
    // sadece seçilen ülkeyi almak yeterli olacaktır.

    val selectedCountryLiveData = MutableLiveData<Model>()

    fun getDataFromRoom(){
        val country = Model("Turkey","Ankara","Asia","TRY","Turkish","www.sss.com.tr")
        selectedCountryLiveData.value = country
    }
}