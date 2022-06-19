package com.enveramil.listedcountriesinformation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.enveramil.listedcountriesinformation.R
import com.enveramil.listedcountriesinformation.adapter.CountryAdapter
import com.enveramil.listedcountriesinformation.viewmodel.CountryListedViewModel
import kotlinx.android.synthetic.main.fragment_country_listed.*

class CountryListedFragment : Fragment() {
    // ViewModel oluşturularak datalar ele alınacak.

    private lateinit var viewModel : CountryListedViewModel
    private val countryAdapter = CountryAdapter(arrayListOf())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_country_listed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CountryListedViewModel::class.java)
        viewModel.refreshData()
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = countryAdapter
        observeLiveData()
        swipeRefreshLayout.setOnRefreshListener {
            recyclerView.visibility = View.GONE
            errorMessage.visibility = View.GONE

            loadingData.visibility = View.VISIBLE
            viewModel.refreshData()
            swipeRefreshLayout.isRefreshing = false
        }


    }

    fun observeLiveData(){
        viewModel.countries.observe(viewLifecycleOwner, Observer {
            countries ->
            countries?.let {
                recyclerView.visibility = View.VISIBLE
                countryAdapter.updateCountryList(countries)
            }
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            error ->
            error?.let {
                if (it){
                    errorMessage.visibility = View.VISIBLE
                }else{
                    errorMessage.visibility = View.GONE
                }
            }
        })

        viewModel.loadingCountry.observe(viewLifecycleOwner, Observer {
            loading ->
            loading?.let {
                if (it){
                    loadingData.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                    errorMessage.visibility = View.GONE
                }else{
                    loadingData.visibility = View.GONE
                }
            }
        })
    }
}