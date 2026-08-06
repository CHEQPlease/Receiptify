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
import com.cheq.receiptify.databinding.LayoutHKitchenItemGroupSubheaderBinding
import com.cheq.receiptify.databinding.LayoutHKitchenItemInfoBinding
import com.cheq.receiptify.databinding.LayoutHKitchenItemReasonNoteBinding
import com.cheq.receiptify.showStrikeThrough
import com.cheq.receiptify.utils.Utils

class HVoidDishListAdapter(dishes: List<Item>) :
    RecyclerView.Adapter<HVoidDishListAdapter.ViewHolder>() {

    private val rows = dishes

    override fun getItemViewType(position: Int): Int = when {
        rows[position].isGroupHeader -> VIEW_TYPE_GROUP_HEADER
        rows[position].isGroupSubHeader -> VIEW_TYPE_GROUP_SUBHEADER
        rows[position].isReasonNote -> VIEW_TYPE_REASON_NOTE
        else -> VIEW_TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_GROUP_HEADER -> ViewHolderHeader(
                LayoutHKitchenItemGroupHeaderBinding.inflate(inflater, parent, false)
            )

            VIEW_TYPE_GROUP_SUBHEADER -> ViewHolderSubHeader(
                LayoutHKitchenItemGroupSubheaderBinding.inflate(inflater, parent, false)
            )

            VIEW_TYPE_REASON_NOTE -> ViewHolderReasonNote(
                LayoutHKitchenItemReasonNoteBinding.inflate(inflater, parent, false)
            )

            else -> ViewHolderItem(
                LayoutHKitchenItemInfoBinding.inflate(inflater, parent, false)
            )
        }
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = rows[position]

        when (holder) {
            is ViewHolderItem -> {
                holder.binding.tvItemName.text = Utils.getHTMLFormattedString(item.itemName)
                holder.binding.tvItemDetails.text =
                    Utils.getHTMLFormattedString(item.description)
                holder.binding.tvQty.text = "${item.quantity}"

                if (item.strikethrough) {
                    holder.binding.tvItemName.showStrikeThrough(true)
                    holder.binding.tvItemDetails.showStrikeThrough(true)
                    holder.binding.tvQty.showStrikeThrough(true)
                }

                if (item.description.isNullOrEmpty()) {
                    holder.binding.tvItemDetails.visibility = View.GONE
                }
            }

            is ViewHolderHeader -> {
                holder.binding.tvHeader.text = item.itemName ?: VOID_SECTION_HEADER
            }

            is ViewHolderSubHeader -> {
                holder.binding.tvSubheader.text = Utils.getHTMLFormattedString(item.itemName)
            }

            is ViewHolderReasonNote -> {
                holder.binding.tvReasonNote.text = Utils.getHTMLFormattedString(item.itemName)
            }
        }
    }

    override fun getItemCount(): Int {
        return rows.size
    }

    open class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    class ViewHolderHeader(val binding: LayoutHKitchenItemGroupHeaderBinding) :
        ViewHolder(binding.root)

    class ViewHolderSubHeader(val binding: LayoutHKitchenItemGroupSubheaderBinding) :
        ViewHolder(binding.root)

    class ViewHolderReasonNote(val binding: LayoutHKitchenItemReasonNoteBinding) :
        ViewHolder(binding.root)

    class ViewHolderItem(val binding: LayoutHKitchenItemInfoBinding) : ViewHolder(binding.root)

    companion object {
        private const val VOID_SECTION_HEADER = "Voided items"

        private const val VIEW_TYPE_GROUP_HEADER = 0
        private const val VIEW_TYPE_ITEM = 1
        private const val VIEW_TYPE_GROUP_SUBHEADER = 2
        private const val VIEW_TYPE_REASON_NOTE = 3
    }
}
