package com.aufhu.Aufa_Alaina_Adhar_16090154.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aufhu.Aufa_Alaina_Adhar_16090154.common.Networking
import com.aufhu.Aufa_Alaina_Adhar_16090154.common.WrappedResponse
import com.aufhu.Aufa_Alaina_Adhar_16090154.data.model.User
import com.aufhu.Aufa_Alaina_Adhar_16090154.data.request.RegisterRequest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {
    private val state = MutableLiveData<RegisterState>()
    private val api = Networking.provideAPIService()

    private fun showToast(message: String){
        state.value = RegisterState.ShowToast(message)
    }

    private fun showAlert(message: String){
        state.value = RegisterState.ShowAlert(message)
    }

    private fun setLoading(){
        state.value = RegisterState.IsLoading(true)
    }

    private fun hideLoading(){
        state.value = RegisterState.IsLoading(false)
    }

    fun doRegister(name: String, email: String, password: String){
        setLoading()
        val userRequest = RegisterRequest(name, email, password)
        val requestBody = Gson().toJson(userRequest).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        api.register(requestBody).enqueue(object : Callback<WrappedResponse<User>>{
            override fun onResponse(call: Call<WrappedResponse<User>>, response: Response<WrappedResponse<User>>) {
                hideLoading()
                if(response.isSuccessful){
                    response.body()?.data?.let {
                        state.value = RegisterState.OnRegisterSuccess(it)
                    }
                }else{
                    val type = object : TypeToken<WrappedResponse<User>>() {}.type
                    val err : WrappedResponse<User>? = Gson().fromJson(response.errorBody()!!.charStream(), type)
                    err?.let {
                        val msg = err.errors?.get(0)
                        msg?.let { errorMsg -> showAlert(errorMsg) }
                    }
                }
            }

            override fun onFailure(call: Call<WrappedResponse<User>>, t: Throwable) {
                hideLoading()
                showAlert(t.message.toString())
            }

        })
    }

    fun getState() : LiveData<RegisterState> = state
}

sealed class RegisterState {
    data class OnRegisterSuccess(val user: User) : RegisterState()
    data class IsLoading(val isLoading: Boolean) : RegisterState()
    data class ShowAlert(val message: String) : RegisterState()
    data class ShowToast(val message: String) : RegisterState()
}