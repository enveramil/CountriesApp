package com.enveramil.listedcountriesinformation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.enveramil.listedcountriesinformation.R
import com.enveramil.listedcountriesinformation.viewmodel.CountryDetailsViewModel
import kotlinx.android.synthetic.main.fragment_country_details.*

class CountryDetailsFragment : Fragment() {
    private lateinit var viewModel : CountryDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_country_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CountryDetailsViewModel::class.java)
        viewModel.getDataFromRoom()
        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.selectedCountryLiveData.observe(viewLifecycleOwner, Observer {
            Countries ->
            Countries?.let {
                countryName.text = Countries.countryName
                capitalName.text = Countries.capitalName
                regionName.text = Countries.regionName
                currencyName.text = Countries.currencyName
                languageName.text = Countries.languageName
            }
        })
    }

}