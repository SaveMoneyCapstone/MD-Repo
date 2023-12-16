package com.dicoding.savemoney.data.response

import com.google.gson.annotations.SerializedName

data class RecommendationResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("status")
	val status: Status? = null,
)

data class RecomendationsItem(

	@field:SerializedName("date")
	val date: String,

	@field:SerializedName("volume")
	val volume: Int,

	@field:SerializedName("symbol")
	val symbol: String,

	@field:SerializedName("high")
	val high: Int,

	@field:SerializedName("hasil_mean")
	val hasilMean: Any,

	@field:SerializedName("low")
	val low: Int,

	@field:SerializedName("company.logo")
	val companyLogo: String,

	@field:SerializedName("company.name")
	val companyName: String,

	@field:SerializedName("close")
	val close: Int,

	@field:SerializedName("open")
	val open: Int
)

data class Status(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("message")
	val message: String,
)

data class Data(

	@field:SerializedName("Pengeluaran User")
	val pengeluaranUser: List<Int>,

	@field:SerializedName("Pemasukan User")
	val pemasukanUser: List<Int>,

	@field:SerializedName("recomendations")
	val recomendations: List<RecomendationsItem>
)
