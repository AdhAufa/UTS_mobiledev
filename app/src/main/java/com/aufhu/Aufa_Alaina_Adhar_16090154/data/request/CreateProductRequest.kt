package com.aufhu.Aufa_Alaina_Adhar_16090154.data.request

import com.google.gson.annotations.SerializedName

data class CreateProductRequest @ExperimentalUnsignedTypes constructor(
        @SerializedName("name")
        val name: String,

        @SerializedName("price")
        val price: UInt
)