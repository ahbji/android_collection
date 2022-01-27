package com.codingnight.example.navigationdrawer

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class MyListAdapter(private val isPager:Boolean) : ListAdapter<Int, RecyclerView.ViewHolder>(callback) {
    object callback : DiffUtil.ItemCallback<Int>() {
        override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder, parent, false)
        if (isPager) {
            view.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
            view.findViewById<ImageView>(R.id.cellImageView).layoutParams.width = 0
            view.findViewById<ImageView>(R.id.cellImageView).layoutParams.height = 0
        }
        return object : RecyclerView.ViewHolder(view){}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.findViewById<ImageView>(R.id.cellImageView).setImageResource(getItem(position))
    }
}