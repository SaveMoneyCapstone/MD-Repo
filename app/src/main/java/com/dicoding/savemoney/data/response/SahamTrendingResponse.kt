package com.dicoding.savemoney.data.response

import android.os.Parcelable
import com.google.gson.annotations.*
import kotlinx.parcelize.*

data class SahamTrendingResponse(

    @field:SerializedName("data")
    val data: DataSahamTrending? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null
)

@Parcelize
data class ResultsItemSaham(

    @field:SerializedName("symbol")
    val symbol: String? = null,

    @field:SerializedName("change")
    val change: Int? = null,

    @field:SerializedName("company")
    val company: Company? = null,

    @field:SerializedName("close")
    val close: Int? = null,

    @field:SerializedName("percent")
    val percent: Double? = null
): Parcelable

@Parcelize
data class Company(

    @field:SerializedName("symbol")
    val symbol: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("logo")
    val logo: String? = null
): Parcelable

data class DataSahamTrending(

    @field:SerializedName("results")
    val results: List<ResultsItemSaham?>? = null
)
