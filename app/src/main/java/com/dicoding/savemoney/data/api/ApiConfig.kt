package com.dicoding.savemoney.data.api
import com.dicoding.savemoney.*
import com.dicoding.savemoney.utils.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.*
import retrofit2.converter.gson.*


class ApiConfig {
    companion object {

        private const val BASE_URL_MAIN = "https://story-api.dicoding.dev/v1/"
        private const val BASE_URL_IDX = "https://api.goapi.io/"
        private const val BASE_URL_OJK = "https://ojk-invest-api.vercel.app/"
        fun getApiService(apyType: GateApi): ApiService {
            val baseUrl = when(apyType) {
                GateApi.API1 -> BASE_URL_MAIN
                GateApi.API2 -> BASE_URL_IDX
                GateApi.API3 -> BASE_URL_OJK
            }
            val loggingInterceptor = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            }else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}