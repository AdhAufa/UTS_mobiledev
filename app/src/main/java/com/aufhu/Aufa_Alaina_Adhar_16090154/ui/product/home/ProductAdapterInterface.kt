package com.aufhu.Aufa_Alaina_Adhar_16090154.ui.product.home

import com.aufhu.Aufa_Alaina_Adhar_16090154.data.model.Product

interface ProductAdapterInterface {
    fun onTap(p: Product)
    fun onLongTap(p : Product)
}