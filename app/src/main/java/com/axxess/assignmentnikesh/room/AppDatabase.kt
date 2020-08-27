package com.axxess.assignmentnikesh.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.axxess.assignmentnikesh.network.model.Comment

@Database(
    entities = [(Comment::class)],
    version = 3, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun commentDao(): CommentDao
}
