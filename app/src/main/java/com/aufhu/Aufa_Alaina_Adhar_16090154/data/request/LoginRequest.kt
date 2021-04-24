package com.aufhu.Aufa_Alaina_Adhar_16090154.data.request

import com.google.gson.annotations.SerializedName

data class LoginRequest (
    @SerializedName("email")
    val email : String,

    @SerializedName("password")
    val password : String
)