package com.dicoding.savemoney.data.api

import com.dicoding.savemoney.data.*
import com.dicoding.savemoney.data.model.NewsResponse
import com.dicoding.savemoney.data.response.*
import retrofit2.*
import retrofit2.http.*

interface ApiService {

    @GET("api/apps/")
    suspend fun getOjkInvestment(): Response<OjkResponse>

    @GET("stock/idx/trending")
    suspend fun getSahamTrending(
        @Header("X-API-KEY") apiKey: String,
    ): Response<SahamTrendingResponse>

    @POST("recomendation")
    suspend fun getRecommendation(
        @Body() userData: UserData
        ) : Response<RecommendationResponse>

    @GET("news")
    fun getNews() : Call<NewsResponse>
}

