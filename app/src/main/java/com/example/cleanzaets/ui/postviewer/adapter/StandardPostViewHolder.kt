package com.example.cleanzaets.ui.postviewer.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanzaets.databinding.ViewHolderStandartPostBinding
import com.example.cleanzaets.ui.PostUiModel

class StandardPostViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding: ViewHolderStandartPostBinding by lazy {
        ViewHolderStandartPostBinding.bind(view)
    }

    fun onBind(standardPost: PostUiModel.StandardPost) {
        binding.apply {
            clContainer.setBackgroundColor(standardPost.backgroundColor)
            tvUserId.text = standardPost.userId
            tvTitle.text = standardPost.title
            tvBody.text = standardPost.body
            if (standardPost.hasWarning) {
                tvWarning.visibility = View.VISIBLE
            }
            else {
                tvWarning.visibility = View.GONE
            }
        }
    }
}
