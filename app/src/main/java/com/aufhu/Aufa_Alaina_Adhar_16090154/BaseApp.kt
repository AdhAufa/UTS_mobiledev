package com.aufhu.Aufa_Alaina_Adhar_16090154

import android.app.Application
import com.aufhu.Aufa_Alaina_Adhar_16090154.common.AuthManager
import com.aufhu.Aufa_Alaina_Adhar_16090154.common.Networking

class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }

    fun getApiService() = Networking.provideAPIService()
}