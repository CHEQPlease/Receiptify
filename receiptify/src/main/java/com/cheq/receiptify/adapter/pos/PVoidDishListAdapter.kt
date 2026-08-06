package com.cheq.receiptify.adapter.pos

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.cheq.receiptify.data.Item
import com.cheq.receiptify.databinding.LayoutPKitchenItemGroupHeaderBinding
import com.cheq.receiptify.databinding.LayoutPKitchenItemGroupSubheaderBinding
import com.cheq.receiptify.databinding.LayoutPKitchenItemInfoBinding
import com.cheq.receiptify.databinding.LayoutPKitchenItemReasonNoteBinding
import com.cheq.receiptify.showStrikeThrough
import com.cheq.receiptify.utils.Utils

class PVoidDishListAdapter(dishes: List<Item>) :
    RecyclerView.Adapter<PVoidDishListAdapter.ViewHolder>() {

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
                LayoutPKitchenItemGroupHeaderBinding.inflate(inflater, parent, false)
            )

            VIEW_TYPE_GROUP_SUBHEADER -> ViewHolderSubHeader(
                LayoutPKitchenItemGroupSubheaderBinding.inflate(inflater, parent, false)
            )

            VIEW_TYPE_REASON_NOTE -> ViewHolderReasonNote(
                LayoutPKitchenItemReasonNoteBinding.inflate(inflater, parent, false)
            )

            else -> ViewHolderItem(
                LayoutPKitchenItemInfoBinding.inflate(inflater, parent, false)
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

    class ViewHolderHeader(val binding: LayoutPKitchenItemGroupHeaderBinding) :
        ViewHolder(binding.root)

    class ViewHolderSubHeader(val binding: LayoutPKitchenItemGroupSubheaderBinding) :
        ViewHolder(binding.root)

    class ViewHolderReasonNote(val binding: LayoutPKitchenItemReasonNoteBinding) :
        ViewHolder(binding.root)

    class ViewHolderItem(val binding: LayoutPKitchenItemInfoBinding) : ViewHolder(binding.root)

    companion object {
        private const val VOID_SECTION_HEADER = "Voided items"

        private const val VIEW_TYPE_GROUP_HEADER = 0
        private const val VIEW_TYPE_ITEM = 1
        private const val VIEW_TYPE_GROUP_SUBHEADER = 2
        private const val VIEW_TYPE_REASON_NOTE = 3
    }
}
