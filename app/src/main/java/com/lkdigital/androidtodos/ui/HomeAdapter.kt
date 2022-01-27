package com.lkdigital.androidtodos.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lkdigital.androidtodos.R
import com.lkdigital.androidtodos.data.Todo

class HomeAdapter(var listener:HomeListener) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>(){

    private var data : ArrayList<Todo>?=null

    interface HomeListener{
        fun onItemDeleted(Todo: Todo, position: Int)
    }

    fun setData(list: ArrayList<Todo>){
        data = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.todo_item_view, parent, false))
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = data?.get(position)
        holder.bindView(item)
        holder.deleteImage.setOnClickListener {
            item?.let { it1 ->
                listener.onItemDeleted(it1, position)
            }
        }
    }

    fun addData(todo: Todo) {
        data?.add(0, todo)
        notifyItemInserted(0)
    }

    fun removeData(position: Int) {
        data?.removeAt(position)
        notifyDataSetChanged()
    }

    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val todoTV = itemView.findViewById<TextView>(R.id.tv_home_item_todo)
        val completedTV = itemView.findViewById<TextView>(R.id.tv_home_item_completed)

        val deleteImage = itemView.findViewById<ImageView>(R.id.img_delete)

        fun bindView(item: Todo?) {
            todoTV.text = item?.todo
            completedTV.text = item?.completed.toString()
        }

    }

}