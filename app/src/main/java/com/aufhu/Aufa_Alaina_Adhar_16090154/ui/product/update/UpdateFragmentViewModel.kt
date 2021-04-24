package com.aufhu.Aufa_Alaina_Adhar_16090154.ui.product.update

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aufhu.Aufa_Alaina_Adhar_16090154.common.Networking
import com.aufhu.Aufa_Alaina_Adhar_16090154.common.WrappedResponse
import com.aufhu.Aufa_Alaina_Adhar_16090154.data.model.Product
import com.aufhu.Aufa_Alaina_Adhar_16090154.data.request.CreateProductRequest
import com.aufhu.Aufa_Alaina_Adhar_16090154.ui.product.create.CreateFragmentState
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateFragmentViewModel : ViewModel(){
    private var state = MutableLiveData<UpdateFragmentState>()
    private val api = Networking.provideAPIService()

    private fun setLoading(){
        state.value = UpdateFragmentState.IsLoading(true)
    }
    private fun hideLoading(){
        state.value = UpdateFragmentState.IsLoading(false)
    }

    private fun showAlert(message: String){
        state.value = UpdateFragmentState.ShowAlert(message)
    }

    private fun success(){
        state.value = UpdateFragmentState.Success
    }

    fun updateProduct(token: String,id : String, updateProductRequest: CreateProductRequest){
        setLoading()
        val requestBody = Gson().toJson(updateProductRequest).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        api.updateProduct(token, id, requestBody).enqueue(object: Callback<WrappedResponse<Product>> {
            override fun onResponse(call: Call<WrappedResponse<Product>>, response: Response<WrappedResponse<Product>>) {
                hideLoading()
                println(response.code())
                if(response.isSuccessful){
                    success()
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

    fun deleteProduct(token: String, id: String){
        setLoading()
        api.deleteProductById(token, id).enqueue(object: Callback<WrappedResponse<Product>> {
            override fun onResponse(call: Call<WrappedResponse<Product>>, response: Response<WrappedResponse<Product>>) {
                hideLoading()
                println(response.code())
                if(response.isSuccessful){
                    success()
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

    fun getProductById(token: String, id: String){
        setLoading()
        api.getProductById(token, id).enqueue(object: Callback<WrappedResponse<Product>> {
            override fun onResponse(call: Call<WrappedResponse<Product>>, response: Response<WrappedResponse<Product>>) {
                hideLoading()
                if(response.isSuccessful){
                    response.body()?.data?.let { p ->
                        state.value = UpdateFragmentState.OnGetProduct(p)
                    }
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

    fun getState() : LiveData<UpdateFragmentState> = state
}

sealed class UpdateFragmentState {
    data class IsLoading(val isLoading: Boolean) : UpdateFragmentState()
    object Success : UpdateFragmentState()
    data class ShowAlert(val message : String) : UpdateFragmentState()
    data class OnGetProduct(val product : Product) : UpdateFragmentState()
}