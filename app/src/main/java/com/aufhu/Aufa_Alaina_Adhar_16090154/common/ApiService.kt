package com.aufhu.Aufa_Alaina_Adhar_16090154.common

import com.aufhu.Aufa_Alaina_Adhar_16090154.data.model.Product
import com.aufhu.Aufa_Alaina_Adhar_16090154.data.model.User
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @POST("auth/login")
    fun login(@Body requestBody: RequestBody) : Call<WrappedResponse<User>>

    @POST("auth/register")
    fun register(@Body requestBody: RequestBody) : Call<WrappedResponse<User>>

    @GET("product/")
    fun allProduct(@Header("Authorization") token : String) : Call<WrappedListResponse<Product>>

    @POST("product/")
    fun createProduct(@Header("Authorization") token : String, @Body requestBody: RequestBody) : Call<WrappedResponse<Product>>

    @PUT("product/{id}")
    fun updateProduct(@Header("Authorization") token: String, @Path("id") id: String, @Body requestBody: RequestBody) : Call<WrappedResponse<Product>>

    @DELETE("product/{id}")
    fun deleteProductById(@Header("Authorization") token: String, @Path("id") id : String) : Call<WrappedResponse<Product>>

    @GET("product/{id}/")
    fun getProductById(@Header("Authorization") token: String, @Path("id") id : String) : Call<WrappedResponse<Product>>
}