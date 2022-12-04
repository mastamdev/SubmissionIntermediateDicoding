package com.finalsubmission.dicoding.adapter

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.finalsubmission.dicoding.databinding.ItemStoryBinding
import com.finalsubmission.dicoding.response.ItemStoryRespone
import com.finalsubmission.dicoding.ui.DetailStoryActivity
import com.finalsubmission.dicoding.utils.DateFormatter
import java.util.*



class AdapterStory : PagingDataAdapter <ItemStoryRespone, AdapterStory.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    class MyViewHolder(private val binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(data: ItemStoryRespone) {
            with(binding) {
                Glide.with(itemView)
                    .load(data.photoUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imgItemPhoto)
                tvItemUname.text = data.name
                tvItemDate.text =
                    DateFormatter.formatDate(data.createdAt.toString(), TimeZone.getDefault().id)
            }
            binding.root.setOnClickListener {
                val intent = Intent(itemView.context, DetailStoryActivity::class.java)
                intent.putExtra(DetailStoryActivity.NAME, data.name)
                intent.putExtra(DetailStoryActivity.PHOTOURL, data.photoUrl)
                intent.putExtra(DetailStoryActivity.DESCRIPTION, data.description)
                intent.putExtra(DetailStoryActivity.DATE, data.createdAt)
                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(binding.imgItemPhoto, "iv_stories"),
                        Pair(binding.tvItemUname, "tv_name"),
                        Pair(binding.tvItemDate, "tv_date"),
                    )
                itemView.context.startActivity(intent, optionsCompat.toBundle())
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemStoryRespone>() {
            override fun areItemsTheSame(
                oldItem: ItemStoryRespone,
                newItem: ItemStoryRespone
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ItemStoryRespone,
                newItem: ItemStoryRespone
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}