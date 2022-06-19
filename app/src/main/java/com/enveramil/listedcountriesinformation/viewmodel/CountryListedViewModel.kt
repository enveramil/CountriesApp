package com.enveramil.listedcountriesinformation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enveramil.listedcountriesinformation.model.Model

class CountryListedViewModel : ViewModel() {
    val countries = MutableLiveData<List<Model>>()
    val errorMessage = MutableLiveData<Boolean>()
    val loadingCountry = MutableLiveData<Boolean>()

    fun refreshData(){
        val country = Model("Turkey","Ankara","Asia","TRY","Turkish","www.ss.com")
        val country2 = Model("Germany","Berlin","Europe","EUR","Turkish","www.ss.com")
        val country3 = Model("Italy","Ankara","Europe","TRY","Turkish","www.ss.com")

        val listOfCountries = arrayListOf<Model>(country,country2,country3)
        countries.value = listOfCountries
        errorMessage.value = false
        loadingCountry.value = false
    }

}