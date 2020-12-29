package com.example.githubsearch.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubsearch.network.Item

class DetailViewModelFactory(
    private val itemProperty: Item,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(itemProperty, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}