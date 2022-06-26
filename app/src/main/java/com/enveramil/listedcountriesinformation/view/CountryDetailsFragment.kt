package com.enveramil.listedcountriesinformation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.enveramil.listedcountriesinformation.R
import com.enveramil.listedcountriesinformation.databinding.FragmentCountryDetailsBinding
import com.enveramil.listedcountriesinformation.util.getImage
import com.enveramil.listedcountriesinformation.util.placeHolderProgressBar
import com.enveramil.listedcountriesinformation.viewmodel.CountryDetailsViewModel
import kotlinx.android.synthetic.main.fragment_country_details.*
import kotlinx.android.synthetic.main.fragment_country_details.countryName
import kotlinx.android.synthetic.main.fragment_country_details.view.*
import kotlinx.android.synthetic.main.item_countries_list.*

class CountryDetailsFragment : Fragment() {
    private lateinit var viewModel : CountryDetailsViewModel
    private var uuid = 0
    private lateinit var dataBinding : FragmentCountryDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_country_details,container,false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            uuid = CountryDetailsFragmentArgs.fromBundle(it).countryUuid
        }

        viewModel = ViewModelProviders.of(this).get(CountryDetailsViewModel::class.java)
        viewModel.getDataFromRoom(uuid)
        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.selectedCountryLiveData.observe(viewLifecycleOwner, Observer {
            Countries ->
            Countries?.let {
                dataBinding.selectedCountry = it
                /*
                countryName.text = Countries.countryName
                capitalName.text = Countries.capitalName
                regionName.text = Countries.regionName
                currencyName.text = Countries.currencyName
                languageName.text = Countries.languageName
                context?.let {
                    countryImageView.getImage(Countries.imageUrl, placeHolderProgressBar(it))
                }

                 */
            }
        })
    }

}