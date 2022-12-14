package com.stenopolz.countrylist.extensions

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stenopolz.countrylist.view.CountriesAdapter
import com.stenopolz.countrylist.viewmodel.CountryShortUiModel

@BindingAdapter("setCountries")
fun setCountries(recyclerView: RecyclerView, list: List<CountryShortUiModel>?) {
    val adapter = recyclerView.adapter as CountriesAdapter
    adapter.setCountries(list ?: emptyList())
}

@BindingAdapter("setImage")
fun setImage(imageView: ImageView, imageUrl: String?) {
    Glide.with(imageView.context)
        .load(imageUrl)
        .into(imageView)
}

@BindingAdapter("visibleIf")
fun View.setVisible(isVisible: Boolean) {
    this.visibility = if (isVisible) View.VISIBLE else View.GONE
}
