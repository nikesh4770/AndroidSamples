package com.axxess.assignmentnikesh.di.component

import android.content.SharedPreferences
import com.axxess.assignmentnikesh.MyApplication
import com.axxess.assignmentnikesh.di.module.AppModule
import com.axxess.assignmentnikesh.di.module.PreferencesModule
import com.axxess.assignmentnikesh.di.module.RetrofitModule
import com.axxess.assignmentnikesh.di.module.RoomModule
import com.axxess.assignmentnikesh.network.ApiInterface
import com.axxess.assignmentnikesh.room.AppDatabase
import com.axxess.assignmentnikesh.room.CommentDao
import com.axxess.assignmentnikesh.room.CommentRepository
import com.axxess.assignmentnikesh.ui.view.CommentDialogFragment
import com.axxess.assignmentnikesh.ui.view.ImageSearchFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RetrofitModule::class, RoomModule::class, PreferencesModule::class])
interface AppComponent {

    fun inject(application: MyApplication)

    fun getRetrofitInterface(): ApiInterface

    fun getSharedPreferences(): SharedPreferences

    fun inject(imageSearchFragment: ImageSearchFragment)

    fun commentDao(): CommentDao?

    fun demoDatabase(): AppDatabase?

    fun productRepository(): CommentRepository?

    fun inject(commentDialogFragment: CommentDialogFragment)
}