package com.enveramil.listedcountriesinformation.model

import com.google.gson.annotations.SerializedName

data class Model(

    @SerializedName("name")
    val countryName : String?,

    @SerializedName("capital")
    val capitalName : String?,

    @SerializedName("region")
    val regionName : String?,

    @SerializedName("currency")
    val currencyName : String?,

    @SerializedName("flag")
    val imageUrl : String?,

    @SerializedName("language")
    val languageName : String?

                   )