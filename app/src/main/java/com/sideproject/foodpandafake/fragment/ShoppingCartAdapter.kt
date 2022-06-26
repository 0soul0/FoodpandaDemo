package com.sideproject.foodpandafake.fragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sideproject.foodpandafake.R
import com.sideproject.foodpandafake.databinding.ItemShoppingCartBinding
import com.sideproject.foodpandafake.model.ShoppingCart
import java.util.*

class ShoppingCartAdapter(context: Context) :
    RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder>() {

    private var oldData = emptyList<ShoppingCart>()
    private var callbackData: ((Int) -> Unit)? = null
    private var sumPrice: Int = 0
    private val numberMenuLabel = context.getString(R.string.numberMenuLabel)
    private var lastStoreId: UUID? = null

    inner class ViewHolder(private val binding: ItemShoppingCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ShoppingCart, position: Int) {
            itemView.apply {
                if (lastStoreId != item.storeId) {
                    binding.tvStore.text = item.storeName
                    binding.tvStore.visibility = View.VISIBLE
                    lastStoreId = item.storeId
                    item.menuNumber
                } else {
                    binding.tvStore.visibility = View.GONE
                }

                binding.tvAmount.text = String.format(numberMenuLabel, item.amount)
                binding.tvPrice.text = item.menuPrice
                binding.tvMenuName.text = item.menuItemName

                sumPrice += item.menuPrice.toInt()
                if (position == itemCount - 1) {
                    callbackData?.let {
                        it(sumPrice)
                    }
                }
            }
        }
    }

    fun callbackData(lister: (Int) -> Unit) {
        callbackData = lister
    }

    fun setData(newData: List<ShoppingCart>) {
        val diffResult = DiffUtil.calculateDiff(AppDiffUtil(oldData, newData))
        oldData = newData
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
        val listItemBinding = ItemShoppingCartBinding.inflate(view, parent, false)
        return ViewHolder(listItemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(oldData[position], position)
    }

    override fun getItemCount() = oldData.size


    class AppDiffUtil(
        private val oldData: List<ShoppingCart>,
        private val newData: List<ShoppingCart>
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