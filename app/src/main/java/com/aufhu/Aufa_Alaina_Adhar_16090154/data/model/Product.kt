package com.aufhu.Aufa_Alaina_Adhar_16090154.data.model

import com.google.gson.annotations.SerializedName

data class Product (
        @SerializedName("id")
        var id : Int? = null,

        @SerializedName("product_name")
        var name : String? = null,

        @SerializedName("price")
        var price : Int = 0,

        @SerializedName("user")
        var user : User? = null

)