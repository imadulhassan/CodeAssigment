package com.codeassesment.ui.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.codeassesment.data.model.User

import com.codeassesment.databinding.ItemUsersBinding
import com.codeassesment.extn.loadImage


@SuppressLint("NotifyDataSetChanged")
internal class UsersAdapter(private val itemClickListener: (User) -> Unit) :
    Adapter<UsersAdapter.ItemHolder>(), Filterable {

    private var userList = mutableListOf<User>()
    private var userListFiltered = mutableListOf<User>()

    inner class ItemHolder(private val binding: ItemUsersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(itemdata: User) {
            binding.userNameTextView.text = "${itemdata.name?.first}  ${itemdata.name?.last}"
            binding.userImageView.loadImage(itemdata.picture?.thumbnail ?: "")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder =
        ItemHolder(ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bindData(userListFiltered[position])
        holder.itemView.setOnClickListener {
            itemClickListener(userListFiltered[position])
        }
    }

    override fun getItemCount(): Int {
        return userListFiltered.size
    }


    fun updateList(items: List<User>) {
        userList = items.toMutableList()
        userListFiltered = items.toMutableList()
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val query = charSequence.toString()

                userListFiltered = if (query.isEmpty()) {
                    userList.toMutableList()
                } else {
                    userList.filter {
                        it.name?.first?.contains(
                            query, ignoreCase = true
                        ) ?: false || it.name?.last?.contains(query, ignoreCase = true) ?: false
                    }.toMutableList()
                }
                val filterResults = FilterResults()
                filterResults.values = userListFiltered
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                userListFiltered = filterResults.values as MutableList<User>
                notifyDataSetChanged()
            }

        }
    }


}
