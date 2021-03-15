package com.example.cleanzaets.ui.postviewer

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.example.cleanzaets.di.AppModule
import com.example.cleanzaets.R
import com.example.cleanzaets.databinding.FragmentViewPostsBinding
import com.example.cleanzaets.di.DaggerAppComponent
import com.example.cleanzaets.ui.PostUiModel
import com.example.cleanzaets.ui.BaseFragment
import com.example.cleanzaets.ui.State
import com.example.cleanzaets.ui.postviewer.adapter.PostAdapter
import javax.inject.Inject

class ViewPostsFragment : BaseFragment(R.layout.fragment_view_posts) {
    companion object {
        fun newInstance() : ViewPostsFragment {
            return ViewPostsFragment()
        }
    }

    @Inject
    lateinit var viewModel: ViewPostsViewModel
    private lateinit var binding: FragmentViewPostsBinding
    private lateinit var adapter: PostAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentViewPostsBinding.bind(view)

        injectDependency()
        observePosts()
        setupRecyclerAdapter()
        setupClickListener()

        viewModel.getPost()
    }

    private fun injectDependency() {
        DaggerAppComponent.builder()
            .appModule(AppModule(requireContext()))
            .build()
            .inject(this)
    }

    private fun observePosts() {
        viewModel.postsLiveData.observe(viewLifecycleOwner) {
            updatePostsList(it)
        }
    }

    private fun updatePostsList(state: State<List<PostUiModel>, Int>) {
        when (state) {
            is State.Loading -> binding.pbLoading.visibility = View.VISIBLE
            is State.Content -> {
                binding.pbLoading.visibility = View.GONE
                adapter.submitList(state.data)
            }
            is State.Error -> {
                binding.pbLoading.visibility = View.GONE
                MaterialDialog(requireContext()).show {
                    title(R.string.error_dialog_title)
                    message(state.error)
                    icon(R.drawable.ic_launcher_foreground)
                }
            }
        }
    }

    private fun setupClickListener() {
        binding.btnAddPost.setOnClickListener {
            showAddPostFragment()
        }
    }

    private fun showAddPostFragment() {
        navigation.showAddPostFragment()
    }

    private fun setupRecyclerAdapter() {
        adapter = PostAdapter()
        binding.apply {
            rvPosts.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            rvPosts.adapter = adapter
        }
    }
}
