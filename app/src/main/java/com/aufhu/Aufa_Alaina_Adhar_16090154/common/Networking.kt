package com.aufhu.Aufa_Alaina_Adhar_16090154.common

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Networking {
    const val ENDPOINT = "https://uts-mobiledev.herokuapp.com/api/"
    private var retrofit: Retrofit? = null

    private val client = OkHttpClient.Builder().apply {
        writeTimeout(60, TimeUnit.SECONDS)
        connectTimeout(60, TimeUnit.SECONDS)
        readTimeout(60, TimeUnit.SECONDS)
//        followRedirects(false)
    }.build()

    private fun getClient() : Retrofit {
        return if(retrofit == null){
            retrofit = Retrofit.Builder().apply {
                baseUrl(ENDPOINT)
                client(client)
                addConverterFactory(GsonConverterFactory.create())
            }.build()
            retrofit!!
        }else{
            retrofit!!
        }
    }

    fun provideAPIService() = getClient().create(ApiService::class.java)
}