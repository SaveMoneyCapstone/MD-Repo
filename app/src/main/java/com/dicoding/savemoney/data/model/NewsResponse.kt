package com.dicoding.savemoney.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class NewsResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("status")
	val status: Status? = null
)

data class News(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("link")
	val link: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("posts")
	val posts: List<PostsItem>
)

@Parcelize
data class PostsItem(

	@field:SerializedName("thumbnail")
	val thumbnail: String,

	@field:SerializedName("link")
	val link: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("pubDate")
	val pubDate: String
) : Parcelable

data class Data(

	@field:SerializedName("news")
	val news: News
)

data class Status(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("message")
	val message: String
)
