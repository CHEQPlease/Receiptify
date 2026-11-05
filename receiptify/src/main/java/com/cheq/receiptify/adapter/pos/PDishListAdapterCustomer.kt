package com.cheq.receiptify.adapter.pos


import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.cheq.receiptify.data.Item
import com.cheq.receiptify.data.ItemSaving
import com.cheq.receiptify.databinding.LayoutPItemSavingBinding
import com.cheq.receiptify.databinding.LayoutPPurchasedItemsBinding
import com.cheq.receiptify.utils.Utils

class PDishListAdapterCustomer(private val dishes: List<Item>) : RecyclerView.Adapter<PDishListAdapterCustomer.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding : LayoutPPurchasedItemsBinding = LayoutPPurchasedItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val dish = dishes[position]

        holder.binding.tvItemName.text = Utils.getHTMLFormattedString(dish.itemName) /* TODO : Move to string resource to support localization in future */
        holder.binding.tvItemDetails.text = Utils.getHTMLFormattedString(dish.description)
        holder.binding.tvPrice.text = "${dish.price}"
        holder.binding.tvQty.text = "${dish.quantity}"
        bindSavings(holder, dish.savings.orEmpty())

        if(dish.description.isNullOrEmpty()){
            holder.binding.tvItemDetails.visibility = View.GONE
        }

//        if(dish.strikethrough){
//            holder.binding.tvQty
//        }

    }

    // Rows are recycled, so rebuild the saving lines on every bind.
    private fun bindSavings(holder: ViewHolder, savings: List<ItemSaving>) {
        val container = holder.binding.containerSavings
        container.removeAllViews()
        container.visibility = if (savings.isEmpty()) View.GONE else View.VISIBLE
        val inflater = LayoutInflater.from(container.context)
        savings.forEach { saving ->
            val row = LayoutPItemSavingBinding.inflate(inflater, container, true)
            row.tvSavingName.text = saving.name
            row.tvSavingQty.text = saving.quantity
            row.tvSavingPrice.text = saving.price
        }
    }

    override fun getItemCount(): Int {
        return dishes.size
    }

    class ViewHolder(var binding: LayoutPPurchasedItemsBinding) : RecyclerView.ViewHolder(binding.root)

}