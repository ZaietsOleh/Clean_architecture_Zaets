package com.example.cleanzaets.domain

import com.example.cleanzaets.data.PostRepository
import com.example.cleanzaets.ui.addpost.AddPostState
import com.example.cleanzaets.ui.addpost.RawPost
import com.example.cleanzaets.utils.ResourceRepository
import io.kotlintest.shouldBe
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class PostInteractorTest {

    private val mockPostRepository = mockk<PostRepository>(relaxUnitFun = true)
    private val badWord = "badword"
    private val mockResourceRepository = mockk<ResourceRepository> {
        every { getString(any()) } returns badWord
    }
    private val mockPostModelMapper = mockk<PostModelMapper>(relaxed = true)
    private val postInteractor =
        PostInteractor(mockPostRepository, mockPostModelMapper, mockResourceRepository)

    @Test
    fun `if title length less then 3 returns TITLE_ERROR_LENGTH`() {
        val testingPost = RawPost(
            title = "12",
            body = "Lorem ipsum dolor sit amet"
        )

        runBlocking {
            postInteractor.addPost(testingPost) shouldBe AddPostState.TITLE_ERROR_LENGTH
        }
    }

    @Test
    fun `if title has bad word returns TITLE_ERROR_BAD_WORD`() {
        val testingPost = RawPost(
            title = badWord,
            body = "Lorem ipsum dolor sit amet"
        )

        runBlocking {
            postInteractor.addPost(testingPost) shouldBe AddPostState.TITLE_ERROR_BAD_WORD
        }
    }

    @Test
    fun `if body length less then 5 returns BODY_ERROR_LENGTH`() {
        val testingPost = RawPost(
            title = "test_title",
            body = "123"
        )

        runBlocking {
            postInteractor.addPost(testingPost) shouldBe AddPostState.BODY_ERROR_LENGTH
        }
    }

    @Test
    fun `if body length more then 500 returns BODY_ERROR_LENGTH`() {
        val testingPost = RawPost(
            title = "test_title",
            body = """
                Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt 
                ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation 
                ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in 
                reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. 
                Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt 
                mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipiscing elit, 
                sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
            """.trimIndent()
        )

        runBlocking {
            postInteractor.addPost(testingPost) shouldBe AddPostState.BODY_ERROR_LENGTH
        }
    }

    @Test
    fun `if everything was OK returns WITHOUT_ERROR`() {
        val testingPost = RawPost(
            title = "test_title",
            body = """
                Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor 
                incididunt ut labore et dolore magna aliqua.
            """.trimIndent()
        )

        runBlocking {
            postInteractor.addPost(testingPost) shouldBe AddPostState.WITHOUT_ERROR
        }
    }

    @Test
    fun `if everything was OK PostRepository should be called`() {
        val testingPost = RawPost(
            title = "test_title",
            body = """
                Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor 
                incididunt ut labore et dolore magna aliqua.
            """.trimIndent()
        )

        runBlocking {
            postInteractor.addPost(testingPost)
        }
        coVerify { mockPostRepository.addPost(any()) }
    }
}