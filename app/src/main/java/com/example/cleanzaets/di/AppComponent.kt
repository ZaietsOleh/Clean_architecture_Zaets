package com.example.cleanzaets.di

import com.example.cleanzaets.ui.addpost.AddPostFragment
import com.example.cleanzaets.ui.postviewer.ViewPostsFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(viewPostsFragment: ViewPostsFragment)

    fun inject(addPostFragment: AddPostFragment)
}
