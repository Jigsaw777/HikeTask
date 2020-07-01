package com.example.hiketask.data.services

import com.example.hiketask.data.constants.AppConstants
import com.example.hiketask.domain.entities.MainEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface GetServices {
    @GET(AppConstants.GET_IMAGES_SERVICE)
    fun getImages(@QueryMap data:Map<String,String>):Single<MainEntity>
}