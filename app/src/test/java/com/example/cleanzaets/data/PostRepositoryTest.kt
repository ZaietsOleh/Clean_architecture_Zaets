package com.example.cleanzaets.data

import com.example.cleanzaets.data.datasource.PostService
import com.example.cleanzaets.data.datasource.database.PostDao
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class PostRepositoryTest {

    @Test
    fun `if database is empty call insertPosts`() {
        val mockPostService = mockk<PostService> {
            coEvery { getPosts() } returns emptyList()
        }
        val mockPostDao = mockk<PostDao>(relaxUnitFun = true) {
            coEvery { getPosts() } returns emptyList()
        }

        val postRepository = PostRepository(mockPostService, mockPostDao)
        runBlocking {
            postRepository.getPosts()
        }

        coVerify { mockPostDao.insertPosts(any()) }
    }
}