package com.axxess.assignmentnikesh.di.module

import android.content.Context
import com.axxess.assignmentnikesh.MyApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(var application: MyApplication) {

    @Provides
    @Singleton
    fun providesApplication(): MyApplication {
        return application
    }

    @Provides
    @Singleton
    fun providesAppContext(): Context {
        return application
    }
}