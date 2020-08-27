package com.axxess.assignmentnikesh.room

import androidx.lifecycle.LiveData
import com.axxess.assignmentnikesh.network.model.Comment
import javax.inject.Inject

class CommentRepository @Inject constructor(var commentDao: CommentDao) {

    fun findAll(imageId: String): LiveData<List<Comment?>?>? {
        return commentDao.findAll(imageId)
    }

    fun insert(comment: Comment?) {
        commentDao.insert(comment)
    }
}
