package com.aufhu.Aufa_Alaina_Adhar_16090154.ui.product

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.aufhu.Aufa_Alaina_Adhar_16090154.R

class ProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        setSupportActionBar(findViewById(R.id.toolbar))
    }
}