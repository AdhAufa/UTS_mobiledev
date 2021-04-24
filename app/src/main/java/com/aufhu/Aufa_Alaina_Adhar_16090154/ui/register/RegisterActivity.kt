package com.aufhu.Aufa_Alaina_Adhar_16090154.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.aufhu.Aufa_Alaina_Adhar_16090154.R
import com.aufhu.Aufa_Alaina_Adhar_16090154.common.AuthManager
import com.aufhu.Aufa_Alaina_Adhar_16090154.common.showAlert
import com.aufhu.Aufa_Alaina_Adhar_16090154.common.showToast
import com.aufhu.Aufa_Alaina_Adhar_16090154.databinding.ActivityLoginBinding
import com.aufhu.Aufa_Alaina_Adhar_16090154.databinding.ActivityRegisterBinding
import com.google.gson.Gson


class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
        observe()
        registerClick()
    }

    private fun setupViewModel(){
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
    }

    private fun observe(){
        viewModel.getState().observe(this, { handleState(it) })
    }

    private fun handleState(state: RegisterState){
        when(state){
            is RegisterState.ShowToast -> showToast(state.message)
            is RegisterState.ShowAlert -> showAlert(state.message)
            is RegisterState.IsLoading -> binding.registerButton.isEnabled = !state.isLoading
            is RegisterState.OnRegisterSuccess -> {
                AuthManager.setLoggedUser(this, Gson().toJson(state.user))
                setResult(RESULT_OK)
                finish()
            }
        }
    }

    private fun registerClick(){
        binding.registerButton.setOnClickListener {
            val name = binding.registernamaEditText.text.toString().trim()
            val email = binding.registeremailEditText.text.toString().trim()
            val pass = binding.registerpasswordEditText.text.toString().trim()
            viewModel.doRegister(name, email, pass)
        }
    }
}