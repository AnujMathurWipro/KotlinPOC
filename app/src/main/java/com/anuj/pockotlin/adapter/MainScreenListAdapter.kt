package com.anuj.pockotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

import com.anuj.pockotlin.R
import com.anuj.pockotlin.databinding.ItemListBinding
import com.anuj.pockotlin.models.RowsItem

class MainScreenListAdapter(private val inflater: LayoutInflater, private var itemList: List<RowsItem>?) : RecyclerView.Adapter<MainScreenListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ItemListBinding>(inflater, R.layout.item_list, parent, false)
        return ViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return if (itemList == null) 0 else itemList!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = DataBindingUtil.getBinding<ItemListBinding>(holder.itemView)
        val item = itemList!![position]
        binding?.rowItem = item
        binding?.executePendingBindings()
    }

    fun setListItems(items: List<RowsItem>?) {
        itemList = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}