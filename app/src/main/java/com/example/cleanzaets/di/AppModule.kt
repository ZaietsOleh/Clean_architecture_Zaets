package com.example.cleanzaets.di

import android.content.Context
import androidx.room.Room
import com.example.cleanzaets.data.PostRepository
import com.example.cleanzaets.data.datasource.PostService
import com.example.cleanzaets.data.UserRepository
import com.example.cleanzaets.data.datasource.database.PostDao
import com.example.cleanzaets.data.datasource.database.PostDatabase
import com.example.cleanzaets.domain.PostInteractor
import com.example.cleanzaets.domain.PostModelMapper
import com.example.cleanzaets.ui.PostUiMapper
import com.example.cleanzaets.ui.addpost.AddPostViewModel
import com.example.cleanzaets.ui.postviewer.ViewPostsViewModel
import com.example.cleanzaets.utils.DATABASE_NAME
import com.example.cleanzaets.utils.ResourceRepository
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideRetrofit(gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
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
    fun providePostRepository(postService: PostService, postDao: PostDao) : PostRepository {
        return PostRepository(postService = postService, postDao = postDao)
    }

    @Provides
    @Singleton
    fun providePostModelMapper(userRepository: UserRepository) : PostModelMapper {
        return PostModelMapper(userRepository)
    }

    @Provides
    @Singleton
    fun provideViewPostsViewModel(postInteractor: PostInteractor, postUiMapper: PostUiMapper) : ViewPostsViewModel {
        return ViewPostsViewModel(postInteractor = postInteractor, postUiMapper = postUiMapper)
    }

    @Provides
    @Singleton
    fun provideAddPostViewModel(postInteractor: PostInteractor) : AddPostViewModel {
        return AddPostViewModel(postInteractor = postInteractor)
    }

    @Provides
    @Singleton
    fun provideDatabase(context: Context) : PostDatabase {
        return Room.databaseBuilder(
            context,
            PostDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providePostDao(postDatabase: PostDatabase) : PostDao {
        return postDatabase.postDao()
    }
}