package com.example.lab_3

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lab_3.CartFragment.Companion.indCart
import com.example.lab_3.CartFragment.Companion.launcherCart

class CartAdapter(listArray: ArrayList<ItemList>, context: Context?, color: ArrayList<Int>) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    private var listArrayR = listArray
    private var contextR = context
    private var colorR = color

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
        private val tvContent = view.findViewById<TextView>(R.id.tvContent)
        private var contextVH:Context ?= null

        fun bind(listItem: ItemList, context: Context?) {
            contextVH = context
            tvTitle.text = listItem.title
            val content = listItem.content
            tvContent.text = content
            itemView.setOnClickListener {
                indCart = adapterPosition
                val intent = Intent(context, ItemCart::class.java).apply {
                    putExtra("title", tvTitle.text.toString())
                    putExtra("content", listItem.content)
                }
                launcherCart?.launch(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(contextR)
        return ViewHolder(inflater.inflate(R.layout.layout_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listItem = listArrayR[position]
        holder.bind(listItem, contextR)
        when {
            listItem.color != null -> {
                holder.itemView.setBackgroundColor(listItem.color!!)
            }
        }
    }

    override fun getItemCount(): Int {
        return listArrayR.size
    }

}



//            else -> {
//                if(MainActivity.colorArr.isNotEmpty())
//                    holder.itemView.setBackgroundColor(listItem.color!!)
//            }