package com.aufhu.Aufa_Alaina_Adhar_16090154.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.aufhu.Aufa_Alaina_Adhar_16090154.R
import com.aufhu.Aufa_Alaina_Adhar_16090154.common.AuthManager
import com.aufhu.Aufa_Alaina_Adhar_16090154.common.showAlert
import com.aufhu.Aufa_Alaina_Adhar_16090154.common.showToast
import com.aufhu.Aufa_Alaina_Adhar_16090154.data.model.User
import com.aufhu.Aufa_Alaina_Adhar_16090154.databinding.ActivityLoginBinding
import com.aufhu.Aufa_Alaina_Adhar_16090154.ui.main.MainActivity
import com.aufhu.Aufa_Alaina_Adhar_16090154.ui.register.RegisterActivity
import com.google.gson.Gson

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel : LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
        observe()
        loginClick()
        registerClick()
    }

    private fun setupViewModel(){
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    private fun observe(){
        viewModel.getState().observe(this, { handleState(it) })
    }

    private fun handleState(loginState: LoginState){
        when(loginState){
            is LoginState.OnLoginSuccess -> {
                onLoginSuccess(loginState.user)
                goToMainActivity()
            }
            is LoginState.IsLoading -> {
                binding.loginButton.isEnabled = !loginState.isLoading
                binding.registerButton.isEnabled = !loginState.isLoading
            }
            is LoginState.ShowAlert -> showAlert(loginState.message)
            is LoginState.ShowToast -> showToast(loginState.message)
        }
    }

    private fun loginClick(){
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val pass = binding.passwordEditText.text.toString().trim()
            viewModel.doLogin(email, pass)
        }
    }

    private fun onLoginSuccess(user: User){
        val json = Gson().toJson(user)
        AuthManager.setLoggedUser(this, json)
    }

    private fun goToMainActivity(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun registerClick(){
        binding.registerButton.setOnClickListener {
            startActivityForResult(Intent(this, RegisterActivity::class.java), 10)
        }
    }

    private fun isLoggedIn(){
        if(AuthManager.checkIsLoggedIn(this) != ""){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10 && resultCode == RESULT_OK){
            isLoggedIn()
        }
    }
}