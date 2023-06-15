package com.example.iqbalfundamental.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.iqbalfundamental.R
import com.example.iqbalfundamental.databinding.FavoriteLayoutBinding
import com.example.iqbalfundamental.db.Favorite
import com.example.iqbalfundamental.ui.DetailActivity

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    private var listData = ArrayList<Favorite>()
    var onItemClick: ((Favorite) -> Unit)? = null

    fun setData(newListData: List<Favorite>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.favorite_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = FavoriteLayoutBinding.bind(itemView)

        fun bind(data: Favorite) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(data.avatarUrl)
                    .into(itemPhoto)
                itemUsername.text = data.username

                itemView.setOnClickListener {
                    val intentDetail = Intent(itemView.context, DetailActivity::class.java)
                    intentDetail.putExtra(DetailActivity.USERNAME, data.username)
                    intentDetail.putExtra(DetailActivity.AVATAR, data.avatarUrl)
                    itemView.context.startActivity(intentDetail)
                }
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }

    }
}