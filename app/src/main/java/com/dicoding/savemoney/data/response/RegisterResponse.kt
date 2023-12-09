package com.dicoding.savemoney.data.response

import com.google.gson.annotations.*

data class RegisterResponse(
    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
)