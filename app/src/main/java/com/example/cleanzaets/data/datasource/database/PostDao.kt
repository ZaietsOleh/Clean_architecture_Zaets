package com.example.cleanzaets.data.datasource.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PostDao {

    @Query("SELECT * FROM POST ORDER BY IDRECORD DESC")
    suspend fun getPosts() : List<Post>

    @Insert
    suspend fun insertPosts(posts: List<Post>)

    @Insert
    suspend fun insertPost(post: Post)
}
