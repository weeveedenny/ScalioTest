package com.example.scaliotest.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.scaliotest.R
import com.example.scaliotest.databinding.RepoViewItemBinding
import com.example.scaliotest.model.User

class UserViewHolder(private val binding: RepoViewItemBinding) : RecyclerView.ViewHolder(binding.root) {


    fun bind(user: User?, context: Context) {
        binding.loginTextView.text = user?.login
        binding.typeTextView.text = user?.type
        Glide.with(context).load(user?.avatar_url).into(binding.itemImageView)
    }


    companion object {
        fun create(parent: ViewGroup): UserViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.repo_view_item, parent, false)
            val binding = RepoViewItemBinding.bind(view)
            return UserViewHolder(binding)
        }
    }
}
