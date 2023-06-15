package com.example.iqbalfundamental.retrofit

import com.google.gson.annotations.SerializedName

data class UserResponse(

    @field:SerializedName("total_count")
    val totalCount: Int? = null,

    @field:SerializedName("incomplete_results")
    val incompleteResults: Boolean? = null,

    @field:SerializedName("items")
    val items: List<ItemsItem?>? = null
)

data class ItemsItem(

    @field:SerializedName("login")
    val login: String? = null,

    @field:SerializedName("url")
    val url: String? = null,

    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null,

    var name: String? = null,
)

data class DetailUserResponse(
    @field:SerializedName("following_url")
    val followingUrl: String? = null,

    @field:SerializedName("login")
    val login: String? = null,

    @field:SerializedName("followers")
    val followers: Int? = null,

    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null,

    @field:SerializedName("following")
    val following: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("location")
    val location: String? = null,
)
