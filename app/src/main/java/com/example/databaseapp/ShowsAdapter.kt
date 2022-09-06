package com.example.databaseapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.databaseapp.databinding.ShowLstElemBinding

class ShowsViewHolder(
    private val binding: ShowLstElemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: RoomShow) {
        with(binding) {
            showNameTxtv.text = item.showName
            seriesTxtv.text=item.showSeries
        }
    }

}

class ShowsAdapter(
    context: Context
) : ListAdapter<RoomShow, ShowsViewHolder>(DIFF_UTIL) {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowsViewHolder {

        return ShowsViewHolder(
                binding = ShowLstElemBinding.inflate(layoutInflater, parent, false)
            )

    }

    override fun onBindViewHolder(holder: ShowsViewHolder, position: Int) {

        val item = getItem(position)
        holder.bind(item)

    }

    companion object {

        private val DIFF_UTIL = object : DiffUtil.ItemCallback<RoomShow>() {

            override fun areItemsTheSame(oldItem: RoomShow, newItem: RoomShow): Boolean {

                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: RoomShow, newItem: RoomShow): Boolean {

                return oldItem == newItem
            }
        }
    }
}