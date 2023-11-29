package com.codeassesment.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codeassesment.data.model.User
import com.codeassesment.databinding.ItemViewPagerBinding
import com.codeassesment.extn.loadImage


internal class ViewPagerAdapter() :
    RecyclerView.Adapter<ViewPagerAdapter.ImageHolder>() {

    private var userList = mutableListOf<User>()

    inner class ImageHolder(private val binding: ItemViewPagerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(itemdata: User) {
            binding.ivViewpager.loadImage(itemdata.picture?.large ?: "")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder =
        ImageHolder(
            ItemViewPagerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        holder.bindData(userList[position])
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun updateList(items: List<User>) {
        userList = items.toMutableList()
        notifyDataSetChanged()
    }


}



