package com.dicoding.savemoney.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.dicoding.savemoney.data.ResultState
import com.dicoding.savemoney.data.api.ApiService
import com.dicoding.savemoney.data.model.NewsResponse
import com.dicoding.savemoney.data.model.PostsItem
import com.dicoding.savemoney.data.response.RecommendationResponse
import com.dicoding.savemoney.data.response.UserData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository(private val apiService: ApiService) {


    private val _newsItem = MutableLiveData<List<PostsItem>>()
    val listNews: LiveData<List<PostsItem>> = _newsItem
    private val _message = MutableLiveData<String>()
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    fun getNews() {
        _isLoading.value = true
        val client = apiService.getNews()
        client.enqueue(object : Callback<NewsResponse> {
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    _isLoading.value = false
                    if(response.isSuccessful) {
                        val responseBody = response.body()
                        if(response != null) {
                            _newsItem.value = responseBody?.data?.news?.posts

                        }
                        _message.value = responseBody?.status?.message.toString()
                    } else {
                        _message.value = response.message()
                    }
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    _isLoading.value = false
                    _message.value = t.message.toString()
                }
            })
    }
    companion object {
        @Volatile
        private var instance: NewsRepository? = null
        fun getInstance(apiService: ApiService): NewsRepository {
            return instance ?: synchronized(this) {
                instance ?: NewsRepository(apiService)
            }.also { instance = it }
        }
    }
}