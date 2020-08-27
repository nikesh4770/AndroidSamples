package com.axxess.assignmentnikesh

import android.app.Application
import com.axxess.assignmentnikesh.di.component.AppComponent
import com.axxess.assignmentnikesh.di.component.DaggerAppComponent
import com.axxess.assignmentnikesh.di.module.AppModule
import com.axxess.assignmentnikesh.di.module.PreferencesModule
import com.axxess.assignmentnikesh.di.module.RetrofitModule
import com.axxess.assignmentnikesh.di.module.RoomModule
import javax.inject.Inject

class MyApplication : Application() {

    @Inject
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .roomModule(RoomModule(this))
            .preferencesModule(PreferencesModule())
            .retrofitModule(RetrofitModule())
            .build()

        appComponent.inject(this)
    }
}