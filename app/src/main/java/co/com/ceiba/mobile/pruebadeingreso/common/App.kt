package co.com.ceiba.mobile.pruebadeingreso.common

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import co.com.ceiba.mobile.pruebadeingreso.userfeature.di.initDI

class App : Application() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
        this.initDI()
    }
}