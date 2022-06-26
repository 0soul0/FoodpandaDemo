package com.sideproject.foodpandafake.fragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sideproject.foodpandafake.databinding.ItemStoreBinding
import com.sideproject.foodpandafake.fragment.StoreAdapter.*
import com.sideproject.foodpandafake.model.Store

class StoreAdapter : RecyclerView.Adapter<ViewHolder>() {

    private var oldData = emptyList<Store>()
    private var onItemClickLister: ((Store, Int) -> Unit)? = null

    inner class ViewHolder(private val binding: ItemStoreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Store, position: Int) {
            itemView.apply {
                binding.ivStore.apply {
                    if (null != item.img) {
                        setImageBitmap(item.img)
                    } else {
                        Glide.with(this).load(item.imgUrl).into(this)
                    }
                }
                binding.tvName.text="$position ${item.name}"
                setOnClickListener {
                    onItemClickLister?.let {
                        it(item, position)
                    }
                }
            }
        }
    }

    fun setOnItemClickLister(lister: (Store, Int) -> Unit) {
        onItemClickLister = lister
    }

    fun setData(newData: List<Store>) {
        val diffResult = DiffUtil.calculateDiff(AppDiffUtil(oldData, newData))
        oldData = newData
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
        val listItemBinding = ItemStoreBinding.inflate(view, parent, false)
        return ViewHolder(listItemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(oldData[position], position)
    }

    override fun getItemCount() = oldData.size


    class AppDiffUtil(
        private val oldData: List<Store>,
        private val newData: List<Store>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldData.size

        override fun getNewListSize(): Int = newData.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldData[oldItemPosition].id == newData[newItemPosition].id

        //這裡可以比較內容
        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldData[oldItemPosition].id == newData[newItemPosition].id


    }
}