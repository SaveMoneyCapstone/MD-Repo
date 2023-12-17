package com.dicoding.savemoney.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class RecommendationResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("status")
	val status: Status? = null
)
@Parcelize
data class RecomendationsItem(

	@field:SerializedName("date")
	val date: String,

	@field:SerializedName("volume")
	val volume: Int,

	@field:SerializedName("symbol")
	val symbol: String,

	@field:SerializedName("high")
	val high: Int,

	@field:SerializedName("low")
	val low: Int,


	@field:SerializedName("hasil_mean")
	val hasil_mean: Double,

	@field:SerializedName("company.logo")
	val companyLogo: String,

	@field:SerializedName("company.name")
	val companyName: String,

	@field:SerializedName("close")
	val close: Int,

	@field:SerializedName("open")
	val open: Int
) : Parcelable

data class Status(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("message")
	val message: String
)

data class Data(
	@field:SerializedName("recomendations")
	val recomendations: List<RecomendationsItem>? = null
)
