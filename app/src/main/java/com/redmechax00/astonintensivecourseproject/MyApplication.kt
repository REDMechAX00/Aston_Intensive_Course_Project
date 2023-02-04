package com.redmechax00.astonintensivecourseproject

import android.app.Application
import com.redmechax00.astonintensivecourseproject.di.components.AppComponent

class MyApplication : Application() {
    private var appComponent: AppComponent? = null

    fun getAppComponent(): AppComponent {
        if (appComponent == null) {
            appComponent = AppComponent.create(applicationContext)
        }
        return appComponent!!
    }
}