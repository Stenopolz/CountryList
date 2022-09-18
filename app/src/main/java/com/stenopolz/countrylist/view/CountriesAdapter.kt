package com.stenopolz.countrylist.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stenopolz.countrylist.databinding.ItemCountryInfoBinding
import com.stenopolz.countrylist.model.data.application.CountryShortInfo

class CountriesAdapter(
    private val onClickListener: (id: String) -> Unit
) : RecyclerView.Adapter<CountriesAdapter.ViewHolder>() {

    private var countryList: List<CountryShortInfo> = emptyList()

    fun setCountries(countries: List<CountryShortInfo>) {
        countryList = countries
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val country = countryList[position]
        holder.bind(country)
        holder.itemView.setOnClickListener {
            onClickListener(country.cca3Code)
        }
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCountryInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    class ViewHolder(
        private val binding: ItemCountryInfoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(countryInfo: CountryShortInfo) {
            binding.countryInfo = countryInfo
        }
    }
}
