package com.example.scaliotest.api

import com.example.scaliotest.model.User
import com.google.gson.annotations.SerializedName

data class UserSearchResponse(
    @SerializedName("total_count") val total: Int = 0,
    @SerializedName("items") val items: List<User> = emptyList(),
    val nextPage: Int? = null
)
