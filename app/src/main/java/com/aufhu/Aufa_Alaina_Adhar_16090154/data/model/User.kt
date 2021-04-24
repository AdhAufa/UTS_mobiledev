package com.aufhu.Aufa_Alaina_Adhar_16090154.data.model

import com.google.gson.annotations.SerializedName

data class User (
    @SerializedName("id")
    var id : Int? = null,

    @SerializedName("name")
    var name : String? = null,

    @SerializedName("email")
    var email : String? = null,

    @SerializedName("token")
    var token : String? = null,
)