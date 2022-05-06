package com.geekbrains.tests.view.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.tests.R

class RecyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return 100
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private var title: TextView = itemView.findViewById(R.id.recycler_title)
        fun bind(position:Int){
            title.text = "Item ${position}"
            itemView.setOnClickListener {
                Toast.makeText(itemView.context, "Item ${position}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}