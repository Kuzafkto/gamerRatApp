package com.example.ratagamerapp.data.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "comments")
data class CommentEntity(
    @PrimaryKey (autoGenerate = true) val commentId:Int,
    val gameId:Int,
    val description: String,
    val userName:String,
)
