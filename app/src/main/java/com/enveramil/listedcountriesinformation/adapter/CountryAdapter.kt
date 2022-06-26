package com.enveramil.listedcountriesinformation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.allViews
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.enveramil.listedcountriesinformation.R
import com.enveramil.listedcountriesinformation.databinding.ItemCountriesListBinding
import com.enveramil.listedcountriesinformation.model.Model
import com.enveramil.listedcountriesinformation.util.getImage
import com.enveramil.listedcountriesinformation.util.placeHolderProgressBar
import com.enveramil.listedcountriesinformation.view.CountryListedFragmentDirections
import kotlinx.android.synthetic.main.fragment_country_details.view.*
import kotlinx.android.synthetic.main.fragment_country_details.view.countryName
import kotlinx.android.synthetic.main.item_countries_list.view.*

class CountryAdapter(val countryList : ArrayList<Model>) :
    RecyclerView.Adapter<CountryAdapter.CountryViewHolder>(),CountryClickListener {

    class CountryViewHolder(var view: ItemCountriesListBinding) : RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        //val view = inflater.inflate(R.layout.item_countries_list,parent,false)
        val view = DataBindingUtil.inflate<ItemCountriesListBinding>(inflater,R.layout.item_countries_list,parent,false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.view.model = countryList[position]
        holder.view.listener = this

        //holder.itemView.countryName.text = countryList[position].countryName
        //holder.itemView.countryRegion.text = countryList[position].regionName
/*
        holder.itemView.setOnClickListener {
            val action = CountryListedFragmentDirections.actionCountryListedFragmentToCountryDetailsFragment(countryList[position].uuid)
            Navigation.findNavController(holder.itemView).navigate(action)
        }


        holder.itemView.countryImage.getImage(countryList[position].imageUrl, placeHolderProgressBar(holder.itemView.context))


 */
    }


    override fun getItemCount(): Int {
        return countryList.size
    }



    // kullanıcı sayfayı refresh ettiği zaman fonksiyon çalışarak yeni verileri çekeceğiz.
    fun updateCountryList(newCountryList : List<Model>){
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }

    override fun countryClickListener(view: View) {
        val uuid = view.countryUuidText.text.toString().toInt()
        val action = CountryListedFragmentDirections.actionCountryListedFragmentToCountryDetailsFragment(uuid)
        Navigation.findNavController(view).navigate(action)
    }


}