package com.example.cleanzaets.ui.addpost

import android.os.Bundle
import android.view.View
import com.example.cleanzaets.R
import com.example.cleanzaets.databinding.FragmentAddPostBinding
import com.example.cleanzaets.ui.BaseFragment
import com.example.cleanzaets.di.AppModule
import com.example.cleanzaets.di.DaggerAppComponent
import javax.inject.Inject

class AddPostFragment : BaseFragment(R.layout.fragment_add_post) {
    companion object {
        fun newInstance() : AddPostFragment {
            return AddPostFragment()
        }
    }

    @Inject
    lateinit var viewModel: AddPostViewModel
    private lateinit var binding: FragmentAddPostBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddPostBinding.bind(view)

        injectDependency()
        observeViewModel()
        setListener()
    }

    private fun setListener() {
        binding.btnAdd.setOnClickListener {
            addPost()
        }
    }

    private fun addPost() {
        viewModel.addPost(
            RawPost(
                title = binding.tieTitle.text.toString(),
                body =binding.tieBody.text.toString()
        ))
    }

    private fun injectDependency() {
        DaggerAppComponent.builder()
            .appModule(AppModule(requireContext()))
            .build()
            .inject(this)
    }

    private fun observeViewModel() {
        viewModel.fragmentStateLiveData.observe(viewLifecycleOwner) {
            setState(it)
        }
    }

    private fun setState(state: AddPostState) {
        binding.apply {
            tilBody.error = null
            tilTitle.error = null
            when (state) {
                AddPostState.TITLE_ERROR_LENGTH -> tilTitle.error = getString(R.string.error_title_length)
                AddPostState.BODY_ERROR_LENGTH -> tilBody.error = getString(R.string.error_body_length)
                AddPostState.TITLE_ERROR_BAD_WORD -> tilTitle.error = getString(R.string.error_title_has_bad_word)
                else -> navigation.showViewPostsFragment()
            }
        }
    }
}