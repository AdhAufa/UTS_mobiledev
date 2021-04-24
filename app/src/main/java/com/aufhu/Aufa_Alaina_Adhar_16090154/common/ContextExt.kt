package com.aufhu.Aufa_Alaina_Adhar_16090154.common

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog


fun Context.showAlert(message: String){
    AlertDialog.Builder(this).apply {
        setMessage(message)
        setPositiveButton("OK"){ dialog, _ ->
            dialog.cancel()
        }
    }.show()
}

fun Context.showToast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}