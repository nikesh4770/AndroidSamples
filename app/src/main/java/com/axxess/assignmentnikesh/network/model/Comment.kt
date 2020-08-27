package com.axxess.assignmentnikesh.network.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Comment(
    @PrimaryKey(autoGenerate = true) val id: Int,
    var imageId: String,
    var description: String
)