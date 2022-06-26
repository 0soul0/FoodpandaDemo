package com.sideproject.foodpandafake.fragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sideproject.foodpandafake.databinding.ItemMenuBinding
import com.sideproject.foodpandafake.model.Menu

class StoreMenuAdapter : RecyclerView.Adapter<StoreMenuAdapter.ViewHolder>() {

    private var oldData = emptyList<Menu>()
    private var onItemClickLister: ((Menu, Int) -> Unit)? = null

    inner class ViewHolder(private val binding: ItemMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Menu, position: Int) {
            itemView.apply {
                Glide.with(this).load(item.imgUrl).into(binding.imgMenu)
                binding.tvMenuName.text = item.menuName
                binding.tvMenuPrice.text="$ ${item.price}"
                binding.imgBtnAdd.setOnClickListener {
                    onItemClickLister?.let {
                        it(item, position)
                    }
                }
            }
        }
    }

    fun setOnItemClickLister(lister:(Menu, Int) -> Unit) {
        onItemClickLister = lister
    }

    fun setData(newData: List<Menu>) {
        val diffResult = DiffUtil.calculateDiff(AppDiffUtil(oldData, newData))
        oldData = newData
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
        val listItemBinding = ItemMenuBinding.inflate(view, parent, false)
        return ViewHolder(listItemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(oldData[position], position)
    }

    override fun getItemCount() = oldData.size


    class AppDiffUtil(
        private val oldData: List<Menu>,
        private val newData: List<Menu>
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