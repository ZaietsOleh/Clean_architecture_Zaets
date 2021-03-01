package com.example.cleanzaets.data.datasource.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Observable

@Dao
interface PostDao {

    @Query("SELECT * FROM POST ORDER BY ID_RECORD DESC")
    fun getPosts() : Observable<List<Post>>

    @Insert
    fun insertPosts(posts: List<Post>)

    @Insert
    fun insertPost(post: Post)
}