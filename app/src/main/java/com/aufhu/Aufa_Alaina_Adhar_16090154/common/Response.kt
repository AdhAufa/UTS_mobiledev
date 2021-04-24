package com.aufhu.Aufa_Alaina_Adhar_16090154.common

import com.google.gson.annotations.SerializedName

data class WrappedListResponse<T> (
    @SerializedName("message") var message : String,
    @SerializedName("status") var status : Boolean,
    @SerializedName("errors") var errors : List<String>? = null,
    @SerializedName("data") var data : List<T>? = null
)


data class WrappedResponse<T> (
    @SerializedName("message") var message : String,
    @SerializedName("status") var status : Boolean,
    @SerializedName("errors") var errors : List<String>? = null,
    @SerializedName("data") var data : T? = null
)