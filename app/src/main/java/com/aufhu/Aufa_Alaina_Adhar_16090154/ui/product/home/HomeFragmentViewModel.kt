package com.aufhu.Aufa_Alaina_Adhar_16090154.ui.product.home

import android.net.Network
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aufhu.Aufa_Alaina_Adhar_16090154.common.Networking
import com.aufhu.Aufa_Alaina_Adhar_16090154.common.WrappedListResponse
import com.aufhu.Aufa_Alaina_Adhar_16090154.common.WrappedResponse
import com.aufhu.Aufa_Alaina_Adhar_16090154.data.model.Product
import com.aufhu.Aufa_Alaina_Adhar_16090154.data.model.User
import com.aufhu.Aufa_Alaina_Adhar_16090154.ui.login.LoginState
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragmentViewModel : ViewModel() {
    private var products = MutableLiveData<List<Product>>(listOf())
    private var state = MutableLiveData<HomeFragmentState>()
    private val api = Networking.provideAPIService()

    private fun setLoading(){
        state.value = HomeFragmentState.IsLoading(true)
    }

    private fun hideLoading(){
        state.value = HomeFragmentState.IsLoading(false)
    }

    private fun showToast(message: String){
        state.value = HomeFragmentState.ShowToast(message)
    }

    fun fetchAllProducts(token : String){
        setLoading()
        api.allProduct(token).enqueue(object : Callback<WrappedListResponse<Product>>{
            override fun onResponse(call: Call<WrappedListResponse<Product>>, response: Response<WrappedListResponse<Product>>) {
                hideLoading()

                if (response.isSuccessful){
                    response.body()?.data?.let {
                        products.postValue(it)
                    }
                }else{
                    val type = object : TypeToken<WrappedResponse<User>>() {}.type
                    val err : WrappedResponse<User>? = Gson().fromJson(response.errorBody()!!.charStream(), type)
                    err?.let {
                        showToast(it.message)
                    }
                }
            }

            override fun onFailure(call: Call<WrappedListResponse<Product>>, t: Throwable) {
                hideLoading()
                showToast(t.message.toString())
            }
        })
    }

    fun getState() : LiveData<HomeFragmentState> = state
    fun getProducts() : LiveData<List<Product>> = products
}

sealed class HomeFragmentState {
    data class ShowToast(val message : String) : HomeFragmentState()
    data class IsLoading(val isLoading : Boolean) : HomeFragmentState()
}