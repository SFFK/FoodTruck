package com.cookandroid.foodtruck

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class RecyclerItemAdapter(private val items : ArrayList<PostData>) : RecyclerView.Adapter<RecyclerItemAdapter.ViewHolder>() {
    // 아이템 개수 반환
    override fun getItemCount(): Int = items.size

    // 뷰에 데이터를 연결
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val listener = View.OnClickListener { it ->
            Toast.makeText(it.context, "Clicked -> Title : ${item.title}, Content : ${item.content}", Toast.LENGTH_SHORT).show()
        }
        holder.apply {
            bind(listener, item)
            itemView.tag = item
        }
    }

    // view 생성, viewHolder를 반환
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false)
        return ViewHolder(inflatedView)
    }


    // 각 항목에 필요한 기능을 구현
    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view : View = v
        fun bind(listener: View.OnClickListener, item: PostData) {
            view.findViewById<TextView>(R.id.post_title).text = item.title
            view.findViewById<TextView>(R.id.post_content).text = item.content
            view.setOnClickListener(listener)
        }
    }
}