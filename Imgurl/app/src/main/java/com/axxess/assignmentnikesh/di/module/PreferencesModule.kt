package com.axxess.assignmentnikesh.di.module

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [AppModule::class])
class PreferencesModule {

    @Singleton
    @Provides
    fun providesSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("ImgurlAxxess", Context.MODE_PRIVATE)
    }
}
