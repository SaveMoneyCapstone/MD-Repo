package com.dicoding.savemoney.data.response.predict

import com.dicoding.savemoney.data.response.Data
import com.dicoding.savemoney.data.response.Status
import com.google.gson.annotations.SerializedName

data class PredictResponse(

    @field:SerializedName("data")
	val data: com.dicoding.savemoney.data.response.predict.Data,

    @field:SerializedName("status")
	val status: Status? = null
)

data class Data(

	@field:SerializedName("histories Pengeluaran User")
	val historiesPengeluaranUser: List<Int>,

	@field:SerializedName("rekomendasi pengeluaran")
	val rekomendasiPengeluaran: Int,

	@field:SerializedName("prediksi pengeluaran besok")
	val prediksiPengeluaranBesok: Int
)

data class Status(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("message")
	val message: String
)
