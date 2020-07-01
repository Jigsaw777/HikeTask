package com.example.hiketask.domain.entities

import com.google.gson.annotations.SerializedName

data class ParentEntity(
    @SerializedName("photos")
    val photos: MainEntity,
    @SerializedName("stats")
    val stats: String
)