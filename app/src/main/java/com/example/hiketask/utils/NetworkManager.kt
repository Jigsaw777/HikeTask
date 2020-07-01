package com.example.hiketask.utils

import com.example.hiketask.data.constants.AppConstants
import com.example.hiketask.data.services.GetServices
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkManager {
    companion object{
        private fun httpClient(): OkHttpClient {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            return OkHttpClient().newBuilder().connectTimeout(5000, TimeUnit.MILLISECONDS)
                .readTimeout(5000, TimeUnit.MILLISECONDS).addInterceptor(loggingInterceptor).build()
        }

        private val retrofit =
            Retrofit.Builder().addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).baseUrl(
                    AppConstants.BASE_URL
                )
                .client(httpClient())
                .build()

        fun getFetchSevices():GetServices{
            return retrofit.create(GetServices::class.java)
        }
    }
}