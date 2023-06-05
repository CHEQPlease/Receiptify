package com.cheq.receiptify.adapter.handheld

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.cheq.receiptify.data.Item
import com.cheq.receiptify.databinding.LayoutHKitchenItemInfoBinding
import com.cheq.receiptify.showStrikeThrough
import com.cheq.receiptify.utils.Utils

class HKitchenDishListAdapter(private val dishes: List<Item>) : RecyclerView.Adapter<HKitchenDishListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding : LayoutHKitchenItemInfoBinding = LayoutHKitchenItemInfoBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val dish = dishes[position]

        holder.binding.tvItemName.text = Utils.getHTMLFormattedString(dish.itemName) /* TODO : Move to string resource to support localization in future */
        holder.binding.tvItemDetails.text = Utils.getHTMLFormattedString(dish.description)
        holder.binding.tvQty.text = "${dish.quantity}"

        if(dish.strikethrough){
            holder.binding.tvItemName.showStrikeThrough(true)
            holder.binding.tvItemDetails.showStrikeThrough(true)
            holder.binding.tvQty.showStrikeThrough(true)
        }

    }

    override fun getItemCount(): Int {
        return dishes.size
    }

    class ViewHolder(var binding: LayoutHKitchenItemInfoBinding) : RecyclerView.ViewHolder(binding.root)

}