package com.example.cleanzaets.utils

import android.content.Context
import com.example.cleanzaets.data.PostRepository
import com.example.cleanzaets.data.PostService
import com.example.cleanzaets.domain.PostInteractor
import com.example.cleanzaets.domain.PostModelMapper
import com.example.cleanzaets.ui.PostUiMapper
import com.example.cleanzaets.ui.postviewer.ViewPostsViewModel
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideRetrofit(gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create(
            GsonBuilder().setLenient().create()
        )
    }

    @Provides
    @Singleton
    fun providePostService(retrofit: Retrofit) : PostService {
        return retrofit.create(PostService::class.java)
    }


    @Provides
    @Singleton
    fun provideMultithreading(context: Context): Multithreading {
        return Multithreading(context = context)
    }

    @Provides
    @Singleton
    fun provideContext() : Context {
        return context
    }

    @Provides
    @Singleton
    fun providePostUiMapper(resourceRepository: ResourceRepository) : PostUiMapper {
        return PostUiMapper(resourceRepository = resourceRepository)
    }

    @Provides
    @Singleton
    fun provideResourceRepository(context: Context) : ResourceRepository {
        return ResourceRepository(context = context)
    }

    @Provides
    @Singleton
    fun providePostInteractor(postRepository: PostRepository, postModelMapper: PostModelMapper) : PostInteractor {
        return PostInteractor(postRepository = postRepository, postModelMapper = postModelMapper)
    }

    @Provides
    @Singleton
    fun providePostRepository(multithreading: Multithreading, postService: PostService) : PostRepository {
        return PostRepository(multithreading = multithreading, postService = postService)
    }

    @Provides
    @Singleton
    fun providePostModelMapper() : PostModelMapper {
        return PostModelMapper()
    }

    @Provides
    @Singleton
    fun provideViewPostsViewModel(postInteractor: PostInteractor, postUiMapper: PostUiMapper) : ViewPostsViewModel {
        return ViewPostsViewModel(postInteractor = postInteractor, postUiMapper = postUiMapper)
    }
}