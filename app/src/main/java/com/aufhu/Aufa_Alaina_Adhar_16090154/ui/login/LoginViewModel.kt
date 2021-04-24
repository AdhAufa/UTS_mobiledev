package com.aufhu.Aufa_Alaina_Adhar_16090154.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aufhu.Aufa_Alaina_Adhar_16090154.common.Networking
import com.aufhu.Aufa_Alaina_Adhar_16090154.common.WrappedResponse
import com.aufhu.Aufa_Alaina_Adhar_16090154.data.model.User
import com.aufhu.Aufa_Alaina_Adhar_16090154.data.request.LoginRequest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginViewModel : ViewModel() {
    private val state = MutableLiveData<LoginState>()
    private val api = Networking.provideAPIService()

    private fun showToast(message: String){
        state.value = LoginState.ShowToast(message)
    }

    private fun showAlert(message: String){
        state.value = LoginState.ShowAlert(message)
    }

    private fun setLoading(){
        state.value = LoginState.IsLoading(true)
    }

    private fun hideLoading(){
        state.value = LoginState.IsLoading(false)
    }

    fun doLogin(email: String, password: String) {
        setLoading()
        val userRequest = LoginRequest(email, password)
        val requestBody = Gson().toJson(userRequest).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        api.login(requestBody).enqueue(object : Callback<WrappedResponse<User>>{
            override fun onResponse(call: Call<WrappedResponse<User>>, response: Response<WrappedResponse<User>>) {
                hideLoading()
                if (response.isSuccessful){
                    response.body()?.data?.let {
                        state.value = LoginState.OnLoginSuccess(it)
                    }
                }else{
                    val type = object : TypeToken<WrappedResponse<User>>() {}.type
                    val err : WrappedResponse<User>? = Gson().fromJson(response.errorBody()!!.charStream(), type)
                    err?.let {
                        showAlert(it.message)
                    }
                }
            }

            override fun onFailure(call: Call<WrappedResponse<User>>, t: Throwable) {
                hideLoading()
                showAlert(t.message.toString())
            }
        })
    }

    fun getState() : LiveData<LoginState> = state

}

sealed class LoginState {
    data class OnLoginSuccess(val user: User) : LoginState()
    data class IsLoading(val isLoading: Boolean) : LoginState()
    data class ShowAlert(val message: String) : LoginState()
    data class ShowToast(val message: String) : LoginState()
}