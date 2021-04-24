package com.aufhu.Aufa_Alaina_Adhar_16090154.ui.product.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aufhu.Aufa_Alaina_Adhar_16090154.common.Networking
import com.aufhu.Aufa_Alaina_Adhar_16090154.common.WrappedResponse
import com.aufhu.Aufa_Alaina_Adhar_16090154.data.model.Product
import com.aufhu.Aufa_Alaina_Adhar_16090154.data.model.User
import com.aufhu.Aufa_Alaina_Adhar_16090154.data.request.CreateProductRequest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateFragmentViewModel : ViewModel() {
    private var state = MutableLiveData<CreateFragmentState>()
    private val api = Networking.provideAPIService()

    private fun setLoading(){
        state.value = CreateFragmentState.IsLoading(true)
    }

    private fun hideLoading(){
        state.value = CreateFragmentState.IsLoading(false)
    }

    private fun showAlert(message: String){
        state.value = CreateFragmentState.ShowAlert(message)
    }

    fun createProduct(token: String, createProductRequest: CreateProductRequest){
        setLoading()
        val requestBody = Gson().toJson(createProductRequest).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        api.createProduct(token, requestBody).enqueue(object: Callback<WrappedResponse<Product>>{
            override fun onResponse(call: Call<WrappedResponse<Product>>, response: Response<WrappedResponse<Product>>) {
                hideLoading()
                if(response.isSuccessful){
                    state.value = CreateFragmentState.OnSuccessCreate
                }else{
                    val type = object : TypeToken<WrappedResponse<Product>>() {}.type
                    val err : WrappedResponse<Product>? = Gson().fromJson(response.errorBody()!!.charStream(), type)
                    err?.let {
                        showAlert(it.message)
                    }
                }
            }

            override fun onFailure(call: Call<WrappedResponse<Product>>, t: Throwable) {
                hideLoading()
                showAlert(t.message.toString())
            }
        })
    }

    fun getState() : LiveData<CreateFragmentState> = state
}

sealed class CreateFragmentState {
    data class IsLoading(val isLoading: Boolean) : CreateFragmentState()
    data class ShowAlert(val msg : String) : CreateFragmentState()
    object OnSuccessCreate : CreateFragmentState()
}