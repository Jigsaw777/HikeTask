package com.example.hiketask.domain.entities

import com.google.gson.annotations.SerializedName

data class MainEntity(
    @SerializedName("page")
    val page: Int,
    @SerializedName("pages")
    val pages: Long,
    @SerializedName("perpage")
    val perPage: Int,
    @SerializedName("total")
    val total: String,
    @SerializedName("photos")
    val photos: List<PhotoEntity>
)