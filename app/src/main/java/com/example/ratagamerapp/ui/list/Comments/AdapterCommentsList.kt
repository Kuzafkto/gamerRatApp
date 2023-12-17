package com.example.ratagamerapp.ui.list.Comments

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ratagamerapp.data.db.CommentEntity
import com.example.ratagamerapp.databinding.CommentListItemBinding

class AdapterCommentsList (private val context: Context) :
//usa un listAdapter que es una clase de kotlin
    ListAdapter<CommentEntity,AdapterCommentsList.DetailViewHolder>(commentEntityCallback) {

    inner class DetailViewHolder(private val binding: CommentListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        //bind se encarga de de hacer el binding del titulo y la imagen usando binding e imageRequest
        fun bind(comment: CommentEntity) {
            binding.commentUser.text=comment.userName
            binding.commentText.text=comment.description
        }

    }

    private object commentEntityCallback : DiffUtil.ItemCallback<CommentEntity>() {
        override fun areItemsTheSame(oldItem: CommentEntity, newItem: CommentEntity) =
            oldItem.commentId == newItem.commentId

        override fun areContentsTheSame(oldItem: CommentEntity, newItem: CommentEntity) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder=
        DetailViewHolder(
            CommentListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )


    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) =
        holder.bind((getItem(position)))
}