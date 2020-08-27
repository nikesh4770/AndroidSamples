package com.axxess.assignmentnikesh.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.axxess.assignmentnikesh.network.model.Comment

@Dao
interface CommentDao {

    @Query("SELECT * FROM Comment WHERE imageId=:imageId")
    fun findAll(imageId: String): LiveData<List<Comment?>?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(comment: Comment?)
}
