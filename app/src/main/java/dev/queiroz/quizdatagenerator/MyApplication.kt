package dev.queiroz.quizdatagenerator

import android.app.Application
import dev.queiroz.quizdatagenerator.di.AppComponent
import dev.queiroz.quizdatagenerator.di.DaggerAppComponent


open class MyApplication : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}
