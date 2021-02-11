package com.example.cleanzaets

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.example.cleanzaets.databinding.ActivityPostsBinding
import com.example.cleanzaets.presenter.PostPresenter
import com.example.cleanzaets.presenter.PostUiModel
import com.example.cleanzaets.presenter.PostView
import com.example.cleanzaets.utils.PostComponent

class PostsActivity : AppCompatActivity(), PostView {
    private val binding: ActivityPostsBinding by lazy {
        ActivityPostsBinding.inflate(layoutInflater)
    }
    private lateinit var presenter: PostPresenter
    private val adapter by lazy {
        PostAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initialPostRecycler()
        presenter = PostComponent.createPresenter(this)
    }

    private fun initialPostRecycler() {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.apply {
            rvPosts.layoutManager = linearLayoutManager
            rvPosts.adapter = adapter
        }
    }

    override fun onStart() {
        super.onStart()
        presenter.attachView(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.detachView()
    }

    override fun showPosts(post: List<PostUiModel>) {
        binding.pbLoading.visibility = View.GONE
        adapter.submitList(post)
    }

    override fun showError(error: String) {
        binding.pbLoading.visibility = View.GONE
        MaterialDialog(this).show {
            title(text = "Error")
            message(text = error)
            icon(R.drawable.ic_launcher_foreground)
//            positiveButton(text = "Sadly")
        }
    }
}