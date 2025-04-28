package com.cheq.receiptify.adapter.handheld

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.cheq.receiptify.data.Item
import com.cheq.receiptify.data.ItemType
import com.cheq.receiptify.databinding.LayoutHKitchenItemGroupHeaderBinding
import com.cheq.receiptify.databinding.LayoutHKitchenItemInfoBinding
import com.cheq.receiptify.showStrikeThrough
import com.cheq.receiptify.utils.Utils

class HKitchenDishListAdapter(private val dishes: List<Item>) : RecyclerView.Adapter<HKitchenDishListAdapter.ViewHolder>() {
    private val groupedDishes = prepareGroupedItems(dishes)

    override fun getItemViewType(position: Int): Int {
        return if (groupedDishes[position].isGroupHeader) 0 else 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == 0) {
            val binding = LayoutHKitchenItemGroupHeaderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            ViewHolderHeader(binding)
        } else {
            val binding = LayoutHKitchenItemInfoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            ViewHolderItem(binding)
        }

    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = groupedDishes[position]

        if (holder is ViewHolderItem) {
            holder.binding.tvItemName.text = Utils.getHTMLFormattedString(item.itemName)
            holder.binding.tvItemDetails.text = Utils.getHTMLFormattedString(item.description)
            holder.binding.tvQty.text = "${item.quantity}"

            if (item.strikethrough) {
                holder.binding.tvItemName.showStrikeThrough(true)
                holder.binding.tvItemDetails.showStrikeThrough(true)
                holder.binding.tvQty.showStrikeThrough(true)
            }

            if (item.description.isNullOrEmpty()) {
                holder.binding.tvItemDetails.visibility = View.GONE
            }
        } else if (holder is ViewHolderHeader) {
            when (item.itemType) {
                ItemType.FOOD -> holder.binding.tvHeader.text = "Food"
                ItemType.BEVERAGE -> holder.binding.tvHeader.text = "Beverages"
                ItemType.NON_FOOD_BEVERAGE -> holder.binding.tvHeader.text = "Non F&B"
                else -> holder.binding.tvHeader.text = "N/A"
            }
        }
    }

    override fun getItemCount(): Int {
        return groupedDishes.size
    }

    open class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    class ViewHolderHeader(val binding: LayoutHKitchenItemGroupHeaderBinding) :
        ViewHolder(binding.root)

    class ViewHolderItem(val binding: LayoutHKitchenItemInfoBinding) : ViewHolder(binding.root)

    private fun prepareGroupedItems(originalItems: List<Item>): List<Item> {
        val groupedList = mutableListOf<Item>()

        ItemType.values().forEach { type ->
            val itemsOfType = originalItems.filter { it.itemType == type }
            if (itemsOfType.isNotEmpty()) {
                val sortedList = itemsOfType.sortedBy { it.itemName }

                groupedList.add(
                    Item(
                        null,
                        null,
                        null,
                        null,
                        strikethrough = true,
                        type,
                        isGroupHeader = true
                    )
                )
                groupedList.addAll(sortedList)
            }
        }

        return groupedList
    }
}
