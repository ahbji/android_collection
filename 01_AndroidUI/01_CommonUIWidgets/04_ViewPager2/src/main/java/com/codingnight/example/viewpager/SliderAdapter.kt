package com.codingnight.example.viewpager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SliderAdapter(var list: Array<Int>): RecyclerView.Adapter<SliderAdapter.MyViewHolder> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.slider_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.setBackgroundColor(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var view: View = itemView.findViewById(R.id.view)
    }
}