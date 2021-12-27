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
import com.example.lab_3.MainActivity.Companion.colorArr
import com.example.lab_3.MainActivity.Companion.launcher
import kotlinx.android.synthetic.main.layout_content.*

class NotesAdapter(listArray: ArrayList<ItemList>, context: Context?, color: ArrayList<Int>) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    private var listArrayR = listArray
    private var contextR = context
    private var colorR = color

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
        private val tvContent = view.findViewById<TextView>(R.id.tvContent)

        fun bind(listItem: ItemList, context: Context?) {
            tvTitle.text = listItem.title
            val content = listItem.content
            tvContent.text = content
            itemView.setOnClickListener {
                MainActivity.ind = adapterPosition
                val intent = Intent(context, UpdateNote::class.java).apply {
                    putExtra("title", tvTitle.text.toString())
                    putExtra("content", listItem.content)
                }
                launcher?.launch(intent)
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
            MainActivity.ind == position -> {
                holder.itemView.setBackgroundColor(colorR[position])
                listItem.color = colorR[position]
            }
            listItem.color != null -> {
                holder.itemView.setBackgroundColor(listItem.color!!)
            }
            else->{
                if(colorArr.isNotEmpty()) {
                    holder.itemView.setBackgroundColor(colorArr[position])
                    listItem.color = colorR[position]
                } else {
                    colorArr.add(Color.rgb(190,190,190))
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return listArrayR.size
    }

}