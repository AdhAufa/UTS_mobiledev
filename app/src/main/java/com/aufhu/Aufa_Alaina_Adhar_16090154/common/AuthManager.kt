package com.aufhu.Aufa_Alaina_Adhar_16090154.common

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import com.aufhu.Aufa_Alaina_Adhar_16090154.data.model.Product
import com.aufhu.Aufa_Alaina_Adhar_16090154.data.model.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object AuthManager {
    const val USER_PREF = "USER_PREF"

    fun checkIsLoggedIn(ctx: Context) : String {
        val pref =  ctx.getSharedPreferences("USER_MANAGER", MODE_PRIVATE)
        return pref.getString(USER_PREF, "").toString()
    }

    fun setLoggedUser(ctx: Context, userData: String) {
        val pref =  ctx.getSharedPreferences("USER_MANAGER", MODE_PRIVATE).edit()
        pref.putString(USER_PREF, userData).apply()
    }

    fun clearLoggedUser(ctx: Context){
        val pref =  ctx.getSharedPreferences("USER_MANAGER", MODE_PRIVATE).edit()
        pref.clear()
        pref.apply()
    }

    fun getToken(context: Context) : String? {
        val user = checkIsLoggedIn(context)
        if (user == ""){
            return null
        }
        val type = object : TypeToken<User>() {}.type
        val userObj : User = Gson().fromJson(user, type) ?: return null
        return userObj.token
    }
}