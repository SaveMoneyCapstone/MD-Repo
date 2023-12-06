package com.dicoding.savemoney.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.*

data class OjkResponse(

	@field:SerializedName("data")
	val data: DataOjk? = null,

	@field:SerializedName("error")
	val error: Any? = null
)

@Parcelize
data class AppsItem(

	@field:SerializedName("owner")
	val owner: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("url")
	val url: String? = null
): Parcelable

data class DataOjk(

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("version")
	val version: String? = null,

	@field:SerializedName("apps")
	val apps: List<AppsItem?>? = null
)
