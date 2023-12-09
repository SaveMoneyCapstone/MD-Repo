package com.dicoding.savemoney.data.api

import com.dicoding.savemoney.data.*
import com.dicoding.savemoney.data.response.*
import retrofit2.*
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("api/apps/")
    suspend fun getOjkInvestment(): Response<OjkResponse>

    @GET("stock/idx/trending")
    suspend fun getSahamTrending(
        @Header("X-API-KEY") apiKey: String,
    ): Response<SahamTrendingResponse>
}

