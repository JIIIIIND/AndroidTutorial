package com.example.githubsearch.overview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubsearch.network.GithubApi
import com.example.githubsearch.network.GithubProperty
import com.example.githubsearch.network.Item
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

enum class GithubApiStatus { LOADING, ERROR, DONE }

class OverviewViewModel : ViewModel() {

    private val _status = MutableLiveData<GithubApiStatus>()
    val status: LiveData<GithubApiStatus>
        get() = _status

    private val _items = MutableLiveData<List<Item>>()
    val items: LiveData<List<Item>>
            get() = _items

    fun getGithubProperties(keyword: String) {
        _status.value = GithubApiStatus.LOADING
        viewModelScope.launch {
            val listResult = GithubApi.retrofitService.getProperties(keyword)
            listResult.enqueue(
                object: Callback<GithubProperty> {
                    override fun onResponse(call: Call<GithubProperty>, response: Response<GithubProperty>) {
                        _status.value = GithubApiStatus.DONE
                        _items.value = response.body()?.items
                        Log.d("items", _items.value?.size.toString())
                    }

                    override fun onFailure(call: Call<GithubProperty>, t: Throwable) {
                        _status.value = GithubApiStatus.ERROR
                        _items.value = null
                        Log.d("error", t.message.toString())
                    }
                }
            )
        }
    }
}