package com.example.cleanzaets.ui.postviewer.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.cleanzaets.ui.PostUiModel

class DiffCallbackPostAdapter: DiffUtil.ItemCallback<PostUiModel>() {
    override fun areItemsTheSame(oldItem: PostUiModel, newItem: PostUiModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: PostUiModel, newItem: PostUiModel): Boolean {
        return oldItem == newItem
    }

}