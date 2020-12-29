package com.example.githubsearch.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.githubsearch.network.*
import com.example.githubsearch.overview.GithubApiStatus
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(@Suppress("UNUSED_PARAMETER")item: Item, app: Application) : AndroidViewModel(app) {
    private val _selectedProperty = MutableLiveData<Item>()
    val selectedProperty: LiveData<Item>
        get() = _selectedProperty

    private val _reposProperty = MutableLiveData<Repos>()
    val reposProperty: LiveData<Repos>
        get() = _reposProperty

    private val _language = MutableLiveData<String>()
    val language: LiveData<String>
        get() = _language

    private val _starCount = MutableLiveData<String>()
    val starCount: LiveData<String>
        get() = _starCount

    private val _follower = MutableLiveData<String>()
    val follower: LiveData<String>
        get() = _follower

    private val _following = MutableLiveData<String>()
    val following: LiveData<String>
        get() = _following
    init {
        _selectedProperty.value = item
        getUserAndRepoProperty()
    }
    private fun getUserAndRepoProperty() {
        viewModelScope.launch {
            GithubApi.retrofitService.getRepos(selectedProperty.value?.owner?.login, selectedProperty?.value?.name).enqueue(
                object:Callback<Repos> {
                    override fun onResponse(call: Call<Repos>, response: Response<Repos>) {
                        _reposProperty.value = response.body()
                        _starCount.value = response.body()?.stargazersCount.toString()
                        _language.value = response.body()?.language ?: "no language"
                    }
                    override fun onFailure(call: Call<Repos>, t: Throwable) {
                        Log.d("error", t.message.toString())
                    }
                }
            )
            GithubApi.retrofitService.getUsers(selectedProperty.value?.owner?.login).enqueue(
                object:Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        _follower.value = response.body()?.followers.toString()
                        _following.value = response.body()?.following.toString()
                    }
                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Log.d("error", t.message.toString())
                    }
                }
            )
            if (language.value == "")
                _language.value = "no language"
        }
    }
}