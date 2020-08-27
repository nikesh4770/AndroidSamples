package com.axxess.assignmentnikesh.di.module

import android.app.Application
import androidx.annotation.NonNull
import androidx.room.Room
import com.axxess.assignmentnikesh.room.AppDatabase
import com.axxess.assignmentnikesh.room.CommentDao
import com.axxess.assignmentnikesh.room.CommentRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [AppModule::class])
class RoomModule(private var application: Application) {

    @Provides
    @Singleton
    fun provideDatabase(): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "Imgurl.db")
            .build()
    }

    @Provides
    @Singleton
    fun provideCommentDao(@NonNull database: AppDatabase): CommentDao {
        return database.commentDao()
    }

    @Provides
    @Singleton
    fun provideCommentRepository(commentDao: CommentDao): CommentRepository {
        return CommentRepository(commentDao)
    }
}
