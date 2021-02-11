package com.example.cleanzaets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanzaets.presenter.PostUiModel

class PostAdapter: ListAdapter<PostUiModel, RecyclerView.ViewHolder>(DiffCallbackPostAdapter()) {
    enum class PostType {
        STANDARD,
        BANNED
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is PostUiModel.StandardPost -> PostType.STANDARD
            is PostUiModel.BannedPost -> PostType.BANNED
        }.ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewTypeEnum = PostType.values()[viewType]
        val view = if (viewTypeEnum == PostType.STANDARD) {
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_standart_post, parent, false)
        } else {
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_banned_post, parent, false)
        }

        return if (viewTypeEnum == PostType.STANDARD) {
            StandardPostViewHolder(view)
        } else {
            BannedPostViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is StandardPostViewHolder -> holder.onBind(getItem(position) as PostUiModel.StandardPost)
            is BannedPostViewHolder ->  holder.onBind(getItem(position) as PostUiModel.BannedPost)

        }
    }
}