package com.cheq.receiptify.adapter.handheld

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.cheq.receiptify.data.Item
import com.cheq.receiptify.databinding.LayoutHKitchenItemGroupHeaderBinding
import com.cheq.receiptify.databinding.LayoutHKitchenItemInfoBinding
import com.cheq.receiptify.showStrikeThrough
import com.cheq.receiptify.utils.Utils

class HVoidDishListAdapter(dishes: List<Item>) :
    RecyclerView.Adapter<HVoidDishListAdapter.ViewHolder>() {

    private val rows = dishes

    override fun getItemViewType(position: Int): Int {
        return if (rows[position].isGroupHeader) 0 else 1
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
        val item = rows[position]

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
            holder.binding.tvHeader.text = item.itemName ?: VOID_SECTION_HEADER
        }
    }

    override fun getItemCount(): Int {
        return rows.size
    }

    open class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    class ViewHolderHeader(val binding: LayoutHKitchenItemGroupHeaderBinding) :
        ViewHolder(binding.root)

    class ViewHolderItem(val binding: LayoutHKitchenItemInfoBinding) : ViewHolder(binding.root)

    companion object {
        private const val VOID_SECTION_HEADER = "Voided items"
    }
}
