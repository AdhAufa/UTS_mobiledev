package com.aufhu.Aufa_Alaina_Adhar_16090154.ui.main

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.aufhu.Aufa_Alaina_Adhar_16090154.R
import com.aufhu.Aufa_Alaina_Adhar_16090154.common.AuthManager
import com.aufhu.Aufa_Alaina_Adhar_16090154.databinding.ActivityMainBinding
import com.aufhu.Aufa_Alaina_Adhar_16090154.ui.login.LoginActivity
import com.aufhu.Aufa_Alaina_Adhar_16090154.ui.product.ProductActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        isLoggedIn()
        signOutClick()
        goToBarangActivity()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }


    private fun isLoggedIn(){
        if(AuthManager.checkIsLoggedIn(this) == ""){
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun signOut(){
        AuthManager.clearLoggedUser(this)
        isLoggedIn()
    }

    private fun signOutClick(){
        binding.content.logoutButton.setOnClickListener {
            signOut()
        }
    }

    private fun goToBarangActivity(){
        binding.content.productButton.setOnClickListener {
            startActivity(Intent(this@MainActivity, ProductActivity::class.java))
        }
    }

}