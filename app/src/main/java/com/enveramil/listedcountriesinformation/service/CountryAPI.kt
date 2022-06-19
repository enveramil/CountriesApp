package com.enveramil.listedcountriesinformation.service

import com.enveramil.listedcountriesinformation.model.Model
import io.reactivex.Single
import retrofit2.http.GET

interface CountryAPI {

    @GET("atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json")
    fun getCountries() : Single<List<Model>>
}